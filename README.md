# Spring Kafka Replicator: kafka consumer and kafka producer

## About

This is a simple project that consumes messages from one Kafka cluster and  produces it to another Kafka cluster

## Technical Stack:

- Java 11
- Maven 3.6+
- SpringBoot 2.5.0
- Kafka clients 2.7.1
- AVRO 1.10.1
- Confluent Serdes 5.3.0


## Installation
This application is a Kafka Consumer and producer:
- Install a Confluent platform (any version is compatible), at least one broker, one Zookeeper ad One Schema Registry is needed 
- Create a topic in the origin and destination cluster 
- In that topic create the Schema for the key and the value both of them in AVRO format
- Start the project by using the Main Application or or installing the jar and start up it with java -jar jar-name
- Produce messages to the origin topic

## Considerations:
- All configuration properties are loaded in the application.properties from System properties, this is an example:

```
-DPRODUCER_BOOTSTRAP_ADDRESS=broker1:9092 -DCONSUMER_SECURITY_PROTOCOL=SASL_PLAINTEXT -DCONSUMER_JAAS_CONFIG="org.apache.kafka.common.security.plain.PlainLoginModule required username='client' password='client-secret';" -DCONSUMER_SASL_MECHANISM=PLAIN -DCONSUMER_TOPIC_NAME=topic1 -DCONSUMER_GROUP_ID=java.replicator.generic -DCONSUMER_SCHEMA_REGISTRY_URL=http://registry1:8081 -DCONSUMER_BOOTSTRAP_ADDRESS=broker2:9092 -DPRODUCER_SECURITY_PROTOCOL=SASL_SSL -DPRODUCER_JAAS_CONFIG="org.apache.kafka.common.security.scram.ScramLoginModule required username='client' password='$client-secret';" -DPRODUCER_SASL_MECHANISM=SCRAM-SHA-512 -DPRODUCER_TOPIC_NAME=topic2 -DPRODUCER_SCHEMA_REGISTRY_URL=http://registry2:8081
```


