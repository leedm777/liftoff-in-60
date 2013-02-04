package leedm777.liftoff.comet

import net.liftweb.http.{ListenerManager, CometListener, CometActor}
import net.liftweb.actor.LiftActor
import net.liftweb.util.Helpers._
import net.liftweb.util.ClearClearable

case class Messages(v: Vector[String])

object ChatServer extends LiftActor with ListenerManager {
  private var messages = Vector.empty[String]

  protected def createUpdate = Messages(messages)

  override protected def lowPriority = {
    case message: String =>
      messages :+= message
      updateListeners()
  }
}

class Chat extends CometActor with CometListener {
  protected def registerWith = ChatServer
  private var messages =  Vector.empty[String]

  override def lowPriority = {
    case Messages(v) =>
      messages = v
      reRender()
  }

  def render = (".message *" #> messages)
}
