package br.com.davidson.ms_credit_evaluator.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RMConfig {

    @Value("${mq.queues.emissao-cartoes}")
    private String queueName;

    @Bean
    public Queue queueIssueCards(){
        return new Queue(queueName, true);
    }
}
