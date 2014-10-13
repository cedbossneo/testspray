package boot

import com.typesafe.config.ConfigFactory
import reactivemongo.api.MongoDriver

trait ReactiveMongoConnection extends TestActorSystem {

  private val config = ConfigFactory.load()
  implicit val context = system.dispatcher
  val driver = new MongoDriver

  val connection = driver.connection(List("localhost"))

  val db = connection("testspray")

  val usersCollection = db("testspray.users")
}