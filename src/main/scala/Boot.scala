import java.net.InetAddress

import actors.UserServiceActor
import akka.actor.{AddressFromURIString, Props}
import akka.cluster.Cluster
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import boot.SprayActorSystem
import spray.can.Http

import scala.concurrent.duration._
import scala.io.Source
import scala.util.Try


/**
 * Created by cehauber on 13/10/2014.
 */
object Boot extends App with SprayActorSystem {
  val usersService = system.actorOf(Props[UserServiceActor], "users-service")

  val seeds = Try(config.getString("app.cluster.seedsFile")).toOption match {
    case Some(seedsFile) =>
      // Seed file was specified, read it
      Source.fromFile(seedsFile).getLines().map { address =>
        AddressFromURIString.parse(s"akka.tcp://${system.name}@$address")
      }.toList
    case None =>
      // No seed file specified, use this node as the first seed
      val port = config.getInt("app.port")
      val localAddress = Try(config.getString("app.host"))
        .toOption.getOrElse(InetAddress.getLocalHost.getHostAddress)
      List(AddressFromURIString.parse(s"akka.tcp://${system.name}@$localAddress:$port"))
  }

  Cluster.get(system).joinSeedNodes(seeds.toSeq)

  implicit val timeout = Timeout(5.seconds)
  IO(Http) ? Http.Bind(usersService, interface = config.getString("app.host"), port = 8080)
}
