package hu.bets.common.util;

import org.everit.json.schema.ValidationException;

public class InvalidScemaException extends RuntimeException {

    public InvalidScemaException(ValidationException exception) {
        super(exception);
    }
}
