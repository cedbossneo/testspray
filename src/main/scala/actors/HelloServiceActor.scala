package actors

import akka.actor.Actor
import dao.UserDao
import services.HelloService

/**
 * Created by cehauber on 13/10/2014.
 */
trait HelloServiceActor extends Actor with HelloService{
  val usersDao: UserDao

  def receive = runRoute(helloRoute)

  def actorRefFactory = context
}
