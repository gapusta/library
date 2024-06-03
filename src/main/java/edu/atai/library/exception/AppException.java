package edu.atai.library.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class AppException extends RuntimeException {
    private final AppExceptionCode code;

    public AppException(AppExceptionCode code, String msg) {
        super(msg);
        this.code = code;
    }

    public static AppException notFound() {
        return new AppException(AppExceptionCode.NOT_FOUND, "Not found");
    }

    public static AppException invalidDueDate() {
        return new AppException(AppExceptionCode.INVALID_DUE_DATE_1, "Due date cannot be before book taken date");
    }

    public static AppException invalidQuantity() {
        return new AppException(AppExceptionCode.INVALID_QUANTITY_1, "Quantity cannot be smaller than reserved");
    }
}
