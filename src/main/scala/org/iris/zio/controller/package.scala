package org.iris.zio

import akka.actor.typed.{ActorRef, ActorSystem}
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import org.iris.zio.model.{Message, TypedGetMessage, TypedGetMessageResponse}
import sttp.tapir._
import sttp.tapir.server.akkahttp._
import akka.actor.typed.scaladsl.AskPattern._

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationDouble

package object controller {
  private val testPath = endpoint.get.in("test").out(stringBody)

  def handleRequest(implicit ec: ExecutionContext,service: ActorSystem[Message]): Route = {
    implicit val timeout: Timeout = 0.5.seconds
    //    testPath.toRoute(_ => (service ? ).mapTo[String].map(s => Right("Hello from a Scala controller! "+s)))
    testPath.toRoute(_ => service.ask[TypedGetMessageResponse](ref => TypedGetMessage(ref)).map {
      m => Right("Hello from a Scala controller! " + m.message)
    })
  }

}
