package fon.classroom.booking.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@ComponentScan("fon.classroom.booking.security")
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
//            .mvcMatchers("/login").permitAll()
            .mvcMatchers("/css/**").permitAll()
            .mvcMatchers("/images/**").permitAll()
            .mvcMatchers("/js/**").permitAll()
            .mvcMatchers("/webfonts/**").permitAll()
            .mvcMatchers("/error").permitAll()
                // permissions
            .mvcMatchers("/createUser").hasRole("ADMIN")
            .mvcMatchers("/saveUser").hasRole("ADMIN")
            .mvcMatchers("/approveReservation").hasRole("ADMIN")
            .mvcMatchers("/rejectReservation").hasRole("ADMIN")
            .mvcMatchers("/createClassRoom").hasRole("ADMIN")
            .mvcMatchers("/createdClassRoom").hasRole("ADMIN")
                // permissions end
            .anyRequest().authenticated()
            .and().formLogin()
            .loginPage("/login").permitAll()
//                .defaultSuccessUrl("/home")
//                .failureUrl("/login?error=true").permitAll()
            .and().httpBasic()
            .and().logout()
                .clearAuthentication(true)
                .logoutUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            ;
    }

}
