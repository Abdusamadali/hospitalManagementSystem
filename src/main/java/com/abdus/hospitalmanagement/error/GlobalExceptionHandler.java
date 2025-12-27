package com.abdus.hospitalmanagement.error;


import io.jsonwebtoken.JwtException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice /// handles exception from service, Repository, Controller --> not FilterLayer
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError>handleUsernameNotFoundException(UsernameNotFoundException usernameNotFoundException){
        ApiError apiError = new ApiError("username not found with Username: "+usernameNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
         return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError>handleJwtException(JwtException jwtException){
        ApiError apiError =  new ApiError("Invalid Jwt Token: "+jwtException.getMessage(),HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError>handleAuthenticationException(AuthenticationException exception){
        ApiError apiError = new ApiError("Authentication failed: "+exception.getMessage(),HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError>handleAccessDeniedException(AccessDeniedException exception){
        ApiError apiError =  new ApiError("Access denied: Insufficient Permission: "+exception.getMessage(),HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }

    public ResponseEntity<ApiError>handleGenericException(Exception exception){
        ApiError apiError = new ApiError("An unexcepted error occurred: "+exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiError,apiError.getHttpStatus());
    }

}
