package com.java.spring.app.security;

import com.java.spring.app.filters.IPFilter;
import com.java.spring.app.services.UsersDetailsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity(debug = false)
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
        System.out.println("AuthManager");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/d").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/**").hasAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .addFilterAfter(new IPFilter(), SecurityContextPersistenceFilter.class)
                .httpBasic()
//                .and()
//                .addFilterBefore(new JWTAuthFilter(), SecurityContextPersistenceFilter.class)
        ;

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
