package com.rabobank.statement.controller;

import com.rabobank.statement.exception.CustomerStatementException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    public String MultiPartErrorhandler(MultipartException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", e.getCause().getMessage());
        return "redirect:/uploadStatus";

    }

    @ExceptionHandler(CustomerStatementException.class)
    public String CustStmtExceptionhandler(CustomerStatementException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", e.getMessage() + e.getCause().toString());
        return "redirect:/uploadStatus";

    }
}
