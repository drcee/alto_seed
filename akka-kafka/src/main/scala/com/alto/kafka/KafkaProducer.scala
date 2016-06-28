package com.alto.kafka

import java.util.Properties

import org.apache.kafka.clients.producer.{ProducerRecord, KafkaProducer, Producer, ProducerConfig}

/**
 * Created by drcee on 27/06/2016.
 */
object KafkaProducerTest extends App{

  val events = 1000
  val topic = "test"
  val props = new Properties()

  props.put("group.id", "test");
  props.put("bootstrap.servers", "localhost:9092");
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

  val producer = new KafkaProducer[String, String](props)
  val t = System.currentTimeMillis()
  for (nEvents <- Range(0, events)) {
      producer.send(new ProducerRecord(topic, nEvents.toString(), "value " + nEvents.toString()))
  }
}
