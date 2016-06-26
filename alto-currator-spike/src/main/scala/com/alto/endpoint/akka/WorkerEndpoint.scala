package com.alto.endpoint.akka

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.RouteResult.route2HandlerFlow
import com.alto.Logging
import com.alto.service.ServiceRegister
import org.slf4j.LoggerFactory

import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

import com.typesafe.config.ConfigFactory

import akka.stream.ActorMaterializer

import spray.json.DefaultJsonProtocol._

/**
 * Created by drcee on 26/06/2016.
 */
object WorkerEndpoint extends App with SprayJsonSupport with Logging {

  val config = ConfigFactory.load()
  val port = config.getInt("port")

  val registerMe = new ServiceRegister().registerInZookeeper("work",port)
  implicit val system = ActorSystem.create()

  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()

  lazy val route =
    get {
      path("work") {
        log.info("serviced by endpoint running on port {}", port)
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http running on port" + port + " </h1>"))
      } ~
      path(Segment) { string =>
        complete(PrettyPrinter(string.toJson))
      }
  }

  Http().bindAndHandle(route, interface = "localhost", port)

}
