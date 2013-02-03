name := "Liftoff in 60 minutes"

version := "0.0.1"

organization := "leedm777.liftoff"

scalaVersion := "2.10.0"

resolvers ++= Seq("snapshots"     at "http://oss.sonatype.org/content/repositories/snapshots",
                "releases"        at "http://oss.sonatype.org/content/repositories/releases"
                )

seq(com.github.siasia.WebPlugin.webSettings :_*)

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies ++= {
  val liftVersion = "2.5-M4"
  Seq(
    "net.liftweb"       %% "lift-webkit"        % liftVersion        % "compile",
    "net.liftweb"       %% "lift-mapper"        % liftVersion        % "compile",
    "net.liftmodules"   %% "lift-jquery-module" % (liftVersion + "-2.1"),
    "org.eclipse.jetty" % "jetty-webapp"        % "9.0.0.M5"         % "container; test",
    "com.h2database"    % "h2"                  % "1.3.170"          % "runtime",
    "ch.qos.logback"    % "logback-classic"     % "1.0.9",
    "org.specs2"        %% "specs2"             % "1.11"             % "test"
  )
}
