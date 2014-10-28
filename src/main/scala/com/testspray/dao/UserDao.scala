package com.testspray.dao

import akka.actor.ActorSystem
import com.testspray.models._
import reactivemongo.api.DB
import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.bson.BSONDocument
import reactivemongo.core.commands.LastError

import scala.concurrent.Future

/**
 * Created by cedric on 13/10/14.
 */

trait UserDao {
  def saveUser(user: User): Future[LastError]

  def deleteUser(name: String): Future[LastError]

  def getUsers: Future[List[User]]

  def findUserByName(name: String): Future[Option[User]]
}

class UserDaoReactive(db: DB, system: ActorSystem) extends UserDao {
  implicit val context = system.dispatcher
  val usersCollection: BSONCollection = db("testspray.users")

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
