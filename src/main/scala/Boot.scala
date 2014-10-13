import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.util.Timeout
import services.HelloServiceActor
import spray.can.Http
import scala.concurrent.duration._
import akka.pattern.ask
import reactivemongo.api._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by cehauber on 13/10/2014.
 */
object Boot extends App{
  val driver = new MongoDriver
  val connection = driver.connection(List("localhost"))
  val db = connection("testspray")

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")

  // create and start our service actor
  val service = system.actorOf(Props[HelloServiceActor], "hello-service")

  implicit val timeout = Timeout(5.seconds)
  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)
}
