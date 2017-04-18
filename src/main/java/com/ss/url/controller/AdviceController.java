package com.ss.url.controller;

import com.ss.url.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;

/**
 * Created by Saurav on 18-04-2017.
 */
@RestControllerAdvice
public class AdviceController {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> errors = result.getFieldErrors();

        return new ResponseEntity<ErrorResponse>(new ErrorResponse("REQUEST_BODY_VALIDATION", processFieldError(errors), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    private String processFieldError(List<FieldError> errors) {
        StringBuffer message = new StringBuffer();
        if (!CollectionUtils.isEmpty(errors)) {
            Locale currentLocale = LocaleContextHolder.getLocale();
            for (FieldError error : errors) {
                String errorMsg = messageSource.getMessage(error.getDefaultMessage(), null, currentLocale);
                message.append(errorMsg).append("|");
            }
            message.setLength(message.length() - 1);
        }
        return message.toString();
    }

}
