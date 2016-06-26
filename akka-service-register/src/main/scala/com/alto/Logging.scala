package com.alto

import org.slf4j._

trait Logging {
  lazy val log = LoggerFactory.getLogger(getClass)

  implicit def logging2Logger(anything: Logging): Logger = anything.log
}