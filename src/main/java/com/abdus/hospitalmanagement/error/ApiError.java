package com.abdus.hospitalmanagement.error;

import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Data
public class ApiError {
    private LocalDateTime localDateTime;
    private String error;
    private HttpStatus httpStatus;

    public ApiError(){
        this.localDateTime = LocalDateTime.now();
    }

    public ApiError(String error,HttpStatus status){
        this(); //calling default constructor
        this.error = error;
        this.httpStatus = status;
    }
}
