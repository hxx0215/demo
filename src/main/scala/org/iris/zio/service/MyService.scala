package org.iris.zio.service

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import org.iris.zio.model.{TypedGetMessage, TypedGetMessageResponse}
import org.neo.zio.model.ServiceConfig

object MyService {
  def apply(serviceConfig: ServiceConfig): Behavior[TypedGetMessage] = Behaviors.setup(new MyService(_, serviceConfig))
}

class MyService(context: ActorContext[TypedGetMessage], serviceConfig: ServiceConfig) extends AbstractBehavior[TypedGetMessage](context) {
  override def onMessage(msg: TypedGetMessage): Behavior[TypedGetMessage] = {
    msg.ref ! TypedGetMessageResponse(s"The service says: '${serviceConfig.someKey}'")
    this
  }
}
