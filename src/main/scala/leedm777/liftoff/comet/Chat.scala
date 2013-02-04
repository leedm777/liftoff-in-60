package leedm777.liftoff.comet

import net.liftweb.http.{ListenerManager, CometListener, CometActor}
import net.liftweb.actor.LiftActor
import net.liftweb.common.Loggable

case class Line(user: String, message: String)

case class Messages(lines: Vector[Line])

object ChatServer extends LiftActor with ListenerManager with Loggable {
  private var lines = Vector.empty[Line]

  protected def createUpdate = Messages(lines)

  override protected def lowPriority = {
    case line: Line =>
      lines :+= line
      updateListeners()
  }
}

class Chat extends CometActor with CometListener {
  protected def registerWith = ChatServer

  private var lines = Vector.empty[Line]

  override def lowPriority = {
    case Messages(v) =>
      lines = v
      reRender()
  }

  def render = ".line" #> lines.map { line =>
    ".user *" #> line.user & ".message *" #> line.message
  }
}
