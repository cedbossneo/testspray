package models
import spray.json._
/**
 * Created by cehauber on 13/10/2014.
 */
case class User(name: String)

object UserProtocol extends DefaultJsonProtocol {
  implicit val userFormat = jsonFormat1(User)
}