package fon.classroom.booking.controller;

import fon.classroom.booking.model.User;
import fon.classroom.booking.services.ExportService;
import fon.classroom.booking.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ExportService exportService;

    @GetMapping("/user")
    public ModelAndView displayUserPage(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("user.html");
        try {
            User user = userService.findByUserName(authentication.getName());
            modelAndView.addObject("user", user);
        } catch (Exception e) {
            modelAndView.addObject("user", new User());
            modelAndView.addObject("errorMessage", e.getMessage());
            System.out.println(e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/createUser")
    public ModelAndView displayCreateUserPage() {
        ModelAndView modelAndView = new ModelAndView("usercreate.html");
        modelAndView.addObject("newUser", new User());
        return modelAndView;
    }

    @PostMapping(value = "/saveUser")
    @ResponseBody
    public ModelAndView createUser(@ModelAttribute User newUser, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("userCreate.html");
        try {
            User savedUser = userService.saveUser(newUser, authentication.getName());
            modelAndView.addObject("newUser", savedUser);
            modelAndView.addObject("successMessage", "Korisnik je uspesno sacuvan!");
        } catch (Exception e) {
            modelAndView.addObject("newUser", newUser);
            modelAndView.addObject("errorMessage", e.getMessage());
            System.out.println(e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping(value = "/updateUser")
    public ModelAndView updateUser(@ModelAttribute User user, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("user.html");
        try {
            User savedUser = userService.updateUser(user, authentication.getName());
            modelAndView.addObject("user", savedUser);
            modelAndView.addObject("successMessage", "Korisnik je uspesno izmenjen!");
        } catch (Exception e) {
            modelAndView.addObject("user", user);
            modelAndView.addObject("errorMessage", e.getMessage());
            System.out.println(e.getMessage());
        }
        return modelAndView;
    }

    @RequestMapping(path = "/exportCalendarForUser")
    public void exportCalendarForUser(HttpServletResponse servletResponse
            , @RequestParam(required = false) Date dateFrom
            , @RequestParam(required = false) Date dateTo
            , Authentication authentication
    ) {
        String userName = authentication.getName();

        servletResponse.setContentType("text/csv");
        servletResponse.addHeader(
                "Content-Disposition",
                "attachment; filename=\"calendarForClassRoom"
                        +userName
                        +(dateFrom != null ? " from_"+dateFrom.getDay()+"_"+(dateFrom.getMonth()+1)+"_"+dateFrom.getYear() : "")
                        +(dateTo != null ? "_to_"+dateTo.getDay()+"_"+(dateTo.getMonth()+1)+"_"+dateTo.getYear() : "")
                        +".csv\"");
        try {
            exportService.exportToCSV(userName, dateFrom, dateTo, servletResponse.getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
