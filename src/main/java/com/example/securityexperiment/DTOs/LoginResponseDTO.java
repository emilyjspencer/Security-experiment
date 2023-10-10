package com.example.securityexperiment.DTOs;

import com.example.securityexperiment.entities.GenericUser;


public class LoginResponseDTO {
    private GenericUser user;
    private String jwt;

    public LoginResponseDTO(){
        super();
    }

    public LoginResponseDTO(GenericUser user, String jwt){
        this.user = user;
        this.jwt = jwt;
    }

    public GenericUser getUser(){
        return this.user;
    }

    public void setUser(GenericUser user){
        this.user = user;
    }

    public String getJwt(){
        return this.jwt;
    }

    public void setJwt(String jwt){
        this.jwt = jwt;
    }

}