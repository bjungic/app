package com.java.spring.app.filters;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IPFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("IP:   " + servletRequest.getRemoteAddr());
        System.out.println("############################################################");
        servletResponse.addHeader("Content-Type", "application/JSON");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}