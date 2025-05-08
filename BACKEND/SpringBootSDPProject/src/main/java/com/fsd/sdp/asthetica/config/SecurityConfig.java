package com.fsd.sdp.asthetica.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	@Autowired
	CustomOAuth2SuccessHandler successHandler;
	
	 @Bean
	 public PasswordEncoder passwordEncoder() {
	     return new BCryptPasswordEncoder();
	 }
	 @Bean
	 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	     http
	         .csrf(csrf -> csrf.disable())
	         .authorizeHttpRequests(auth -> auth
	             .requestMatchers("/", "/user/**", "/admin/**", "/seller/**", "/customer/**").permitAll()
	             .requestMatchers("/customer/adduseroauth").authenticated()
	             .anyRequest().authenticated()
	         )
	         .formLogin(form -> form.disable()) // Disable default login page
	         .httpBasic(httpBasic -> httpBasic.disable())
	         .oauth2Login(oauth2 -> oauth2
	                .permitAll()
	                .successHandler(successHandler)
	            );
	     return http.build();
	 }

}
