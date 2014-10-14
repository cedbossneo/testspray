package dao

import boot.ReactiveMongoConnection
import models._
import reactivemongo.bson.BSONDocument

import scala.concurrent.Future

/**
 * Created by cedric on 13/10/14.
 */

trait UserDao extends ReactiveMongoConnection {
  def getUsers: Future[List[User]]
}

class UserDaoReactive extends UserDao {
  def getUsers: Future[List[User]] = {
    usersCollection.find(BSONDocument()).cursor[User].collect[List]()
  }
}
