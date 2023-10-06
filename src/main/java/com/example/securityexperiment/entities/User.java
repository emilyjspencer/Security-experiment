package com.example.securityexperiment.entities;


import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.example.securityexperiment.repositories.RoleRepository;
import com.example.securityexperiment.repositories.UserRepository;
import com.example.securityexperiment.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer userId;
    @Column(unique=true)
    private String username;
    private String password;

    private String first_name;

    private String last_name;

    private BigInteger phone_number;

    private String email;

    private String home_office;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name="user_role",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private Set<Role> authorities;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public User() {
        super();
        authorities = new HashSet<>();
    }


    public User(Integer userId, String username, String password, Set<Role> authorities) {
        super();
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public User registerUser(String username, String password, String roleAuthority) {
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority(roleAuthority).orElse(null);

        if(userRole == null) {
            //
            throw new IllegalArgumentException("Invalid role specified: " + roleAuthority);
        }
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        return userRepository.save(new User(0, username, encodedPassword, authorities));


    };

    public Integer getUserId() {
        return this.userId;
    }

    public void setId(Integer userId) {
        this.userId = userId;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return this.authorities;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}