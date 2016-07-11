lazy val commonSettings = Seq(
  organization := "christopherdavenport.io",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.8"
)

lazy val scalaMail = project.in(file("."))
  .settings(commonSettings : _*)
  .settings(moduleName := "scala-mail")
  .dependsOn(core)

lazy val core = (project in file("core")).
  settings(commonSettings: _*).
  settings(moduleName := "scala-mail-core").
  settings(
    Seq(
      libraryDependencies ++= Seq(
        "com.sun.mail" % "javax.mail" % "1.5.5"
//        "javax.mail" % "javax.mail-api" % "1.5.5",
//        "javax.activation" % "activation" % "1.1.1"
      )
    )
  )
    