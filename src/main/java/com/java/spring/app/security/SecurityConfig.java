package com.java.spring.app.security;

import com.java.spring.app.filters.IPConfig;
import com.java.spring.app.services.UsersDetailsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    HttpServletRequest httpServletRequest;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    DataSource dataSource;

    @Autowired
    UsersDetailsServiceImplementation usersDetailsServiceImplementation;

    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usersDetailsServiceImplementation);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/d").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/**").hasAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .addFilterAfter(new IPConfig(), BasicAuthenticationFilter.class)
                .httpBasic();

//        //propusta sve
//        http
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                .anyRequest()
//                .anonymous()
//                .and()
//                .httpBasic();
    }
}
