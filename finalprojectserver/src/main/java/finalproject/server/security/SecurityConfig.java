package finalproject.server.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static finalproject.server.constants.SQL.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource db;

    @Autowired
    private JwtRequestFilter filter;

    @Override
    public void configure(WebSecurity web) throws Exception {
     
      web.ignoring().antMatchers("/game");  
    }  

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // set your configuration on the auth object
        auth.jdbcAuthentication()
            .dataSource(db)
            .usersByUsernameQuery(SQL_VERIFY_USER)
            .authoritiesByUsernameQuery(SQL_CHECK_USER_AUTHORITY);
    }

    @Override
	protected void configure(HttpSecurity http) throws Exception {
        // Enable CORS and disable CSRF, otherwise, 
        // Spring Security blocks all POST request
        http = http.cors().and().csrf().disable();


		http.authorizeRequests()
            .antMatchers("/authenticate", "/game", "/api/toptengames").permitAll()
            .antMatchers("/api/gamedetails/**").hasRole("USER")
            .antMatchers("/hello").hasRole("USER")
            // .anyRequest().authenticated()
            .and()
			.exceptionHandling().and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	}

    // Set password encoding schema
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // Create overriding bean for AuthenticationManager for use in AuthController
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    
}
