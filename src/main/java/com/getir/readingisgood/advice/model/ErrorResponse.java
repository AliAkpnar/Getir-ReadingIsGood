package com.getir.readingisgood.advice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.getir.readingisgood.advice.exception.BaseRuntimeException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.getir.readingisgood.advice.constants.ErrorCodes.VALIDATION_ERROR;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {
    private Long timestamp;
    private int code;
    private String message;
    private Map<String, List<String>> errors;

    public static ErrorResponse of(BindingResult bindingResult) {
        return new ErrorResponse(VALIDATION_ERROR, bindingResult);
    }

    public static ErrorResponse of(BaseRuntimeException exception) {
        return new ErrorResponse(exception.getCode(), exception.getMessage());
    }

    private ErrorResponse(int code, String message) {
        this.timestamp = Instant.now().toEpochMilli();
        this.code = code;
        this.message = message;
    }

    private ErrorResponse(int code, BindingResult bindingResult) {
        this.timestamp = Instant.now().toEpochMilli();
        this.message = "Validation error";
        this.code = code;
        this.errors = bindingResult.getAllErrors()
                .stream()
                .collect(ErrorConsumer::new, ErrorConsumer::accept, ErrorConsumer::combine)
                .getErrors();
    }

    private static class ErrorConsumer implements Consumer<ObjectError> {
        private final Map<String, List<String>> errors = new HashMap<>();

        public Map<String, List<String>> getErrors() {
            return errors;
        }

        @Override
        public void accept(ObjectError error) {
            put(getKey(error), getValue(error));
        }

        public void combine(ErrorConsumer other) {
            other.errors.forEach(this::put);
        }

        private String getKey(ObjectError error) {
            if(error instanceof FieldError) {
                return  ((FieldError)error).getField();
            }
            return error.getCode();
        }

        private List<String> getValue(ObjectError error) {
            return Optional.ofNullable(error.getDefaultMessage())
                    .map(this::getValue)
                    .orElseGet(ArrayList::new);
        }

        private List<String> getValue(String message) {
            return Arrays.stream(message.split(","))
                    .collect(Collectors.toList());
        }

        private void put(String key, List<String> value) {
            if (errors.containsKey(key)) {
                errors.get(key).addAll(value);
            } else {
                errors.put(key, value);
            }
        }
    }
}