package bootstrap.liftweb

import net.liftweb.db.{DefaultConnectionIdentifier, DB, StandardDBVendor}
import net.liftweb.mapper.Schemifier
import net.liftweb.util._
import net.liftweb.common._
import net.liftweb.http._
import net.liftweb.sitemap._
import net.liftweb.sitemap.Loc._
import net.liftmodules.JQueryModule
import net.liftweb.http.js.jquery._
import leedm777.liftoff.model._


/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot extends Loggable {
  def boot {
    // where to search snippet
    LiftRules.addToPackages("leedm777.liftoff")

    // setup mapper
    (for (driverName <- Props.get("db.driver"); url <- Props.get("db.url")) yield {
      val user = Props.get("db.user")
      val password = Props.get("db.password")
      val vendor = new StandardDBVendor(driverName, url, user, password)
      DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)
      Schemifier.schemify(true, Schemifier.infoF _, User)
      logger.info("Database configured")
    }) or {
      logger.error("Missing database configuration")
      Empty
    }

    val LoggedIn = If(
      () => User.isLoggedIn,
      () => {
        S.error("Login required")
        RedirectResponse("/login")
      }
    )


    // Build SiteMap
    val entries = List(
      Menu.i("Home") / "index", // the simple way to declare a menu
      Menu.i("Login") / "login" >> Hidden,
      Menu.i("Create Account") / "new-account" >> Hidden,
      Menu.i("Chat") / "chat" >> LoggedIn,

      // more complex because this menu allows anything in the
      // /static path to be visible
      Menu(Loc("Static", Link(List("static"), true, "/static/index"),
        "Static Content")))

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMap(SiteMap(entries: _*))

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>      new Html5Properties(r.userAgent))

    // Link our user database in with Lift
    LiftRules.loggedInTest = Full(() => User.isLoggedIn)

    // Init the jQuery module, see http://liftweb.net/jquery for more information.
    LiftRules.jsArtifacts = JQueryArtifacts
    JQueryModule.InitParam.JQuery = JQueryModule.JQuery172
    JQueryModule.init()
  }
}
