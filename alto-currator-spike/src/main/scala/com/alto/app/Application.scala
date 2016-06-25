package com.alto.app

import javax.imageio.spi.ServiceRegistry

import com.alto.service.ServiceRegister

/**
 * Created by drcee on 25/06/2016.
 */
object ServiceRegisterSpike extends App {


  new ServiceRegister().registerInZookeeper()

  /*
   val service = new ServiceRegister().serviceInstance("service/xxx","testService")

    service.buildUriSpec()

    println("starting ...")
    val discover = new ServiceRegister().serviceDiscover(service)

    println("starting discovery...")

    discover.start()
    */

  /*
   println("starting discovery completed...")

    val provider = discover.serviceProviderBuilder()
      .serviceName("service/yyyy")
      .build()


    println("starting provider...")
    provider.start()


    println("attempting to register service...")
    discover.registerService(service)
  */

}
