package dao

import akka.actor.ActorSystem
import models.User
import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.bson.BSONDocument

import scala.concurrent.Future

/**
 * Created by cedric on 13/10/14.
 */
trait UserDao {
  def getUsers: Future[List[User]]
}

class UserDaoImpl(usersCollection: BSONCollection, system: ActorSystem) extends UserDao{
  implicit val context = system.dispatcher

  def getUsers: Future[List[User]] = {
    usersCollection.find(BSONDocument()).cursor[User].collect[List[User]]()
  }
}
