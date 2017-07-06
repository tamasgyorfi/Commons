package hu.bets.common.util.servicediscovery;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.EurekaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class EurekaRegistrationHandler {

    private EurekaClient eurekaClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(EurekaRegistrationHandler.class);
    private static final int RETRY_WAIT_TIME = 5;

    void waitForRegistrationWithEureka(String vipAddress, EurekaClient eurekaClient, int retrySeconds) {
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
        ApplicationInfoManager appInfoManager;
        appInfoManager = eurekaFactory.getApplicationInfoManager(new MyDataCenterInstanceConfig());
        eurekaClient = eurekaFactory.getEurekaClient(appInfoManager, new DefaultEurekaClientConfig());

        appInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
        LOGGER.info("Registering with eureka server.");
        waitForRegistrationWithEureka(name, eurekaClient, RETRY_WAIT_TIME);
        LOGGER.info("Service is up and running.");
    }

    void blockingRegister(String name) {
        register(name);
    }

    Future<Boolean> nonBlockingRegister(String name) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> retVal = executor.submit(() -> {
            register(name);
            return true;
        });

        executor.shutdownNow();
        return retVal;
    }

    public void unregister() {
        eurekaClient.shutdown();
    }
}