package it.unisalento.pasproject.scoreservice.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for RabbitMQ settings.
 * This class defines beans for queue, exchange, and bindings specific to score messages,
 * as well as general configuration for message conversion and template setup.
 */
@Configuration
public class RabbitMQConfig {
    // ------  SCORE  ------ //

    @Value("${rabbitmq.queue.score.name}")
    private String scoreQueue;

    @Value("${rabbitmq.exchange.score.name}")
    private String scoreExchange;

    @Value("${rabbitmq.routing.score.key}")
    private String scoreRoutingKey;

    /**
     * Defines a bean for the score queue.
     * The queue name is injected from application properties.
     *
     * @return a new Queue instance for score messages.
     */
    @Bean
    public Queue scoreQueue() {
        return new Queue(scoreQueue);
    }

    /**
     * Defines a bean for the score exchange.
     * The exchange name is injected from application properties.
     *
     * @return a new TopicExchange instance for score messages.
     */
    @Bean
    public TopicExchange scoreExchange() {
        return new TopicExchange(scoreExchange);
    }

    /**
     * Defines a bean for binding between the score queue and exchange using a routing key.
     * The routing key is injected from application properties.
     *
     * @return a new Binding instance linking the score queue to the score exchange.
     */
    @Bean
    public Binding scoreBinding() {
        return BindingBuilder
                .bind(scoreQueue())
                .to(scoreExchange())
                .with(scoreRoutingKey);
    }

    // ------  END SCORE  ------ //

    /**
     * Creates a bean for a message converter to convert messages to JSON format.
     * This converter is used by the RabbitTemplate for message serialization.
     *
     * @return a new Jackson2JsonMessageConverter instance.
     */
    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Creates a bean for an AMQP template configured with a connection factory and message converter.
     * This template is used for sending messages to RabbitMQ.
     *
     * @param connectionFactory the connection factory to use for RabbitMQ connections.
     * @return a new RabbitTemplate instance configured with the specified connection factory and message converter.
     */
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}