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
	private KafkaTemplate<GenericRecord, GenericRecord> genericTemplate;

	@Autowired
	GenericReplicatorConfig config;

	public void sendIncidenceWithResult(int partition, long timestamp, GenericRecord key, GenericRecord record) {
		log.info("Sending record to:" + config.getProducerBootstrapAddress() + " topic: " + config.getProducerTopicName() + ", with key:" + key + ",  message: " + record);
		ListenableFuture<SendResult<GenericRecord, GenericRecord>> future = genericTemplate.send(config.getProducerTopicName(), key, record);
		future.addCallback(new ListenableFutureCallback<SendResult<GenericRecord, GenericRecord>>() {
			@Override
			public void onSuccess(SendResult<GenericRecord, GenericRecord> result) {
				log.info(
						"Sent message with key=[" + key + ", value=[" + record + "], offset=[" + result.getRecordMetadata().offset() + "]");
			}

			@Override
			public void onFailure(Throwable ex) {
				log.error("Unable to send message=[" + record + "] due to : " + ex.getMessage());
			}
		});
	}
	
	public void sendIncidenceMessage(int partition, long timestamp, GenericRecord key, GenericRecord incidence) {
		genericTemplate.send(config.getProducerTopicName(), partition, timestamp, key, incidence);
	}
}