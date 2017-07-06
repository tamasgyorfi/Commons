package hu.bets.common.util.servicediscovery;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.EurekaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class EurekaServiceResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(EurekaServiceResolver.class);

    private String getEndpointByVipAddress(String vipAddress, EurekaClient eurekaClient) {

        InstanceInfo nextServerInfo;
        try {
            LOGGER.info("Trying to obtain service endpoint using vipAddress: " + vipAddress);
            nextServerInfo = eurekaClient.getNextServerFromEureka(vipAddress, false);
            LOGGER.info("Information successfully retrieved: " + nextServerInfo.getHomePageUrl());
        } catch (Exception e) {
            throw new ServiceNotFoundException(e);
        }

        return nextServerInfo.getHomePageUrl();
    }

    String getServiceEndpoint(String name) {
        EurekaFactory eurekaFactory = new EurekaFactory();

        EurekaClient eurekaClient = eurekaFactory.getEurekaClient(eurekaFactory.getApplicationInfoManager(new MyDataCenterInstanceConfig()), new DefaultEurekaClientConfig());
        return getEndpointByVipAddress(name, eurekaClient);
    }
}
