package fon.classroom.booking.controller;

import fon.classroom.booking.model.ClassRoom;
import fon.classroom.booking.model.ClassRoomType;
import fon.classroom.booking.services.ClassRoomService;
import fon.classroom.booking.services.ClassRoomTypeService;
import fon.classroom.booking.services.ExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
public class ClassRoomController {

    @Autowired
    ClassRoomService classRoomService;
    @Autowired
    ClassRoomTypeService classRoomTypeService;
    @Autowired
    ExportService exportService;

    @GetMapping(value = "/classRooms")
    public ModelAndView displayClassRooms(@ModelAttribute ClassRoom classRoom, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("classrooms.html");
        try {
            if (result.hasErrors()) {
                throw new IllegalArgumentException("Invalid input for classRoom");
            }
            List<ClassRoom> classRooms;
            if(classRoom != null) {
//                classRooms = new ArrayList<>();
//                classRooms.add(classRoomService.findById(classRoom.getId()).get());
                classRooms = classRoomService.findAll(classRoom);
                modelAndView.addObject("classRooms", classRooms);
            } else {
                classRooms = classRoomService.findAll();
                modelAndView.addObject("classRooms", classRooms);
            }
        } catch (Exception e) {
            modelAndView.addObject("classRooms", new ArrayList<ClassRoom>());
            modelAndView.addObject("errorMessage", e.getMessage());
            System.out.println(e.getMessage());
        }
        List<ClassRoomType> classRoomTypes = classRoomTypeService.findAll();
        modelAndView.addObject("classRoomTypes", classRoomTypes);

        return modelAndView;
    }

    @GetMapping(value = "/createClassRoom")
    public ModelAndView displayCreateClassRoomPage() {
        ModelAndView modelAndView = new ModelAndView("createClassRoom.html");
        List<ClassRoomType> classRoomTypes = new ArrayList<>();
        try {
            classRoomTypes = classRoomTypeService.findAll();
            modelAndView.addObject("classRoomTypes", classRoomTypes);
            modelAndView.addObject("newClassRoom", new ClassRoom());
        } catch (Exception e) {
            modelAndView.addObject("classRoomTypes", classRoomTypes);
            modelAndView.addObject("errorMessage", e.getMessage());
            System.out.println(e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping(value = "/createdClassRoom")
    @ResponseBody
    public ModelAndView createClassRoom( @ModelAttribute ClassRoom newClassRoom, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("createClassRoom.html");
        try {
            ClassRoom savedClassRoom = classRoomService.saveClassRoom(newClassRoom, authentication.getName());
            modelAndView.addObject("newClassRoom", savedClassRoom);
            modelAndView.addObject("newClassRoomType", classRoomTypeService.findById(savedClassRoom.getClassRoomType()).get());
            modelAndView.addObject("successMessage", "Sala je sačuvana!!");
        } catch (Exception e) {
            modelAndView.addObject("classRoomTypes", new ArrayList<ClassRoomType>());
            modelAndView.addObject("errorMessage", e.getMessage());
            System.out.println(e.getMessage());
        }
        return modelAndView;
    }

    @RequestMapping(path = "/exportCalendarForClassRoom")
    public void exportCalendarForClassRoom(HttpServletResponse servletResponse
                                    , @RequestParam Integer classRoomID
                                    , @RequestParam(required = false) Date dateFrom
                                    , @RequestParam(required = false) Date dateTo
    ) {
        if(classRoomID == null)
            throw new IllegalArgumentException("You must supply classRoomID!");

        String classRoomDsc = classRoomService.findById(classRoomID).get().getDsc();

        servletResponse.setContentType("text/csv");
        servletResponse.addHeader(
                "Content-Disposition",
                "attachment; filename=\"calendarForClassRoom"
                                        +classRoomDsc
                                        +(dateFrom != null ? " _from_"+dateFrom.getDay()+"_"+(dateFrom.getMonth()+1)+"_"+dateFrom.getYear() : "")
                                        +(dateTo != null ? "_to_"+dateTo.getDay()+"_"+(dateTo.getMonth()+1)+"_"+dateTo.getYear() : "")
                                        +".csv\"");
        try {
            exportService.exportToCSV(classRoomID, dateFrom, dateTo, servletResponse.getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
