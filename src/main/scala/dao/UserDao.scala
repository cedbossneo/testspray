package dao

import boot.ReactiveMongoConnection
import models._
import reactivemongo.bson.BSONDocument

import scala.concurrent.Future

/**
 * Created by cedric on 13/10/14.
 */

trait UserDao extends ReactiveMongoConnection {
  def saveUser(user: User): Future[Option[User]]

  def getUsers: Future[List[User]]

  def findUserByName(name: String): Future[Option[User]]
}

class UserDaoReactive extends UserDao {
  def getUsers: Future[List[User]] = {
    usersCollection.find(BSONDocument()).cursor[User].collect[List]()
  }

  def saveUser(user: User): Future[Option[User]] = {
    usersCollection.save(user).flatMap { _ =>
      findUserByName(user.name)
    }
  }

  def findUserByName(name: String): Future[Option[User]] = {
    usersCollection.find(BSONDocument("name" -> name)).one[User]
  }
}
