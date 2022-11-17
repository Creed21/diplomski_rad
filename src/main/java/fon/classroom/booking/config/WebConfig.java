package fon.classroom.booking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/dashboard").setViewName("dashboard");
        registry.addViewController("/classRooms").setViewName("classRooms");
        registry.addViewController("/classRoom/create").setViewName("classRoomCreate");
        registry.addViewController("/classRoom/edit").setViewName("classRoomEdit");
        registry.addViewController("/reservations").setViewName("reservations");
        registry.addViewController("/myProfile").setViewName("myProfile");
        registry.addViewController("/users").setViewName("users");
        registry.addViewController("/user/create").setViewName("userCreate");

    }
}
