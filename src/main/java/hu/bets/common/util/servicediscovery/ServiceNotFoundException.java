package hu.bets.common.util.servicediscovery;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException(Exception e) {
        super(e);
    }
}
