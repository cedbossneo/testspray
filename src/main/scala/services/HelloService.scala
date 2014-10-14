package services

import dao.UserDao
import models.MyJsonProtocol._
import models.User
import spray.http.MediaTypes
import spray.json.pimpAny
import spray.routing.HttpService

import scala.util.{Failure, Success}

trait HelloService extends HttpService{
    import scala.concurrent.ExecutionContext.Implicits.global

    val usersDao: UserDao

    val helloRoute =
    path(""){
      get{
        respondWithMediaType(MediaTypes.`application/json`) {
          complete{
            User(name = "Cedric23").toJson.toString()
          }
        }
      }
    }~
    path("list") {
      get{
        respondWithMediaType(MediaTypes.`application/json`) {
          onComplete[List[User]](usersDao.getUsers) {
            case Success(value) => complete(value.toJson.toString())
            case Failure(ex) => complete(Map("error" -> ex.getMessage).toJson.toString())
          }
        }
      }
    }
}