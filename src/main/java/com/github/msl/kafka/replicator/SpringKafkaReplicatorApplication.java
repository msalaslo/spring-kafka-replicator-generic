package com.github.msl.kafka.replicator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class SpringKafkaReplicatorApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaReplicatorApplication.class, args);		
	}

}
