package com.movierepo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "app_user")
@Component
public class User implements UserDetails {
    @Id
    private String username;
    private String password;


    @JsonIgnore @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore @Override
    public boolean isEnabled() {
        return true;
    }

    // Setter method with @JsonProperty
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Data> dataList;
}
