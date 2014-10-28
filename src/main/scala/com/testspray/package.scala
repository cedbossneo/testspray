package com

import com.testspray.models.User
import reactivemongo.bson.Macros

/**
 * Created by cehauber on 14/10/2014.
 */
package object testspray {
  implicit val userHandler = Macros.handler[User]
}
