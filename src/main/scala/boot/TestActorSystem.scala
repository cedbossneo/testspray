package boot

import akka.actor.ActorSystem

/**
 * Created by cedric on 13/10/14.
 */
trait TestActorSystem {
  implicit val system = ActorSystem("on-spray-can")

}
