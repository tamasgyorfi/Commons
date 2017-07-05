package hu.bets.common.util.servicediscovery;

public class UnableToRegisterServiceException extends RuntimeException {

    public UnableToRegisterServiceException(String msg, Exception e) {
        super(msg, e);
    }
}
