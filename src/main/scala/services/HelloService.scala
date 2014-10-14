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
      path("users") {
        put {
          entity(as[User]) { user =>
            respondWithMediaType(MediaTypes.`application/json`) {
              onComplete[Option[User]](usersDao.saveUser(user)) {
                case Success(value) => complete(value.get.toJson.toString())
                case Failure(ex) => complete(Map("error" -> ex.getMessage, "result" -> "ko").toJson.toString())
              }
          }
        }
        } ~
          get {
            respondWithMediaType(MediaTypes.`application/json`) {
              onComplete[List[User]](usersDao.getUsers) {
                case Success(value) => complete(value.toJson.toString())
                case Failure(ex) => complete(Map("error" -> ex.getMessage, "result" -> "ko").toJson.toString())
              }
            }
          }
    }~
        path("user" / Rest) { name =>
          delete {
            respondWithMediaType(MediaTypes.`application/json`) {
              onComplete(usersDao.deleteUser(name)) {
                case Success(value) => complete(Map("result" -> "ok").toJson.toString())
                case Failure(ex) => complete(Map("error" -> ex.getMessage, "result" -> "ko").toJson.toString())
              }
          }
        }
    }
}