package fon.classroom.booking.controller;

import fon.classroom.booking.model.ClassRoom;
import fon.classroom.booking.model.Reservation;
import fon.classroom.booking.model.ReservationType;
import fon.classroom.booking.model.User;
import fon.classroom.booking.services.ClassRoomService;
import fon.classroom.booking.services.ReservationService;
import fon.classroom.booking.services.ReservationTypeService;
import fon.classroom.booking.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ClassRoomService classRoomService;
    @Autowired
    private ReservationTypeService reservationTypeService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/reservations")
    public ModelAndView displayReservationPage(@RequestParam(required = false) Integer classRoomID) {
        ModelAndView modelAndView = new ModelAndView("reservations.html");
        List<Reservation> reservations = new ArrayList<>();
        try {
            if(classRoomID != null)
                reservations = reservationService.findByClassRoomID(classRoomID);
            else
                reservations = reservationService.findAll();
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", e.getMessage());
        }
        if(reservations.isEmpty())
            modelAndView.addObject("errorMessage", "No result.");

        modelAndView.addObject("reservations", reservations);
        return modelAndView;
    }

    @GetMapping(value = "/makeReservation")
    public ModelAndView displayMakeReservationPage(@RequestParam(required = false) Integer classRoomId) {
        ModelAndView modelAndView = new ModelAndView("makeReservation.html");
        List<ClassRoom> classRooms = new ArrayList<>();
        List<ReservationType> reservationTypes = new ArrayList<>();
        List<User> users = new ArrayList<>();
        try {
            if(classRoomId != null)
                classRooms.add(classRoomService.findById(classRoomId).get());
            else
                classRooms = classRoomService.findAll();
            reservationTypes = reservationTypeService.findAll();
            users = userService.findAll();
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", e.getMessage());
        }

        modelAndView.addObject("classRooms", classRooms);
        modelAndView.addObject("reservationTypes", reservationTypes);
        modelAndView.addObject("users", users);
        modelAndView.addObject("reservation", new Reservation());

        return modelAndView;
    }

    @PostMapping("/makeReservation")
    public ModelAndView makeReservation(@ModelAttribute Reservation reservation, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("reservations.html");
        try {
            System.out.println("makeReservation: "+authentication.getName());
            Reservation saved_reservation = reservationService.saveReservation(reservation, authentication.getName());
            modelAndView.addObject("successMessage", "Rezervacija je sacuvana!");
            List<Reservation> reservations = new ArrayList<>();
            reservations.add(saved_reservation);
            modelAndView.addObject("reservations", reservations);
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping("/approveReservation")
    public ModelAndView approveReservation(@RequestParam Integer reservation, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("reservations.html");
        try {
            Reservation r = reservationService.findById(reservation).get();
            reservationService.approveReservation(r, authentication.getName());
            List<Reservation> reservations = new ArrayList<>();
            reservations.add(r);
            modelAndView.addObject("reservations", reservations);
            modelAndView.addObject("successMessage", "Rezervacija je prihvacena!");
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", e.getMessage());
        }

        return modelAndView;
    }

    @PostMapping("/rejectReservation")
    public ModelAndView rejectReservation(@RequestParam Integer reservation, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("reservations.html");
        try {
            Reservation r = reservationService.findById(reservation).get();
            reservationService.rejectReservation(r, authentication.getName());
            List<Reservation> reservations = new ArrayList<>();
            reservations.add(r);
            modelAndView.addObject("reservations", reservations);
            modelAndView.addObject("errorMessage", "Rezervacija je odbijena!");
        } catch (Exception e) {
            modelAndView.addObject("errorMessage", e.getMessage());
        }

        return modelAndView;
    }

}
