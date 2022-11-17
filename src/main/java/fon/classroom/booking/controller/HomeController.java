package fon.classroom.booking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping(value = {"", "/", "/home", "/dashboard"})
    public String displayHomePage() {
        return "home.html";
    }
}
