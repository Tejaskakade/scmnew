package com.scmnew.config;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.scmnew.services.impl.SecurityCustomUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Configuration
public class SecurityConfig {    //below code is only for  Inmemorydatabase

    //user creation and login using java code with inMemory service (we just write this code for understanding)
    //@Bean

    // @Bean
    // public UserDetailsService userDetailsService(){

    //      UserDetails user1= User
    //      .withDefaultPasswordEncoder()
    //      .username("admin123")
    //      .password("admin123")
    //      .roles("ADMIN", "USER")
    //      .build();

    //      UserDetails user2=User
    //      .withDefaultPasswordEncoder() 
    //      .username("user123")
    //      .password("password")
    //      //roles null
    //      .build();

    //     var inMemoryUserDetailsManager= new InMemoryUserDetailsManager(user1,user2);
    //     return inMemoryUserDetailsManager;
    // }

       @Autowired
     private SecurityCustomUserDetailService userDetailService;

     @Autowired
     private OAuthAuthenticationSuccessHandler handler;


     //configuration of authentication provider for spring security
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider  daoAuthenticationProvider= new DaoAuthenticationProvider();

        //user detail service object
        daoAuthenticationProvider.setUserDetailsService(userDetailService);

        //password encoder service object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider; 
    }


    //this bean is used for which page is access by which user
     @Bean   
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        //configuratiion about url about which is public or which is private
        httpSecurity.authorizeHttpRequests(authorize->{
            // authorize.requestMatchers("/home","/register","/services").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });


        //form default login
        // if we want to channge anything about form login then we can do  here 
        httpSecurity.formLogin(formLogin->{
            formLogin.loginPage("/login")
            .loginProcessingUrl("/authenticate");   //the login form processing is done on "/authenticate"

            formLogin.successForwardUrl("/user/profile");   //the form forward on this  url
            // formLogin.failureForwardUrl("/login?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
        

            // formLogin.failureHandler(new AuthenticationFailureHandler() {

            // @Override
            // public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            //         AuthenticationException exception) throws IOException, ServletException {
            //     // TODO Auto-generated method stub
            //     throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationFailure'");
            // }
            // });

            // formLogin.successHandler(new AuthenticationSuccessHandler() {

            //     @Override
            //     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            //             Authentication authentication) throws IOException, ServletException {
            //         // TODO Auto-generated method stub
            //         throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
            //     }
                
            // });


        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.logout(logoutForm->{
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });


        // oauth2 configuration

        httpSecurity.oauth2Login(oauth ->{
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });


        return httpSecurity.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
