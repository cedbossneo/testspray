package actors

import akka.actor.Actor
import dao.{UserDao, UserDaoReactive}
import services.HelloService

/**
 * Created by cehauber on 13/10/2014.
 */
class HelloServiceActor extends Actor with HelloService {
  val usersDao: UserDao = new UserDaoReactive

  def receive = runRoute(helloRoute)

  def actorRefFactory = context
}
