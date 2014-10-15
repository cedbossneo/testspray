import dao.{UserDao, UserDaoReactive}
import models.User
import org.specs2.mutable.Specification
import services.HelloService
import spray.httpx.SprayJsonSupport._
import spray.testkit.Specs2RouteTest

/**
 * Created by cehauber on 15/10/2014.
 */
class HelloServiceSpec extends Specification with Specs2RouteTest with HelloService {
  val usersDao: UserDao = new UserDaoReactive

  def actorRefFactory = system // connect the DSL to the test ActorSystem

  "The service" should {

    "add a user on PUT requests to the users path" in {
      Put("/users", User(name = "test")) ~> helloRoute ~> check {
        responseAs[String] must contain("ok")
      }
    }
    "return an array for GET requests to the users path" in {
      Get("/users") ~> helloRoute ~> check {
        responseAs[String] must contain("Cedric")
      }
    }
    "delete a user with a DELETE request to the user path" in {
      Delete("/user/test") ~> helloRoute ~> check {
        responseAs[String] must contain("ok")
      }
    }
  }
}