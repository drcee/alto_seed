package com.alto.endpoint.akka

import com.alto.service.{ServiceMetaInfo, ServiceRegister}
import com.typesafe.config.ConfigFactory

/**
 * Created by drcee on 26/06/2016.
 */
trait RegisterableEndPoint {

  val config = ConfigFactory.load()

  val port = config.getInt("port")

  val serviceInfo:ServiceMetaInfo

  val registerMe = ServiceRegister.registerInZookeeper(serviceInfo,port)
}
