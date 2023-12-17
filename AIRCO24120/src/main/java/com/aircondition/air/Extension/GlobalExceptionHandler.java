package com.policearsenalsystem.Extensions;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        // Log the exception if needed
        e.printStackTrace();

        // Add error message to the model
        model.addAttribute("errorMessage", "An error occurred. Please try again later.");

        // Return the error view
        return "error";
    }

    // Handle specific custom exceptions if needed
    @ExceptionHandler(CustomException.class)
    public String handleCustomException(CustomException e, Model model) {
        // Log the exception if needed
        e.printStackTrace();

        // Add custom error message to the model
        model.addAttribute("errorMessage", e.getMessage());

        // Return the error view
        return "error"; // Create an "error.html" Thymeleaf template for displaying error messages
    }

}
