package services

import akka.actor.{Actor, ActorRefFactory}
import models.User
import spray.http.MediaTypes
import spray.json.pimpAny
import spray.routing.HttpService
import models.UserProtocol._

/**
 * Created by cehauber on 13/10/2014.
 */
class HelloServiceActor extends Actor with HelloService{
  def receive = runRoute(helloRoute)

  def actorRefFactory = context
}

trait HelloService extends HttpService{
    val helloRoute =
    path(""){
      get{
        respondWithMediaType(MediaTypes.`application/json`) {
          complete{
            User(name = "Cedric").toJson.toString()
          }
        }
      }
    }~
    path("list") {
      get{
        respondWithMediaType(MediaTypes.`application/json`) {
          complete{
            User(name = "Geowarin").toJson.toString()
          }
        }
      }
    }
}