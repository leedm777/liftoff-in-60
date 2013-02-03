package leedm777.liftoff.snippet

import net.liftweb.http.{S, LiftScreen}
import leedm777.liftoff.model.User
import net.liftweb.util.FieldError
import net.liftweb.common._

class LoginScreen extends LiftScreen with Loggable {

  val name = field("Email", "")

  val passwd = password("Password", "")

  override def validations = validatePassword _ :: super.validations

  def validatePassword(): List[FieldError] = {
    User.login(name.is, passwd.is) match {
      case Full(_) => Nil // No error
      case Empty => "Invalid username or password"
      case f: Failure =>
        logger.error("Backend error on login: " + f.msg)
        "Login error. Please try again later."
    }
  }

  protected def finish() {
    // no-op
  }
}
