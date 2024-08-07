package com.scmnew.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scmnew.entities.Providers;
import com.scmnew.entities.User;
import com.scmnew.helper.AppConstant;
import com.scmnew.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    @Autowired
    private UserRepo userRepo;

    Logger logger= LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
       
           logger.info("OnAuthenticationSuccessHandler");

        //    here 1st we have to identify the provider i.e by which provider user get  login

        var oauth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;

          String authorizedClientId= oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
          logger.info(authorizedClientId);

          var oauthUser=(DefaultOAuth2User)authentication.getPrincipal();

          oauthUser.getAttributes().forEach((key,value)->{
                     logger.info(key+ " : " +value);
          });

          User user= new User();
          user.setUserId(UUID.randomUUID().toString());
          user.setRoleList(List.of(AppConstant.ROLE_USER));
          user.setEmailVerified(true);
          user.setEnabled(true);
          user.setPassword("password");
          


          if(authorizedClientId.equalsIgnoreCase("google")){

             //google
             user.setEmail(oauthUser.getAttribute("email").toString());
             user.setProfilePic(oauthUser.getAttribute("picture").toString());
             user.setName(oauthUser.getAttribute("name").toString());
             user.setProviderUserId(oauthUser.getName());
             user.setProvider(Providers.GOOGLE);
             user.setAbout("This account is created using google");

          }

          else if(authorizedClientId.equalsIgnoreCase("github")){
           //github

           String email=oauthUser.getAttribute("email")!=null ?
            oauthUser.getAttribute("email").toString():oauthUser.getAttribute("login").toString()+"@gmail.com"; 

            String picture=oauthUser.getAttribute("avatar_url").toString();
            String name=oauthUser.getAttribute("login").toString();
            String providerUserId=oauthUser.getName();

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderUserId(providerUserId);
            user.setProvider(Providers.GITHUB);
            user.setAbout("This account is created using github");
          }

          else if(authorizedClientId.equalsIgnoreCase("linkedin")){

            //linkedin

          }
          else{

            logger.info("OAuthAuthenticationSuccessHandler :Unknown provider");

          }
          
        //    save the user
        User user2 = userRepo.findByEmail(user.getEmail()).orElse(null);
        if(user2==null){
            userRepo.save(user);
            System.out.println("user savedd to " + user.getEmail());
        }

           

       
     /*   DefaultOAuth2User user= (DefaultOAuth2User)authentication.getPrincipal();

    //    line number 31 to 37 only to get data from email login(we just understand how to get data)
    //    logger.info(user.getName());

    //    user.getAttributes().forEach((key,value)->{
    //         logger.info("{}: {}",key,value);
    //    });
    //    logger.info(user.getAuthorities().toString());


        //    data save to database
        String email= user.getAttribute("email").toString();
        String name= user.getAttribute("name").toString();
        String picture=user.getAttribute("picture").toString();


        //create user and save to database

        User user1= new User();
        user1.setName(name);
        user1.setEmail(email);
        user1.setProfilePic(picture);
        user1.setPassword("password");
        user1.setUserId(UUID.randomUUID().toString());
        user1.setProvider(Providers.GOOGLE);
        user1.setEnabled(true);
        user1.setEmailVerified(true);
        user1.setProviderUserId(user.getName());
        user1.setRoleList(List.of(AppConstant.ROLE_USER));
        user1.setAbout("This account is created using google");

        User user2=userRepo.findByEmail(email).orElse(null);

        if(user2==null){
            userRepo.save(user1);

            logger.info("User saved :" + email);
        }
       
*/

           new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

    }



}
