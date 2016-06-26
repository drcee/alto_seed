package com.alto.app

import javax.imageio.spi.ServiceRegistry

import com.alto.service.ServiceRegister
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder

/**
 * Created by drcee on 25/06/2016.
 */
object ServiceRegisterSpike extends App {

  new ServiceRegister().registerInZookeeper("exampleGGG",8082)

}

object ClientConnector extends App {

  val client = new ServiceRegister().curator()
  client.start()

  val discovery = ServiceDiscoveryBuilder
    .builder(classOf[String])
    .basePath("services")
    .client(client)
    .build()

    discovery.start()

  val provider = discovery.serviceProviderBuilder().serviceName("exampleGGG").build()

  provider.start()

  var instances = provider.getAllInstances

  var instance = provider.getInstance()

  var address = instance.buildUriSpec()

  val response = scala.io.Source.fromURL(address + "/exampleGGG").mkString

  println("response " + response)

}
