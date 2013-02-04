package leedm777.liftoff.snippet

import net.liftweb.http.{S, SHtml}
import leedm777.liftoff.comet.{Line, ChatServer}
import net.liftweb.http.js.JsCmds.SetValById
import leedm777.liftoff.model.User
import net.liftweb.common.Full

object ChatIn {
  def render = SHtml.onSubmit(s => {
    User.currentUser match {
      case Full(user) =>
        ChatServer ! Line(user, s)
        SetValById("chat_in", "")
      case _ =>
        S.error("Must login to post")
    }
  })
}
