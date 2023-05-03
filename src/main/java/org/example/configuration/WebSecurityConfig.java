package org.example.configuration;

import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()//Отключает CSRF Protection, поскольку она не нужна для API
                .authorizeRequests()
                .antMatchers("/", "/home", "/api/authorization/registration","/api/authorization/login")//исключение, какие запросы\страницы
                .permitAll()//будут разрешены всем
                .anyRequest()
                .authenticated()//Декларирует, что все запросы к любой конечной точке должны быть авторизованы, иначе они должны быть отклонены.
                .and()
                .formLogin()
                .loginPage("/api/authorization/login").permitAll()//разрешить всем доступ к странице логинизации
                .defaultSuccessUrl("/api/doctors", true)//по умолчанию страница
                .and()
                .rememberMe()
                .tokenValiditySeconds((int) TimeUnit.MINUTES.toSeconds(5))
                .key("somesecretkey")
        ;
    }
}
