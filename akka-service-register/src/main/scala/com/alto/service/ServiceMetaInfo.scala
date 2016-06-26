package com.alto.service

/**
 * Created by drcee on 25/06/2016.
 */
class ServiceMetaInfo(val serviceName: String,
                      val path: String,
                      val version: Long) {

}

object ServiceMetaInfo {
  def apply(serviceName: String,
            path: String,
            version :Long) = { new ServiceMetaInfo(serviceName,path,version)
  }

  def apply(serviceName: String,
            path: String):ServiceMetaInfo = { apply(serviceName,path,1L)

  }
}
