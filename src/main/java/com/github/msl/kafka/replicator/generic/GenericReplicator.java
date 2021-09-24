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

//	@KafkaListener(topics = {"${consumer.topic.names}"}, containerFactory = "kafkaListenerContainerFactory")
	@KafkaListener(topics = {"moaii.analytics.event.sum.in", "moaii.analytics.incidence.queue1", "moaii.analytics.installation.in", "moaii.analytics.predictiveModel.in", "moaii.esp.advmon.alarmlog.queue"}, containerFactory = "kafkaListenerContainerFactory")
	public void avroConsumer(GenericRecord record, 
			@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Integer key,
	        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
	        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
	        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp){ 
		topic = formatTopicName(topic);
		log.info("Received from brokers: " + consumerConfig.getConsumerBootstrapAddress() + " topic:" + topic + ",record with key:" + key + ",  message: " + record);
		producer.sendIncidenceWithResult(topic, partition, timestamp, key, record);
		log.info("Sent from brokers: " + consumerConfig.getConsumerBootstrapAddress() + " topic:" + topic + ",record with key:" + key + ",  message: " + record);

	}

	private String formatTopicName(String topicName) {
		String topic = topicName;
		int index = topic.indexOf(",");
		if (index > 0) {
			topic = topic.substring(0, index);
		}
		return topic;
	}

}
