package org.example.workshop.error;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Comparator;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Helper function to create a ProblemDetail with a list of field errors
    private ProblemDetail withFieldErrors(ProblemDetail problem,
                                          List<FieldErrorResponse> errors) {
        List<FieldErrorResponse> sorted = errors.stream()
                .sorted(Comparator.comparing(FieldErrorResponse::field))
                .toList();
        problem.setTitle("Validation failed");
        problem.setDetail("The request has " + sorted.size() + " invalid field" + ((sorted.size() == 1) ? "." : "s."));
        problem.setProperty("errors", sorted);
        return problem;
    }

    // Code 400
    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldErrorResponse> errors =
                ex.getBindingResult().getFieldErrors().stream()
                        .map(error -> new FieldErrorResponse(error.getField(),
                                error.getDefaultMessage()))
                        .toList();

        return handleExceptionInternal(ex, withFieldErrors(ex.getBody(), errors), headers, status, request);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldErrorResponse> errors = ex.getParameterValidationResults().stream()
                .flatMap(result -> result.getResolvableErrors().stream()
                        .map(error -> new FieldErrorResponse(
                                result.getMethodParameter().getParameterName(),
                                error.getDefaultMessage()
                        )))
                .toList();

        return handleExceptionInternal(ex, withFieldErrors(ex.getBody(), errors), headers, status, request);
    }
}
