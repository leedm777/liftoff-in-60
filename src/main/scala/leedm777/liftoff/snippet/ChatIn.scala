package leedm777.liftoff.snippet

import net.liftweb.http.SHtml
import leedm777.liftoff.comet.ChatServer
import net.liftweb.http.js.JsCmds.SetValById

object ChatIn {
  def render = SHtml.onSubmit(s => {
    ChatServer ! s
    SetValById("chat_in", "")
  })
}
