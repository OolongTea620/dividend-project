package zerobase.devidend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import zerobase.devidend.exception.AbstractException;
import zerobase.devidend.exception.ErrorResponse;

@Slf4j
@ControllerAdvice // controller 보다 좀 더 바깥쪽에서 돟작 하도록 한다.
public class CustomExceptionHandler {
    @ExceptionHandler(AbstractException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(AbstractException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                                                    .message(e.getMessage())
                                                    .code(e.getStatusCode())
                                                    .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(e.getStatusCode()));
    }
}
