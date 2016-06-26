package com.alto.endpoint.akka

import akka.actor.ActorSystem
import akka.http.scaladsl.Http

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.RouteResult.route2HandlerFlow

import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

import com.typesafe.config.ConfigFactory

import akka.stream.ActorMaterializer

import spray.json.DefaultJsonProtocol._


/**
 * Created by drcee on 25/06/2016.
 */
object RestEndpoint2 extends App with SprayJsonSupport {
  val config = ConfigFactory.load()

  implicit val system = ActorSystem.create()

  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()

  lazy val route =
    get {
        path("example11") {
        complete(List(1, 2, 3))
      } ~
        path(Segment) { string =>
          complete(PrettyPrinter(string.toJson))
        }
    }

  Http().bindAndHandle(route, interface = "localhost", port = 8082)

}
