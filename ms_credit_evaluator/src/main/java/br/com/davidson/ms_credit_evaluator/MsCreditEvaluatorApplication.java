package br.com.davidson.ms_credit_evaluator;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableRabbit
public class MsCreditEvaluatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCreditEvaluatorApplication.class, args);
    }

}
