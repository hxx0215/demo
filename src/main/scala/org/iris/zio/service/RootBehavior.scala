package org.iris.zio.service

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import org.iris.zio.model.{Message, TypedGetMessage}
import org.neo.zio.model.ServiceConfig

object RootBehavior {

  def apply(serviceConfig: ServiceConfig): Behavior[Message] = Behaviors.setup(new RootBehavior(_, serviceConfig))

}


class RootBehavior(context: ActorContext[Message], serviceConfig: ServiceConfig) extends AbstractBehavior[Message](context) {
  val myService = context.spawn(MyService(serviceConfig), "my-service")

  override def onMessage(msg: Message): Behavior[Message] = msg match {
    case m: TypedGetMessage =>
      myService ! m
      this
  }
}
