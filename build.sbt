ThisBuild / scalaVersion := "2.13.5"

inThisBuild(
  List(
    organization := "eu.l-space",
    homepage := Some(url("https://github.com/L-space/Types")),
    licenses := List("MIT" -> url("https://opensource.org/licenses/MIT")),
    developers := List(
      Developer(
        "thijsbroersen",
        "Thijs Broersen",
        "thijsbroersen@gmail.com",
        url("https://github.com/ThijsBroersen")
      )
    )
  ))

(ThisBuild / dynverSonatypeSnapshots) := true
ThisBuild / version ~= (version =>
  """(\+\d\d\d\d\d\d\d\d-\d\d\d\d)-SNAPSHOT$""".r
    .findFirstIn(version)
    .fold(version)(version.stripSuffix(_) + "-SNAPSHOT"))

val settings = Seq(
  crossScalaVersions := Seq("2.13.5")
)

lazy val Types = project
  .in(file("."))
  .settings((publish / skip) := true)
  .aggregate(types.jvm, types.js)

lazy val types =
  (crossProject(JSPlatform, JVMPlatform)
    .withoutSuffixFor(JVMPlatform)
    .crossType(CrossType.Pure) in file("types"))
    .settings(settings)
    .settings(
      name := "types",
      libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.8" % "test",
      publishTo := sonatypePublishToBundle.value
    )
    .jsSettings(
      scalaJSLinkerConfig ~= { _.withOptimizer(false) },
      (Test / jsEnv) := new org.scalajs.jsenv.nodejs.NodeJSEnv()
    )
    .jvmSettings(
      )
