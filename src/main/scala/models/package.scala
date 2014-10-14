import reactivemongo.bson.Macros

/**
 * Created by cehauber on 14/10/2014.
 */
package object models {
  implicit val userHandler = Macros.handler[User]
}
