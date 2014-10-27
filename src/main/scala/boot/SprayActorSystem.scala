package boot

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

/**
 * Created by cedric on 13/10/14.
 */
trait SprayActorSystem {
  val config = ConfigFactory.load()
  implicit val system = ActorSystem("on-spray-can", config)

}
