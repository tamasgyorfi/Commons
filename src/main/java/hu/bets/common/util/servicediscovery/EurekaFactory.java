package hu.bets.common.util.servicediscovery;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;

class EurekaFactory {

    private static final EurekaFactory INSTANCE = new EurekaFactory();

    private static final EurekaInstanceConfig config = new MyDataCenterInstanceConfig();
    private static final EurekaClientConfig clientConfig = new DefaultEurekaClientConfig();
    private static final InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(config).get();
    private static final ApplicationInfoManager infoManager = new ApplicationInfoManager(config, instanceInfo);
    private static final EurekaClient eurekaClient = new DiscoveryClient(infoManager, clientConfig);

    private EurekaFactory() {

    }

    public static synchronized EurekaFactory getInstance() {
        return INSTANCE;
    }

    public ApplicationInfoManager getApplicationInfoManager() {
        return infoManager;

    }

    public EurekaClient getEurekaClient() {
        return eurekaClient;
    }
}
