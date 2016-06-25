package com.alto.service

import org.apache.curator.framework.{CuratorFrameworkFactory, CuratorFramework}
import org.apache.curator.retry.ExponentialBackoffRetry
import org.apache.curator.x.discovery.{UriSpec, ServiceDiscoveryBuilder, ServiceInstance, ServiceDiscovery}
import org.slf4j.LoggerFactory


/**
 * Created by drcee on 22/06/2016.
 */
class ServiceRegister {

  val log = LoggerFactory.getLogger(classOf[ServiceRegister])

  val ZK_HOST = "localhost:2181"

  def curator() :CuratorFramework = {
      CuratorFrameworkFactory.newClient(ZK_HOST, new ExponentialBackoffRetry(1000,3))
  }

  def serviceDiscover(service: ServiceInstance[RestServiceMetaInfo]): ServiceDiscovery[RestServiceMetaInfo] = {

    log.info("attempting to start curator .. ")
    curator().start()

    log.info("creating service discovery .. ")
    ServiceDiscoveryBuilder
      .builder(classOf[RestServiceMetaInfo])
      .basePath("services")
      .client(curator())
      .thisInstance(service)
      .build()
  }


  def serviceInstance(serviceName: String,
                      id: String
                      //payload: String
                       )
      : ServiceInstance[RestServiceMetaInfo] = {

    ServiceInstance.builder()
      .address("localhost")
      .name(serviceName)
      .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
      .port(3888)
      .build()
  }

  def registerInZookeeper(): Unit = {

    log.info("attempting to start curator .. ")
    curator().start()

    val service = new ServiceRegister().serviceInstance("service/xxx","testService")

    log.info("creating service discovery .. ")
    ServiceDiscoveryBuilder
      .builder(classOf[RestServiceMetaInfo])
      .basePath("services")
      .client(curator())
      .thisInstance(service)
      .build()
      .start()

  }

}
