package com.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
 
import com.filter.JwtFilter;
import com.service.CustomUserDetailService;

@SpringBootApplication(scanBasePackages="com.controller")
@EntityScan("com.model")
@EnableJpaRepositories("com.DAO")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	@DependsOn("userDetailsService")
	public DaoAuthenticationProvider daoAuthenticationProvider() {
	    CustomUserDetailService service=userDetailsService();
 
	  
	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	    
	    provider.setUserDetailsService(service);
	    provider.setPasswordEncoder(passwordEncoder());
	    return provider;
	}
 
	@Bean
	public CustomUserDetailService userDetailsService() {
		
	
	    return new CustomUserDetailService(); // Implement your own user details service
	}
 
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder(); // Use a password encoder of your choice
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    System.out.println("invoked");
		http
	        .csrf().disable() // Disable CSRF protection
	        
	        .authorizeRequests()
	            .requestMatchers("/register").permitAll()
	            
	            .requestMatchers("/getitems").hasRole("USER")
	            
	             .requestMatchers("/getinfo").permitAll()
	             
	             .requestMatchers(HttpMethod.PUT,"/updateitem").hasRole("ADMIN")
	             .requestMatchers(HttpMethod.DELETE, "/deleteitem/\\d+").hasRole("ADMIN")
	            .requestMatchers("/findbyname/**").hasAnyRole("USER","ADMIN")
	            .requestMatchers(HttpMethod.POST,"/additem").hasRole("ADMIN")
	            .anyRequest().authenticated()
	            .and()
	             .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
	           
	        .sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            .and()
	            .sessionManagement().disable()
	            .authenticationManager(new ProviderManager(daoAuthenticationProvider()));
	        
	
	        
	    return http.build();
	}
}