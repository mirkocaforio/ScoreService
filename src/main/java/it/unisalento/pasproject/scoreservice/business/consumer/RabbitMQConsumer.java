package it.unisalento.pasproject.scoreservice.business.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Provides functionality for consuming messages from RabbitMQ queues.
 * This service is designed to interact with RabbitMQ to receive messages
 * from specified queues. It supports consuming messages of generic types
 * as well as specifically string messages from named queues.
 */
@Service("RabbitMQConsumer")
public class RabbitMQConsumer implements MessageConsumerStrategy{

    /**
     * Logger instance for logging runtime events and errors.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    /**
     * RabbitTemplate instance for interacting with RabbitMQ.
     * This template provides operations to send and receive messages.
     */
    private final RabbitTemplate rabbitTemplate;

    /**
     * Constructs a RabbitMQConsumer with a specified RabbitTemplate.
     * @param rabbitTemplate the RabbitTemplate used for RabbitMQ operations.
     */
    @Autowired
    public RabbitMQConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Consumes a message of a generic type from RabbitMQ.
     * This method is a placeholder and needs to be implemented.
     * @param <T> the type of the message to consume.
     * @param message the message to consume.
     * @return the consumed message.
     */
    @Override
    public <T> T consumeMessage(T message) {
        //TODO: Implement this method
        return null;
    }

    /**
     * Consumes a string message from a specified RabbitMQ queue.
     * @param message the message to consume.
     * @param queueName the name of the queue to consume the message from.
     * @return the consumed message as a string.
     */
    @Override
    public String consumeMessage(String message, String queueName) {
        String ret = Objects.requireNonNull(rabbitTemplate.receive(queueName)).toString();
        return ret;
    }
}