package com.github.msl.kafka.replicator.generic;

import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GenericProducer {

	@Autowired
	private KafkaTemplate<Integer, GenericRecord> genericTemplate;

	@Autowired
	GenericReplicatorConfig config;

	public void sendIncidenceWithResult(String topic, int partition, long timestamp, Integer key, GenericRecord record) {
		log.info("Sending record to:" + config.getProducerBootstrapAddress() + " topic: " + topic + ", with key:" + key + ",  message: " + record);
		ListenableFuture<SendResult<Integer, GenericRecord>> future = genericTemplate.send(topic, key, record);
		future.addCallback(new ListenableFutureCallback<SendResult<Integer, GenericRecord>>() {
			@Override
			public void onSuccess(SendResult<Integer, GenericRecord> result) {
				log.info(
						"Sent message with key=[" + key + ", value=[" + record + "], offset=[" + result.getRecordMetadata().offset() + "]");
			}

			@Override
			public void onFailure(Throwable ex) {
				log.error("Unable to send message=[" + record + "] due to : " + ex.getMessage());
			}
		});
	}
	
	public void sendIncidenceMessage(String topic, int partition, long timestamp, Integer key, GenericRecord incidence) {
		genericTemplate.send(topic, partition, timestamp, key, incidence);
	}
}