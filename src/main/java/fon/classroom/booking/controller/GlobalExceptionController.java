package fon.classroom.booking.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice // allows us to handle exceptions across the whole app
public class GlobalExceptionController {

    /*
    * it is possible to handle multiple specific exceptions
    * @ExceptionHandler({NullPointerException.class, IOException.class})
    * */
    @ExceptionHandler(Exception.class) // annotates handler for a specific exception class
    public ModelAndView exceptionHandler(Exception exception) {
        ModelAndView errorPage = new ModelAndView("error.html");
        errorPage.addObject("errorMessage", exception.getMessage());
        return errorPage;
    }
}
