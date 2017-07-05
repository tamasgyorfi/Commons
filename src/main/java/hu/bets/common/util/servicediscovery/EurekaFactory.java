package hu.bets.common.util.servicediscovery;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;

class EurekaFactory {

    public static final String EUREKA_URL = "EUREKA_URL";

    private ApplicationInfoManager applicationInfoManager;
    private EurekaClient eurekaClient;

    public synchronized ApplicationInfoManager getApplicationInfoManager(EurekaInstanceConfig instanceConfig) {
        InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
        return new ApplicationInfoManager(instanceConfig, instanceInfo);

    }

    public EurekaClient getEurekaClient(ApplicationInfoManager applicationInfoManager, EurekaClientConfig clientConfig) {
        return new DiscoveryClient(applicationInfoManager, clientConfig);
    }
}
