package br.com.davidson.ms_credit_evaluator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsCreditEvaluatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCreditEvaluatorApplication.class, args);
    }

}
