package com.testspray.models

import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol

/**
 * Created by cehauber on 13/10/2014.
 */
case class User(name: String)

object User extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val userFormat = jsonFormat1(User.apply)
}