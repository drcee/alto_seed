package com.alto.currator

import org.apache.curator.x.discovery.{ServiceInstance, ServiceDiscovery, ServiceDiscoveryBuilder}


/**
 * Created by drcee on 22/06/2016.
 */
class ServiceRegister[T] {


/*
  def serviceDiscover(): ServiceDiscovery = {
    ServiceDiscoveryBuilder().build()
  }
*/

  def serviceInstance(serviceName : String,
                       id : String): ServiceDiscovery[T] = {

    ServiceInstance.builder()
      .name(serviceName)
      .id(id)
      .port(8080)
      .build()
  }

  /*
  def registerService(): Unit = {
    serviceDiscover().registerService()

  }
  */
}
