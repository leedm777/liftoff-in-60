package leedm777.liftoff.comet

import net.liftweb.http.CometActor
import java.util.Date
import net.liftweb.http.js.JsCmds
import scala.xml
import net.liftweb.actor.LAPinger
import net.liftweb.common.Loggable
import java.util.concurrent.ScheduledFuture

object UpdateClock

class Clock extends CometActor with Loggable {
  private[this] var future: Option[ScheduledFuture[Unit]] = None

  def poll() {
    future = Some(LAPinger.schedule(this, UpdateClock, 1000))
  }

  override protected def localSetup() {
    this ! UpdateClock
  }

  override protected def localShutdown() {
    for (f <- future) {f.cancel(false) }
  }

  // Sending the UpdateClock message to this actor updates the #now element
  override def lowPriority = {
    case UpdateClock =>
      partialUpdate {
        JsCmds.SetHtml("now", xml.Text(new Date().toString))
      }
      poll()
  }

  def render = {
    "#now *" #> ""
  }
}
