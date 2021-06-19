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
	
	
//	@KafkaListener(topics = "${consumer.topic.name}", containerFactory = "kafkaListenerContainerFactory")
//	public void genericListener(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) GenericRecord key, @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long receivedTimestamp, GenericRecord record) {
//		log.info("Received record with key:" + key + ",  message: " + record);
//		producer.sendIncidenceMessage(key, record);
//	}
	
	
	@KafkaListener(topics = "${consumer.topic.name}", containerFactory = "kafkaListenerContainerFactory")
	public void avroConsumer(GenericRecord record, 
			@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) GenericRecord key,
	        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
	        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
	        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp){ 
		log.info("Receiving records from topic:" + topic);
		log.info("Received record with key:" + key + ",  message: " + record);
		producer.sendIncidenceMessage(partition, timestamp, key, record);
	}
	
	

}
