package com.alto.kafka

import java.util.concurrent.atomic.AtomicBoolean

import akka.actor.Actor
import org.apache.kafka.clients.producer.KafkaProducer

/**
 * Created by drcee on 27/06/2016.
 */
abstract class AkkaKafkaProducer extends Actor{

  /*
  private val producerCreated = new AtomicBoolean(false)

  var producer: KafkaProducer = null

  def init(topic: String, zklist: String) = {
    if (producerCreated.getAndSet(true)) {
      throw new RuntimeException(this.getClass.getSimpleName + " this kafka akka actor has a producer already")
    }

    producer = new KafkaProducer(topic,zklist)
  }


  def receive = {
    case t: (String, String) ⇒ {
      init(t._1, t._2)
    }
    case msg: String ⇒
    {
      producer.send(msg)
    }
  }
*/
}
