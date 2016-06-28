package com.alto.kafka

import java.util
import java.util.Properties

import akka.actor.Actor
import org.apache.kafka.clients.consumer.{KafkaConsumer, ConsumerRecord}

/**
 * Created by drcee on 27/06/2016.
 */
object KafkaConsumerActor extends Actor{

  type Records = ConsumerRecord[String, String]

  val props = new Properties()

  props.put("group.id", "test");
  props.put("bootstrap.servers", "localhost:9092");
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

  val consumer = new KafkaConsumer[String,String](props);
  consumer.subscribe(util.Arrays.asList("test"));


  def receive = {
   case msg : String ⇒ {

   }
 }

  /*
  private def pollKafka(timeout: Int = 0)(cb: Option[Records] => Unit): Unit =
    tryWithConsumer(currentConsumerOffsets) {
      log.debug("Poll Kafka for {} milliseconds", timeout)
      val rs = consumer.poll(timeout)
      if (rs.count() > 0)
        cb(Some(ConsumerRecords(currentConsumerOffsets, rs)))
      else
        cb(None)
    }
  */

}
