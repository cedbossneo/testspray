package models

import spray.json.{JsonFormat, DefaultJsonProtocol}

/**
 * Created by cedric on 13/10/14.
 */
object MyJsonProtocol extends DefaultJsonProtocol {
  implicit val userFormat = jsonFormat1(User)
}
