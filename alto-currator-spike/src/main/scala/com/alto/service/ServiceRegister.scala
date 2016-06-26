package com.alto.service

import org.apache.curator.framework.{CuratorFrameworkFactory, CuratorFramework}
import org.apache.curator.retry.ExponentialBackoffRetry
import org.apache.curator.x.discovery.details.JsonInstanceSerializer
import org.apache.curator.x.discovery.{UriSpec, ServiceDiscoveryBuilder, ServiceInstance, ServiceDiscovery}
import org.apache.zookeeper.CreateMode
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

  def serviceInstance(serviceName: String,
                      port: Int
                       )
      : ServiceInstance[String] = {

    ServiceInstance.builder()
      .address("localhost")
      .name(serviceName)
      .uriSpec(new UriSpec("{scheme}://{address}:{port}/" + serviceName))
      .port(port)
      .build()
  }

  def registerInZookeeper(serviceName: String,
                          port: Int): Unit = {

    log.info("attempting to start curator .. ")

    val client = curator()
    client.start()
    client.blockUntilConnected()

    val serial = new JsonInstanceSerializer(classOf[String])

    val service = serviceInstance(serviceName,port)

    log.info("creating service discovery .. ")
    ServiceDiscoveryBuilder
      .builder(classOf[String])
      .basePath("services")
      .client(client)
     // .serializer(serial)
      .thisInstance(service)
      .build()
      .start()
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
}
