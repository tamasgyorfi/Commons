package hu.bets.common.util.servicediscovery;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClientConfig;
import org.apache.log4j.Logger;

import java.util.Properties;

public class EurekaServiceResolver {

    private static final Logger LOGGER = Logger.getLogger(EurekaServiceResolver.class);

    private DynamicPropertyFactory configInstance;
    private DiscoveryClient eurekaClient;

    private ApplicationInfoManager initializeApplicationInfoManager(EurekaInstanceConfig instanceConfig) {
        InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
        return new ApplicationInfoManager(instanceConfig, instanceInfo);
    }

    private DiscoveryClient initializeEurekaClient(ApplicationInfoManager applicationInfoManager, EurekaClientConfig clientConfig) {
        return new DiscoveryClient(applicationInfoManager, clientConfig);
    }

    private String getEndpointByVipAddress(String vipAddress) {

        InstanceInfo nextServerInfo = null;
        try {
            LOGGER.info("Trying to obtain service endpoint using vipAddress: " + vipAddress);
            nextServerInfo = eurekaClient.getNextServerFromEureka(vipAddress, false);
            LOGGER.info("Information successfully retrieved: " + nextServerInfo.getHomePageUrl());
        } catch (Exception e) {
            throw new ServiceNotFoundException(e);
        }

        return nextServerInfo.getHomePageUrl();
    }

    public String getServiceEndpoint(String name) {

        Properties props = new Properties();
        props.put("eureka.registration.enabled", "false");
        props.put("eureka.shouldUseDns", "false");
        props.put("eureka.serviceUrl.default", "https://user:footballheureka@football-discovery-server.herokuapp.com/eureka/");
        System.getProperties().putAll(props);

        ApplicationInfoManager applicationInfoManager = initializeApplicationInfoManager(new MyDataCenterInstanceConfig());
        eurekaClient = initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());

        return getEndpointByVipAddress(name);
    }
}
