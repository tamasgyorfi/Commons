package hu.bets.common.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import hu.bets.common.util.EnvironmentVarResolver;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

@Configuration
public class CommonMessagingConfig {

    private static final String MESSAGING_URI = "CLOUDAMQP_URL";
    private static final Logger LOGGER = Logger.getLogger(CommonMessagingConfig.class);

    @Bean
    public Channel channel(Connection connection) {
        try {
            return connection.createChannel();
        } catch (IOException e) {
            LOGGER.error(e);
        }

        return null;
    }

    @Bean(destroyMethod = "close")
    public Connection connection(ConnectionFactory connectionFactory) {
        try {
            return connectionFactory.newConnection();
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    @Bean
    public ConnectionFactory connectionFactory() throws KeyManagementException, NoSuchAlgorithmException {
        ConnectionFactory factory = new ConnectionFactory();

        try {
            factory.setUri(EnvironmentVarResolver.getEnvVar(MESSAGING_URI));
            factory.useSslProtocol();
        } catch (URISyntaxException | NoSuchAlgorithmException | KeyManagementException e) {
            LOGGER.error("Unable to set up messaging.", e);
        }

        return factory;
    }
}
