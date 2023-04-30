package com.efficy.counterapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Y.Parasuram
 *
 */
/**
 * An Exception that can be thrown when duplicate counter is encountered
 */
@ResponseStatus(value = HttpStatus.IM_USED)
public class InValidCounterException extends RuntimeException {

    private static final long serialVersionUID = 6584480319956341884L;

	public InValidCounterException(String message) {
        super(message);
    }
}
