package com.Connectify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.Connectify.service.login.UserDetailsServiceImpl;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	

     
    @Bean
    public UserDetailsService userDetailsServicee() {
        return new UserDetailsServiceImpl();
    }
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
    	BCryptPasswordEncoder password=new BCryptPasswordEncoder();
        return password;
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsServicee());
        authProvider.setPasswordEncoder(passwordEncoder());
        
         
        return authProvider;
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

	// ZAS keep it simple - No need for custom login page
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		

		http.authorizeRequests()
			.antMatchers("/users/show_form", "/users/register*", "/")
			.permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin().defaultSuccessUrl("/feed")
			.and()
			.logout().permitAll()
			.logoutSuccessUrl("/index.html");
		
		http.cors().and().csrf().disable();
					
	}

		
}