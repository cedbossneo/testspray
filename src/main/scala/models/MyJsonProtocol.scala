package models

import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol

/**
 * Created by cedric on 13/10/14.
 */
object MyJsonProtocol extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val userFormat = jsonFormat1(User)
}
