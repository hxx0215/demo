package org.iris.zio.model

import akka.actor.typed.ActorRef

sealed trait Message

case class TypedGetMessage(ref: ActorRef[TypedGetMessageResponse]) extends Message
case class TypedGetMessageResponse(message: String) extends Message
