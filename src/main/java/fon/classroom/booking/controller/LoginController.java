package fon.classroom.booking.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView displayLoginPage(@RequestParam(required = false) String error,
                                        @RequestParam(required = false) String logout ) {

        ModelAndView modelAndView = new ModelAndView("/login.html");

        String errorMessage = null;
        if(error != null)
            errorMessage = "Username or password is incorrect!";
        if(logout != null)
            errorMessage = "You have been successfully logged out!";

        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String displayLogoutPage(HttpServletRequest request, HttpServletResponse response/*, Authentication auth*/) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null)
            new SecurityContextLogoutHandler().logout(request, response, auth);
//        return "redirect:/login?logout=true";
        return "redirect:/login";
    }
}
