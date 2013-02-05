package leedm777.liftoff.snippet

import scala.xml.NodeSeq
import net.liftweb.util.Helpers._

object User {
  object currentUser {
    val name = "Fake Name"
  }
}

class Hello {
  // render methods are NodeSeq => NodeSeq
  def hard(in: NodeSeq): NodeSeq =
    <div>Hello there,
      <span id="name">
        {User.currentUser.name}
      </span>
    </div>

  // Same snippet, this time using a CssSel
  def easy = ".name *" #> User.currentUser.name

}
