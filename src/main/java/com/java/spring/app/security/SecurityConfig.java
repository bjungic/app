package com.java.spring.app.security;

import com.java.spring.app.filters.IPFilter;
import com.java.spring.app.filters.JWTAuthFilter;
import com.java.spring.app.filters.JWTVerifyFilter;
import com.java.spring.app.jwt.TokenConfig;
import com.java.spring.app.services.UsersDetailsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity(debug = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private final DataSource dataSource;

    private final UsersDetailsServiceImplementation usersDetailsServiceImplementation;

    private final TokenConfig tokenConfig;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, DataSource dataSource, UsersDetailsServiceImplementation usersDetailsServiceImplementation, TokenConfig tokenConfig) {
        this.passwordEncoder = passwordEncoder;
        this.dataSource = dataSource;
        this.usersDetailsServiceImplementation = usersDetailsServiceImplementation;
        this.tokenConfig = tokenConfig;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usersDetailsServiceImplementation);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JWTAuthFilter(authenticationManager(), tokenConfig))
                .addFilterAfter(new JWTVerifyFilter(tokenConfig), JWTAuthFilter.class)
                .authorizeRequests()
                .antMatchers("/", "login", "/file", "/file/**").permitAll()
                .antMatchers("/d").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/**").hasAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .addFilterAfter(new IPFilter(), SecurityContextPersistenceFilter.class)
//                .httpBasic()
        ;
    }
}
