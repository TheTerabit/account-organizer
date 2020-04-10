package pl.bs.accountorganizer.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
