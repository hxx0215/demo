package org.iris.zio

import akka.actor.typed.ActorSystem
import com.quasigroup.zio.akka.models.BindOn
import com.quasigroup.zio.akka.typed
import com.quasigroup.zio.akka.typed.http.ToRoute
import org.iris.zio.model.Message
import org.iris.zio.service.RootBehavior
import org.neo.zio.model.ServiceConfig
import zio.console.getStrLn
import zio.{ExitCode, IO, Task, URIO, ZIO}

object Main extends zio.App {
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = (for {
    akka <- ZIO.service[ActorSystem[Message]]
    routes <- IO.effect[ToRoute](controller.handleRequest(_, akka))
    _ <- typed.http.start(BindOn("127.0.0.1", 8089), routes).provide(akka).use(_ => getStrLn)
  } yield ()).provideCustomLayer(typed.live(RootBehavior(ServiceConfig()), "root")).exitCode
}
