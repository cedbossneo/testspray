package boot

import com.typesafe.config.ConfigFactory
import reactivemongo.api.MongoDriver
import reactivemongo.api.collections.default.BSONCollection

trait ReactiveMongoConnection extends TestActorSystem {

  implicit val context = system.dispatcher
  val driver = new MongoDriver
  val connection = driver.connection(List("localhost"))
  val db = connection("testspray")
  val usersCollection: BSONCollection = db("testspray.users")
  private val config = ConfigFactory.load()
}