package com.flashcard.flash_app.service;

import com.flashcard.flash_app.dto.request.AuthenticationRequest;
import com.flashcard.flash_app.dto.request.IntrospectRequest;
import com.flashcard.flash_app.dto.response.AuthenticationResponse;
import com.flashcard.flash_app.dto.response.IntrospectResponse;
import com.flashcard.flash_app.entity.User;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    public IntrospectResponse introsecpt(IntrospectRequest request) throws JOSEException, ParseException;
    public AuthenticationResponse authenticate(AuthenticationRequest request) ;
    public String generateToken(User user);
}
