package hu.bets.common.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DefaultMessageListener implements MessageListener {

    private final Channel channel;
    private final Consumer consumer;
    private String queueName;
    private final String exchangeName;
    private final String routingKey;

    public DefaultMessageListener(Channel channel, Consumer consumer, String queueName, String exchangeName, String routingKey) {
        this.channel = channel;
        this.consumer = consumer;
        this.queueName = queueName;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
    }

    @Override
    public void receive() throws IOException, TimeoutException {
        channel.queueBind(queueName, exchangeName, routingKey);
        channel.basicConsume(queueName, true, consumer);
    }

}
