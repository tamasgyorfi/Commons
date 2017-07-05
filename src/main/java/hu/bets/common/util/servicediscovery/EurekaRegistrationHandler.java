package hu.bets.common.util.servicediscovery;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.EurekaClient;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

class EurekaRegistrationHandler {

    private static final Logger LOGGER = Logger.getLogger(EurekaRegistrationHandler.class);
    private static final int RETRY_WAIT_TIME = 5;

    protected void waitForRegistrationWithEureka(String vipAddress, EurekaClient eurekaClient, int retrySeconds) {
        LOGGER.info("Registering service with name: " + vipAddress);
        InstanceInfo nextServerInfo = null;
        int retries = 5;

        while (nextServerInfo == null) {
            try {
                nextServerInfo = eurekaClient.getNextServerFromEureka(vipAddress, false);
            } catch (Exception e) {
                LOGGER.info("Waiting ... verifying service registration with eureka ...");
                LOGGER.error("", e);
                retries--;
                if (retries == 0) {
                    throw new UnableToRegisterServiceException("Could not register service: " + vipAddress, e);
                }
                try {
                    TimeUnit.SECONDS.sleep(retrySeconds);
                } catch (InterruptedException e1) {
                    // Do nothing here.
                }
            }
        }

    }

    private void register(String name) {
        EurekaFactory eurekaFactory = new EurekaFactory();
        ApplicationInfoManager appInfoManager = eurekaFactory.getApplicationInfoManager(new MyDataCenterInstanceConfig());
        EurekaClient eurekaClient = eurekaFactory.getEurekaClient(appInfoManager, new DefaultEurekaClientConfig());

        appInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
        LOGGER.info("Registering with eureka server.");
        waitForRegistrationWithEureka(name, eurekaClient, RETRY_WAIT_TIME);
        LOGGER.info("Service is up and running.");
    }

    void blockingRegister(String name) {
        register(name);
    }

    void nonBlockingRegister(String name) {
        new Thread(() -> register(name)).start();
    }
}