package hu.bets.common.util.servicediscovery;

import hu.bets.common.util.EnvironmentVarResolver;

import java.util.Properties;
import java.util.concurrent.Future;

public class DefaultEurekaFacade implements EurekaFacade {

    private static EurekaRegistrationHandler registrationHandler;
    private static EurekaServiceResolver serviceResolver;

    public DefaultEurekaFacade() {
        Properties props = new Properties();
        props.put("eureka.region", "default");
        props.put("eureka.preferSameZone", "true");
        props.put("eureka.shouldUseDns", "false");
        props.put("eureka.serviceUrl.default", EnvironmentVarResolver.getEnvVar(EurekaFactory.EUREKA_URL));

        System.getProperties().putAll(props);
    }

    public DefaultEurekaFacade(Properties props) {
        System.getProperties().putAll(props);
    }

    public void registerBlockingly(String serviceName) {
        System.getProperties().put("eureka.vipAddress", serviceName);
        System.getProperties().put("eureka.name", serviceName);
        System.getProperties().put("eureka.instance.homePageUrl", "https://" + serviceName + ".herokuapp.com");

        getRegistrationHandler().blockingRegister(serviceName);
    }

    public Future<Boolean> registerNonBlockingly(String serviceName) {
        System.getProperties().put("eureka.vipAddress", serviceName);
        System.getProperties().put("eureka.name", serviceName);
        System.getProperties().put("eureka.instance.homePageUrl", "https://" + serviceName + ".herokuapp.com");

        return getRegistrationHandler().nonBlockingRegister(serviceName);
    }

    public String resolveEndpoint(String name) {
        return getServiceResolver().getServiceEndpoint(name);
    }

    @Override
    public void unregister() {
        getRegistrationHandler().unregister();
    }

    private synchronized EurekaServiceResolver getServiceResolver() {
        if (serviceResolver == null) {
            serviceResolver = new EurekaServiceResolver();
        }

        return serviceResolver;
    }

    private synchronized EurekaRegistrationHandler getRegistrationHandler() {
        if (registrationHandler == null) {
            registrationHandler = new EurekaRegistrationHandler();
        }

        return registrationHandler;
    }
}
