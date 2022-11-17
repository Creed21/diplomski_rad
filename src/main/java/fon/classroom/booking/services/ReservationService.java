package fon.classroom.booking.services;

import fon.classroom.booking.model.Reservation;
import fon.classroom.booking.model.ReservationStatus;
import fon.classroom.booking.model.User;
import fon.classroom.booking.repository.ReservationRepository;
import fon.classroom.booking.repository.ReservationStatusRepository;
import fon.classroom.booking.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationStatusRepository reservationStatusRepository;

    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    UserRepository userRepository;

    public List<Reservation> findAll() {
        System.out.println("ReservationRepository.findAll()");
        return reservationRepository.findAll();
    }

    public Optional<Reservation> findById(Integer ID) {
        return reservationRepository.findById(ID);
    }

    public List<Reservation> findByClassRoomID(Integer classRoomID) {
        return reservationRepository.findByClassRoomID(classRoomID);
    }

    @Transactional
    public Reservation saveReservation(Reservation reservation, String createdBy) {
        if(reservation == null)
            throw new IllegalArgumentException("Reservation has not been approved!");

        if(reservation.getClassRoom() == null || reservation.getBeginningAsked() == null || reservation.getEndAsked() == null
         || reservation.getReservationForUser() == null)
            throw new IllegalArgumentException("Bad input! Populate all the fields.");

        if(reservation.getBeginningAsked().after(reservation.getEndAsked()))
            throw new IllegalArgumentException("Beginning of the meeting must be after it's ending!");

        if(!reservationRepository.findByClassRoomAndBeginningApprovedAfterAndEndApprovedBefore(
                reservation.getClassRoom().getId(), reservation.getBeginningAsked(), reservation.getEndAsked()
        ).isEmpty()) {
            System.out.println("This period is is already taken!");
            throw new IllegalArgumentException("This period is is already taken!");
        }
        reservation.setUsc(createdBy);
        reservation.setDtc(LocalDateTime.now());

        ReservationStatus reservationStatus = reservationStatusRepository.findByDscIgnoreCase("PENDING").get();
        if(reservationStatus == null)
            throw new IllegalArgumentException("Rezervacija nije sačuvana!");
        
        reservation.setReservationStatus(reservationStatus);
        Reservation saved_reservation = reservationRepository.save(reservation);
        if(saved_reservation == null)
            throw new IllegalArgumentException("Rezervacija nije sačuvana!");

        User reservationFor = saved_reservation.getReservationForUser();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy. HH:mm");

        String subject = "Zahtev za rezervaciju: "+saved_reservation.getClassRoom().getDsc()+" "+simpleDateFormat.format(saved_reservation.getBeginningAsked());

        String emailBody = "Poštovani "+reservationFor.getFirstName()+" "+reservationFor.getLastName()+",\n\n"
                +"Želimo da Vas obavestimo da je rezervacija za kabinet \""+saved_reservation.getClassRoom().getDsc()+"\" u periodu od "
                +simpleDateFormat.format(saved_reservation.getBeginningAsked())
                +(saved_reservation.getEndAsked() != null ? " - "+simpleDateFormat.format(saved_reservation.getEndAsked()) : "")
                +" uspešno poslata.\n\n"
                +"Srdačan pozdrav,\nFonClassRoomBookingSystem\nAleksandar Janković";

        emailService.sendSimpleMessage(reservationFor.getEmail(), subject, emailBody);


        userRepository.findUsersByRole("ADMIN").stream()
                .forEach((u) -> {
                    String email_for_admins = "Poštovani "+u.getFirstName()+" "+u.getLastName()+",\n\n"
                            +"Želimo da Vas obavestimo da je rezervacija za kabinet \""+saved_reservation.getClassRoom().getDsc()+"\" u periodu od "
                            +simpleDateFormat.format(saved_reservation.getBeginningAsked())
                            +(saved_reservation.getEndAsked() != null ? " - "+simpleDateFormat.format(saved_reservation.getEndAsked()) : "")
                            +" poslata rezevacija od strane korisnika "
                            +reservationFor.getUserName()+": "+reservationFor.getFirstName()+" "+reservationFor.getLastName()+".\n\n"
                            +"Srdačan pozdrav,\nFonClassRoomBookingSystem\nAleksandar Janković";

                    emailService.sendSimpleMessage(u.getEmail(), subject, email_for_admins);
                    System.out.println("EMAIL TO ADMIN: "+u);
                    System.out.println("EMAIL SUBJECT TO ADMIN: "+subject);
                    System.out.println("EMAIL BODY TO ADMIN: "+email_for_admins);
                });

        return saved_reservation;
    }

    @Transactional
    public Reservation approveReservation(Reservation reservation, String modifiedBy) {
        if(reservation == null)
            throw new IllegalArgumentException("Reservation has not been approved!");

        if(!reservationRepository.findByClassRoomAndBeginningApprovedAfterAndEndApprovedBefore(
                reservation.getClassRoom().getId(), reservation.getBeginningAsked(), reservation.getEndAsked()
        ).isEmpty()) {
            System.out.println("This period is is already taken!");
            throw new IllegalArgumentException("This period is is already taken!");
        }

        reservation.setUsm(modifiedBy);
        reservation.setDtm(LocalDateTime.now());
        reservation.setBeginningApproved(reservation.getBeginningAsked());
        reservation.setEndApproved(reservation.getEndAsked());

        ReservationStatus reservationStatus = reservationStatusRepository.findByDscIgnoreCase("APPROVED").get();
        if(reservationStatus == null)
            throw new IllegalArgumentException("Reservation has not been approved!");
        
        reservation.setReservationStatus(reservationStatus);
        Reservation saved_reservation = reservationRepository.save(reservation);
        if(saved_reservation == null)
            throw new IllegalArgumentException("Reservation has not been approved!");

        User reservationFor = saved_reservation.getReservationForUser();
        User approvedBy = userRepository.findByUserName(modifiedBy).get();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy. HH:mm");

        String subject = "Uspešna rezervacija: "+saved_reservation.getClassRoom().getDsc()+" "+simpleDateFormat.format(saved_reservation.getBeginningApproved());

        String emailBody = "Poštovani "+reservationFor.getFirstName()+" "+reservationFor.getLastName()+",\n\n"
                            +"Želimo da Vas obavestimo da je rezervacija za kabinet \""+saved_reservation.getClassRoom().getDsc()+"\" u periodu od "
                            +simpleDateFormat.format(saved_reservation.getBeginningApproved())
                            +(saved_reservation.getEndApproved() != null ? " - "+simpleDateFormat.format(saved_reservation.getEndApproved()) : "")
                            +" odobrena od strane "+ approvedBy.getFirstName()+" "+approvedBy.getLastName()+".\n\n"
                            +"Srdačan pozdrav,\nFonClassRoomBookingSystem\nAleksandar Janković";

        emailService.sendSimpleMessage(reservationFor.getEmail(), subject, emailBody);
        if(!reservationFor.equals(approvedBy))
            emailService.sendSimpleMessage(approvedBy.getEmail(), subject, emailBody);

        return saved_reservation;
    }

    @Transactional
    public Reservation rejectReservation(Reservation reservation, String modifiedBy) {
        if(reservation == null)
            throw new IllegalArgumentException("Reservation has not been approved!");

        reservation.setUsm(modifiedBy);
        reservation.setDtm(LocalDateTime.now());
        reservation.setBeginningApproved(null);
        reservation.setEndApproved(null);

        ReservationStatus reservationStatus = reservationStatusRepository.findByDscIgnoreCase("REJECTED").get();
        if (reservationStatus == null)
            throw new IllegalArgumentException("Rezervacija nije obijena!");
        
        reservation.setReservationStatus(reservationStatus);
        Reservation saved_reservation = reservationRepository.save(reservation);
        if(saved_reservation == null)
            throw new IllegalArgumentException("Rezervacija nije obijena!");

        User reservationFor = saved_reservation.getReservationForUser();
        User rejectedBy = userRepository.findByUserName(modifiedBy).get();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy. HH:mm");

        String subject = "Obijena rezervacija: "+saved_reservation.getClassRoom().getDsc()+" "+simpleDateFormat.format(saved_reservation.getBeginningAsked());

        String emailBody = "Poštovani "+reservationFor.getFirstName()+" "+reservationFor.getLastName()+",\n\n"
                +"Želimo da Vas obavestimo da je na žalost rezervacija za kabinet \""+saved_reservation.getClassRoom().getDsc()+"\" u periodu od "
                +simpleDateFormat.format(saved_reservation.getBeginningAsked())
                +(saved_reservation.getEndAsked() != null ? " - "+simpleDateFormat.format(saved_reservation.getEndAsked()) : "")
                +" obijena.\n\n"
                +"Srdačan pozdrav,\nFonClassRoomBookingSystem\nAleksandar Janković";
//        System.out.println("EMAIL TO: "+reservationFor.getEmail());
//        System.out.println("EMAIL SUBJECT: "+subject);
//        System.out.println("EMAIL emailBody: "+emailBody);
        emailService.sendSimpleMessage(reservationFor.getEmail(), subject, emailBody);

        if(!reservationFor.equals(rejectedBy))
            emailService.sendSimpleMessage(rejectedBy.getEmail(), subject, emailBody);

        System.out.println(saved_reservation);
        return saved_reservation;
    }

//    @Transactional
//    public Reservation saveReservationRest(Reservation reservation) {
//        if(reservation.getUsc() == null || reservation.getUsc().isEmpty()
//                || reservation.getDsc() == null || reservation.getDsc().isEmpty()
//                || reservation.getBeginningAsked() == null || reservation.getEndAsked() == null
////            || reservation.getClassRoom() == null || reservation.getReservationType() == null
////            || reservation.getReservationForUser() == null
//        ){ throw new IllegalArgumentException("Reservation has not been submitted!"); }
//
//        Reservation saved_reserv = reservationRepository.save(reservation);
//        if(saved_reserv != null && saved_reserv.getId() != null)
//            return saved_reserv;
//
//        throw new IllegalArgumentException("Reservation has not been submitted!");
//    }
}
