package dao

import boot.ReactiveMongoConnection
import models._
import reactivemongo.bson.BSONDocument
import reactivemongo.core.commands.LastError

import scala.concurrent.Future

/**
 * Created by cedric on 13/10/14.
 */

trait UserDao extends ReactiveMongoConnection {
  def saveUser(user: User): Future[LastError]

  def deleteUser(name: String): Future[LastError]

  def getUsers: Future[List[User]]

  def findUserByName(name: String): Future[Option[User]]
}

class UserDaoReactive extends UserDao {
  def getUsers: Future[List[User]] = {
    usersCollection.find(BSONDocument()).cursor[User].collect[List]()
  }

  def saveUser(user: User): Future[LastError] = {
    usersCollection.save(user)
  }

  def findUserByName(name: String): Future[Option[User]] = {
    usersCollection.find(BSONDocument("name" -> name)).one[User]
  }

  def deleteUser(name: String): Future[LastError] = {
    usersCollection.remove(BSONDocument("name" -> name))
  }
}
