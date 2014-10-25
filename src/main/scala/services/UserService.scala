package services

import dao.UserDao
import models.User
import spray.http.MediaTypes
import spray.httpx.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.routing.HttpService

import scala.util.{Failure, Success}

trait UserService extends HttpService {

  import scala.concurrent.ExecutionContext.Implicits.global

  val usersDao: UserDao

  val helloRoute =
    path("users") {
      put {
        entity(as[User]) { user =>
          respondWithMediaType(MediaTypes.`application/json`) {
            onComplete(usersDao.saveUser(user)) {
              case Success(value) => complete(Map("result" -> "ok"))
              case Failure(ex) => complete(Map("error" -> ex.getMessage, "result" -> "ko"))
            }
          }
        }
      } ~
        get {
          respondWithMediaType(MediaTypes.`application/json`) {
            onComplete[List[User]](usersDao.getUsers) {
              case Success(value) => complete(value)
              case Failure(ex) => complete(Map("error" -> ex.getMessage, "result" -> "ko"))
            }
          }
        }
    } ~
      path("user" / Rest) { name =>
        delete {
          respondWithMediaType(MediaTypes.`application/json`) {
            onComplete(usersDao.deleteUser(name)) {
              case Success(value) => complete(Map("result" -> "ok"))
              case Failure(ex) => complete(Map("error" -> ex.getMessage, "result" -> "ko"))
            }
          }
        }
      }
}