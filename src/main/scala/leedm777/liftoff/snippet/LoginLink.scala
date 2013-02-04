package leedm777.liftoff.snippet

import scala.xml.NodeSeq
import leedm777.liftoff.model.User
import net.liftweb.http.{SHtml, S}
import net.liftweb.http.js.JsCmds

object LoginLink {
  def render(in: NodeSeq): NodeSeq =
    if (User.isLoggedIn)
      <div>
        {
          def logout = {
            User.logout()
            JsCmds.RedirectTo("/")
          }
          SHtml.a(logout _, <span>Logout</span>)
        }
      </div>
    else
      <div>
        <lift:Menu.item name="Login" donthide="true"></lift:Menu.item> / <lift:Menu.item name="Create Account" donthide="true">Create Account</lift:Menu.item>
      </div>

}
