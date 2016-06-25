package com.alto.service

/**
 * Created by drcee on 25/06/2016.
 */
class RestServiceMetaInfo(val version :Long) {

}

object RestServiceMetaInfo {
  def apply(version :Long) = { new RestServiceMetaInfo(version)}
}
