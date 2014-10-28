import com.testspray.dao.{UserDao, UserDaoReactive}
import com.testspray.models.User
import com.testspray.services.UserService
import org.specs2.mutable.Specification
import reactivemongo.api.{DB, MongoDriver}
import spray.httpx.SprayJsonSupport._
import spray.testkit.Specs2RouteTest

/**
 * Created by cehauber on 15/10/2014.
 */
class UserServiceSpec extends Specification with Specs2RouteTest with UserService {

  val usersDao: UserDao = new UserDaoReactive(createDB(), system)

  def createDB(): DB = {
    val mongoDriver = new MongoDriver
    val mongoConnection = mongoDriver.connection(List("localhost"))
    mongoConnection("testspray-test")
  }

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