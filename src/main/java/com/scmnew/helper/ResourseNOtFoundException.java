package com.scmnew.helper;

public class ResourseNOtFoundException extends RuntimeException {

    public ResourseNOtFoundException(String message) {
        super(message);
    }

    public ResourseNOtFoundException(){
        super("Resourse not found ");
    }

}
