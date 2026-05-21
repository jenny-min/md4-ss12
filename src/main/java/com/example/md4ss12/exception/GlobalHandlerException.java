package com.example.md4ss12.exception;

import com.example.md4ss12.models.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandlerException {
    // login sai
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<String>>
    handleBadCredentials(BadCredentialsException ex){

        ApiResponse<String> response =
                new ApiResponse<>(
                        false,
                        "Email hoặc mật khẩu không đúng",
                        null
                );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    // validation DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>>
    handleValidation(
            MethodArgumentNotValidException ex
    ){

        Map<String, String> errors =
                new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {

                    errors.put(
                            error.getField(),
                            error.getDefaultMessage()
                    );
                });

        ApiResponse<Map<String, String>> response =
                new ApiResponse<>(
                        false,
                        "Dữ liệu không hợp lệ",
                        errors
                );

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    // RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntime(RuntimeException ex){

        ApiResponse<?> response = ApiResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    // Exception tổng
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>>
    handleException(Exception ex){

        ApiResponse<String> response =
                new ApiResponse<>(
                        false,
                        "Lỗi hệ thống",
                        null
                );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
