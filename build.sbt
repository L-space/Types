// shadow sbt-scalajs' crossProject and CrossType until Scala.js 1.0.0 is released
import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

ThisBuild / organization := "eu.l-space"
ThisBuild / scalaVersion := "2.13.0"
ThisBuild / version ~= (version => """(\+\d\d\d\d\d\d\d\d-\d\d\d\d)-SNAPSHOT$""".r
  .findFirstIn(version).fold(version)(version.stripSuffix(_)))

val settings = Seq(crossScalaVersions := Seq("2.11.12", "2.12.10", "2.13.0"))

lazy val Types = project
  .in(file("."))
  .settings(skip in publish := true)
  .aggregate(types.jvm, types.js)

lazy val types =
  (crossProject(JSPlatform, JVMPlatform)
    .withoutSuffixFor(JVMPlatform)
    .crossType(CrossType.Pure) in file("types"))
    .settings(settings)
    .settings(
      name := "types",
      libraryDependencies += "org.scalatest" %%% "scalatest" % "3.1.0-RC2" % "test"
    )
    .jsSettings(
      scalaJSLinkerConfig ~= { _.withOptimizer(false) },
      jsEnv in Test := new org.scalajs.jsenv.nodejs.NodeJSEnv()
    )
    .jvmSettings(
    )
