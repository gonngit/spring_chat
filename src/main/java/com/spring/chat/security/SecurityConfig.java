package com.spring.chat.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.spring.chat.controller.StompHandler;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
	        .csrf(csrf -> csrf.disable())
	        .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())) // iframe을 같은 출처에서 허용
	        .authorizeHttpRequests(authorize -> authorize
	            .requestMatchers("/chat/**").hasRole("USER")
	            .anyRequest().permitAll())
	        .formLogin(formLogin -> formLogin
                .defaultSuccessUrl("/chat/room", true) // redirect to chat room when login is successful
                .permitAll());

        return http.build();
    }
    
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // In-Memory user details
        UserDetails user1 = User.withUsername("one")
            .password(passwordEncoder.encode("qwer"))
            .roles("USER")
            .build();

        UserDetails user2 = User.withUsername("two")
            .password(passwordEncoder.encode("asdf"))
            .roles("USER")
            .build();

        UserDetails guest = User.withUsername("guest")
            .password(passwordEncoder.encode("zxcv"))
            .roles("GUEST")
            .build();

        return new InMemoryUserDetailsManager(user1, user2, guest);
    }
    
    
    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

