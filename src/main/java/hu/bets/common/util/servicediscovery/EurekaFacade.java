package hu.bets.common.util.servicediscovery;

public interface EurekaFacade {

    void registerBlockingly(String serviceName);

    void registerNonBlockingly(String serviceName);

    String resolveEndpoint(String name);
}
