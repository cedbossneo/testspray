import actors.HelloServiceActor
import akka.actor.Props
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import boot.{ReactiveMongoConnection, TestActorSystem}
import spray.can.Http

import scala.concurrent.duration._

/**
 * Created by cehauber on 13/10/2014.
 */
object Boot extends App with ReactiveMongoConnection with TestActorSystem{
  // create and start our service actor
  val helloService = system.actorOf(Props[HelloServiceActor], "hello-service")

  implicit val timeout = Timeout(5.seconds)
  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ? Http.Bind(helloService, interface = "localhost", port = 8080)
}
