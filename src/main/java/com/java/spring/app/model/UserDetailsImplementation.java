package com.java.spring.app.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UserDetailsImplementation implements UserDetails {

    private Set<GrantedAuthority> authorities = new HashSet<>();
    private String password;
    private String username;

    public UserDetailsImplementation(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();

        Iterable<Role> result = user.getRoles();
        Iterator iter = result.iterator();
        while (iter.hasNext()) {
            SimpleGrantedAuthority auth = new SimpleGrantedAuthority(((Role) iter.next()).getName());
            this.authorities.add(auth);
        }
        System.out.println(username);
        System.out.println(password);
        System.out.println(authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
