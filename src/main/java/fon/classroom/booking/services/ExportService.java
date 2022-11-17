package fon.classroom.booking.services;

import fon.classroom.booking.model.ClassRoom;
import fon.classroom.booking.model.Reservation;
import fon.classroom.booking.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
@Service
public class ExportService {

    @Autowired
    ReservationRepository reservationRepository;

    public void exportToCSV(Integer classRoomID, Date dateFrom, Date dateTo, PrintWriter printWriter) {
        if(classRoomID == null)
            throw new IllegalArgumentException("Illegal input parameters!");

        List<Reservation> reservations = new ArrayList<>();
        if(dateFrom != null && dateTo != null)
            reservations = reservationRepository.findByClassRoomAndBeginningApprovedAfterAndEndApprovedBefore(classRoomID, dateFrom, dateTo);
        else
            reservations = reservationRepository.findByClassRoomIDApproved(classRoomID);
        try(CSVPrinter printer = new CSVPrinter(printWriter, CSVFormat.EXCEL)) {
            printer.printRecord("ReservationID", "Reservation Name", "Type", "Beginning", "End", "For User Email", "For User", "Approved By");
            Stream<Reservation> reservationStream = reservations.stream();
            reservationStream.forEach(
                    (e) -> {
                        try {
                            printer.printRecord(
                                    e.getId(), e.getDsc(), e.getReservationType().getDsc()
                                    , e.getBeginningApproved(), e.getEndApproved()
                                    , e.getReservationForUser().getUserName()
                                    , e.getReservationForUser().getFirstName()+" "+e.getReservationForUser().getLastName()
                                    , e.getUsm()
                            );
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("System was not able to create csv calendar!");
        }
    }

    public void exportToCSV(String userName, Date dateFrom, Date dateTo, PrintWriter printWriter) {
        if(userName == null)
            throw new IllegalArgumentException("Illegal input parameters!");

        List<Reservation> reservations = reservationRepository.findByUserApproved(userName);
        try(CSVPrinter printer = new CSVPrinter(printWriter, CSVFormat.EXCEL)) {
            printer.printRecord("ReservationID", "Reservation Name", "Type", "Beginning", "End", "For User Email", "For User", "Approved By");
            Stream<Reservation> reservationStream = reservations.stream();
            reservationStream.forEach(
                    (e) -> {
                        try {
                            printer.printRecord(
                                    e.getId(), e.getDsc(), e.getReservationType().getDsc()
                                    , e.getBeginningApproved(), e.getEndApproved()
                                    , e.getReservationForUser().getUserName()
                                    , e.getReservationForUser().getFirstName()+" "+e.getReservationForUser().getLastName()
                                    , e.getUsm()
                            );
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("System was not able to create csv calendar!");
        }
    }
}
