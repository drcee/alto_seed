package com.alto.kafka

import java.util
import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerRecords, ConsumerRecord, KafkaConsumer}

/**
 * Created by drcee on 27/06/2016.
 */
object KafkaConsumerTest extends App{

  type Records = ConsumerRecord[String, String]

  val props = new Properties()

  props.put("group.id", "test");
  props.put("bootstrap.servers", "localhost:9092");
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");


  val consumer = new KafkaConsumer[String,String](props);
  consumer.subscribe(util.Arrays.asList("test"));

  while (true) {
        val records = consumer.poll(1000)

        println("records count " + records.count())

    /*
        if (records.count >0) {
            //new ConsumerRecords(records)
          println("received something ")
        }
        */
        val iterator = records.iterator()
          while (iterator.hasNext()) {
            processMessage(iterator.next())
          }


          //records.forEach((rec: ConsumerRecord[String,String]) => println(rec.toString()))
         //records.count shouldBe 1
          //val record = records.records("test").forEach("received something")
          //.forEach(record => println(record))

    //for (rec <- records) println(rec
  }

  def processMessage(message:ConsumerRecord[String,String]): Unit = {
    println("Received " + message.value())
  }
}
