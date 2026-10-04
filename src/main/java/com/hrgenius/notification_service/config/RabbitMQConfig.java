package com.hrgenius.notification_service.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${app.rabbitmq.exchange:hrgenius.exchange}")
    private String exchangeName;

    @Value("${app.rabbitmq.queue.payroll-notify:notification.payroll.queue}")
    private String payrollNotifyQueue;

    @Value("${app.rabbitmq.queue.leave-notify:notification.leave.queue}")
    private String leaveNotifyQueue;

    @Value("${app.rabbitmq.routing-key.payroll-processed:payroll.processed}")
    private String payrollRoutingKey;

    @Value("${app.rabbitmq.routing-key.leave-approved:leave.approved}")
    private String leaveRoutingKey;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    // Payroll Notification Queue
    @Bean
    public Queue payrollNotifyQueue() {
        return new Queue(payrollNotifyQueue, true);
    }

    @Bean
    public Binding payrollNotifyBinding(Queue payrollNotifyQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(payrollNotifyQueue)
                .to(exchange)
                .with(payrollRoutingKey);
    }

    // Leave Notification Queue
    @Bean
    public Queue leaveNotifyQueue() {
        return new Queue(leaveNotifyQueue, true);
    }

    @Bean
    public Binding leaveNotifyBinding(Queue leaveNotifyQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(leaveNotifyQueue)
                .to(exchange)
                .with(leaveRoutingKey);
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}