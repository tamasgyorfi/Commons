package hu.bets.common.util.servicediscovery;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;
import org.apache.log4j.Logger;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class EurekaRegistrationHandler {

    private static final Logger LOGGER = Logger.getLogger(EurekaRegistrationHandler.class);
    private DynamicPropertyFactory configInstance;

    private ApplicationInfoManager initializeApplicationInfoManager(EurekaInstanceConfig instanceConfig) {
        InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
        return new ApplicationInfoManager(instanceConfig, instanceInfo);
    }

    private EurekaClient initializeEurekaClient(ApplicationInfoManager applicationInfoManager, EurekaClientConfig clientConfig) {
        return new DiscoveryClient(applicationInfoManager, clientConfig);
    }

    private void waitForRegistrationWithEureka(EurekaClient eurekaClient) {
        String vipAddress = configInstance.getStringProperty("eureka.vipAddress", "sampleservice.mydomain.net").get();
        LOGGER.info("Checking eureka server at: " + vipAddress);
        InstanceInfo nextServerInfo = null;
        while (nextServerInfo == null) {
            try {
                nextServerInfo = eurekaClient.getNextServerFromEureka(vipAddress, false);
            } catch (Exception e) {
                LOGGER.info("Waiting ... verifying service registration with eureka ...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e1) {
                    // Do nothing here.
                }
            }
        }
    }

    private void register(Properties props) {
        System.getProperties().putAll(props);

        configInstance = com.netflix.config.DynamicPropertyFactory.getInstance();
        ApplicationInfoManager applicationInfoManager = initializeApplicationInfoManager(new MyDataCenterInstanceConfig());
        EurekaClient eurekaClient = initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());

        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
        LOGGER.info("Registering with eureka server.");
        waitForRegistrationWithEureka(eurekaClient);
        LOGGER.info("Service is up and running.");
    }

    public void blockingRegister(Properties properties) {
        register(properties);
    }

    public void nonBlockingRegister(Properties properties) {
        new Thread(() -> register(properties)).start();
    }
}