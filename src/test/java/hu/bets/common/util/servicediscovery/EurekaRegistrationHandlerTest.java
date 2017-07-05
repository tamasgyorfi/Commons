package hu.bets.common.util.servicediscovery;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EurekaRegistrationHandlerTest {

    private static final String ADDRESS = "address";
    private EurekaRegistrationHandler sut = new EurekaRegistrationHandler();
    @Mock
    private EurekaClient eurekaClient;
    @Mock
    private InstanceInfo instanceInfo;

    @Test
    public void shouldRetryFiveTimesBeforeGivingUpServiceRegistration() {
        try {
            when(eurekaClient.getNextServerFromEureka(ADDRESS, false)).thenThrow(new IllegalArgumentException());
            sut.waitForRegistrationWithEureka(ADDRESS, eurekaClient, 0);
        } catch (UnableToRegisterServiceException e) {
        }
        verify(eurekaClient, times(5)).getNextServerFromEureka(ADDRESS, false);
    }

    @Test
    public void shouldStopRetryingWhenEurekaRegistersWService() {
        try {
            when(eurekaClient.getNextServerFromEureka(ADDRESS, false))
                    .thenThrow(new IllegalArgumentException())
                    .thenThrow(new IllegalArgumentException())
                    .thenReturn(instanceInfo);
            sut.waitForRegistrationWithEureka(ADDRESS, eurekaClient, 0);
        } catch (UnableToRegisterServiceException e) {
        }
        verify(eurekaClient, times(3)).getNextServerFromEureka(ADDRESS, false);
    }

}
