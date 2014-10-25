package actors

import akka.actor.Actor
import dao.{UserDao, UserDaoReactive}
import services.UserService

/**
 * Created by cehauber on 13/10/2014.
 */
class UserServiceActor extends Actor with UserService {
  val usersDao: UserDao = new UserDaoReactive

  def receive = runRoute(helloRoute)

  def actorRefFactory = context
}
