package com.olx.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManager();

	}


	// authorization
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeRequests().antMatchers("/user").hasAnyRole("USER", "ADMIN").antMatchers("/admin")
				.hasRole("ADMIN").antMatchers("/user/authenticate").permitAll().and().formLogin();
	}

}
