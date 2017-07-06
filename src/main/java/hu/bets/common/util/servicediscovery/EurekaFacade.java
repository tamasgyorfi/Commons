package hu.bets.common.util.servicediscovery;

import java.util.concurrent.Future;

public interface EurekaFacade {

    void registerBlockingly(String serviceName);

    Future<Boolean> registerNonBlockingly(String serviceName);

    String resolveEndpoint(String name);

    void unregister();
}
