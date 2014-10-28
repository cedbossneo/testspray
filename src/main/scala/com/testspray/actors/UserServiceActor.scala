package com.testspray.actors

import akka.actor.{Actor, ActorSystem}
import com.testspray.dao.{UserDao, UserDaoReactive}
import com.testspray.services.UserService
import reactivemongo.api.DB

/**
 * Created by cehauber on 13/10/2014.
 */
class UserServiceActor(db: DB, system: ActorSystem) extends Actor with UserService {
  val usersDao: UserDao = new UserDaoReactive(db = db, system = system)

  def receive = runRoute(helloRoute)

  def actorRefFactory = context
}
