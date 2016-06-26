package com.alto.endpoint.akka

import com.alto.service.{ServiceMetaInfo, ServiceRegister}
import com.typesafe.config.ConfigFactory


trait RegisterableEndPoint {

  val config = ConfigFactory.load()

  val port = config.getInt("port")

  val serviceInfo:ServiceMetaInfo

  val registerMe = ServiceRegister.registerInZookeeper(serviceInfo,port)
}›
