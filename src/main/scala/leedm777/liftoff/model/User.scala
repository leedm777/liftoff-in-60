package leedm777.liftoff.model

import net.liftweb.mapper._
import net.liftweb.http.SessionVar
import net.liftweb.common._

class User extends LongKeyedMapper[User] with IdPK {
  def getSingleton = User

  object email extends MappedEmail(this, 80)

  object password extends MappedPassword(this)

}

object User extends User with MetaMapper[User] with LongKeyedMetaMapper[User] {
  def isLoggedIn: Boolean = currentUser.isDefined

  private object _currentUser extends SessionVar[Box[String]](Empty)

  def currentUser = _currentUser.is

  def login(loginEmail: String, loginPassword: String) = {
    _currentUser.set {
      for (user <- find(By(email, loginEmail)) if user.password.match_?(loginPassword)) yield user.email.is
    }
  }

  def logout() {
    _currentUser.set(Empty)
  }

  def findByEmail(e: String) = find(By(email, e))
}
