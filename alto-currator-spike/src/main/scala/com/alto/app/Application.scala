package com.alto.app

import com.alto.service.{ServiceMetaInfo, ServiceRegister}
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder

/**
 * Created by drcee on 25/06/2016.
 */
object RegisterService extends App {

  val serviceInfo = ServiceMetaInfo("exampleService","exampleGGG",8082)

  ServiceRegister.registerInZookeeper(serviceInfo,8082)
}

object ClientConnector extends App {

  val client = ServiceRegister.curator()
  client.start()

  val discovery = ServiceDiscoveryBuilder
    .builder(classOf[String])
    .basePath("services")
    .client(client)
    .build()

  discovery.start()

  val provider = discovery.serviceProviderBuilder().serviceName("exampleService").build()

  provider.start()

  invokeInstance
  invokeInstance()
  invokeInstance()
  invokeInstance()
  invokeInstance()
  invokeInstance()
  invokeInstance()
  invokeInstance()


  def invokeInstance(): Unit = {
    var instance = provider.getInstance()

    var address = instance.buildUriSpec()

    val response = scala.io.Source.fromURL(address).mkString

    println("response " + response  + " from address " + address)
  }
}
