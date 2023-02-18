package com.example.simpleserver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping("/error")
    public ResponseEntity<Object> handleError(HttpServletRequest request) {
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(
                new ServletWebRequest(request), ErrorAttributeOptions.of(
                        ErrorAttributeOptions.Include.MESSAGE, ErrorAttributeOptions.Include.EXCEPTION, ErrorAttributeOptions.Include.STACK_TRACE, ErrorAttributeOptions.Include.BINDING_ERRORS
                )
        );

        int status = (Integer) errorAttributes.get("status");
        String error = (String) errorAttributes.get("error");
        String message = (String) errorAttributes.get("message");
        String trace = (String) errorAttributes.get("trace");

        // Construct your custom error response here
        // ...
        String customErrorResponse = status + " " + error + " " + message + " " + trace;
        System.out.println(customErrorResponse);
        return ResponseEntity.status(status).body(customErrorResponse);
    }

    public String string() {
        return "/error";
    }
}
