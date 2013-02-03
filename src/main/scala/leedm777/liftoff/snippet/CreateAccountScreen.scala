package leedm777.liftoff.snippet

import net.liftweb.http.{S, LiftScreen}
import net.liftweb.util.FieldError
import leedm777.liftoff.model.User
import net.liftweb.common._

class CreateAccountScreen extends LiftScreen with Loggable {
  val email = field("Email", "", checkForAccount _)

  val passwd1 = password("Password", "")

  val passwd2 = password("Verify", "", passwordsMatch _)

  def checkForAccount(email: String): List[FieldError] = {
    User.findByEmail(email) match {
      case Full(_) => "Account already exists"
      case Empty => Nil
      case f: Failure =>
        logger.error("Cannot access database: " + f.msg)
        "Error accessing database"
    }
  }

  def passwordsMatch(p2: String): List[FieldError] = {
    if (p2 == passwd1.is) Nil
    else "Passwords do not match"
  }

  override def validations = saveUser _ :: super.validations

  def saveUser(): List[FieldError] = {
    val created = User.create
      .email(email)
      .password(passwd1)
      .save()
    if (created) Nil
    else "Internal error creating user"
  }

  protected def finish() {
    User.login(email.is, passwd1.is)
    S.notice("Account created")
  }
}
