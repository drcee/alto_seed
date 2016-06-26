package com.alto.service

import com.alto.Logging
import org.apache.curator.framework.{CuratorFramework, CuratorFrameworkFactory}
import org.apache.curator.retry.ExponentialBackoffRetry
import org.apache.curator.x.discovery.details.JsonInstanceSerializer
import org.apache.curator.x.discovery.{ServiceDiscoveryBuilder, ServiceInstance, UriSpec}


/**
 * Created by drcee on 22/06/2016.
 */
object ServiceRegister extends Logging{

  val ZK_HOST = "localhost:2181"

  def curator() :CuratorFramework = {
      CuratorFrameworkFactory.newClient(ZK_HOST, new ExponentialBackoffRetry(1000,3))
  }

  def serviceInstance(serviceInfo: ServiceMetaInfo,
                      port: Int
                       )
      : ServiceInstance[String] = {

    ServiceInstance.builder()
      .address("localhost")
      .name(serviceInfo.serviceName)
      .uriSpec(new UriSpec("{scheme}://{address}:{port}/" + serviceInfo.path))
      .port(port)
      .build()
  }

  def registerInZookeeper(serviceInfo: ServiceMetaInfo, port: Int): Unit = {

    log.info("attempting to start curator .. ")

    val client = curator()
    client.start()
    client.blockUntilConnected()

    val serial = new JsonInstanceSerializer(classOf[String])

    log.info("attempting create service {}",serviceInfo)

    val service = serviceInstance(
      serviceInfo,
        port)

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

  /*
  def serviceDiscover(service: ServiceInstance[ServiceMetaInfo]): ServiceDiscovery[ServiceMetaInfo] = {

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
  */
}
