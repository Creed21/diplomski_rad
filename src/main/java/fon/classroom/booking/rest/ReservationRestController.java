package fon.classroom.booking.rest;

import fon.classroom.booking.model.Reservation;
import fon.classroom.booking.services.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/reservation", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class ReservationRestController {

    @Autowired
    ReservationService reservationService;

    @GetMapping("/findAll")
    @ResponseBody
//    public List<Reservation> getAllReservations(@RequestBody(required = false) ClassRoom classRoom) {
    public List<Reservation> getAllReservations() {
        List<Reservation> r = reservationService.findAll();
        System.out.println(r);
        return r;
//        return "OK";
    }

//    @PostMapping("/submit")
//    @ResponseBody
//    public List<Reservation> submitReservation(@RequestBody Reservation reservation) {
//        try {
//            Reservation saved_r = reservationService.saveReservationRest(reservation);
//            List<Reservation> list = new ArrayList<>();
//            list.add(saved_r);
//            return list;
//        } catch (Exception e) {
////            return e.getMessage();
//        }
//        return null;
//    }
}
