package com.github.msl.kafka.replicator.generic;

import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GenericReplicator {

	@Autowired
	GenericProducer producer;
	
	@Autowired
	GenericReplicatorConfig consumerConfig;
	
	@KafkaListener(topics = "${consumer.topic.name}", containerFactory = "kafkaListenerContainerFactory")
	public void avroConsumer(GenericRecord record, 
			@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) GenericRecord key,
	        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
	        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
	        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp){ 
		log.info("Received from brokers: " + consumerConfig.getConsumerBootstrapAddress() + " topic:" + topic + ",record with key:" + key + ",  message: " + record);
		producer.sendIncidenceWithResult(partition, timestamp, key, record);
	}
	
	

}
