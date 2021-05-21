ThisBuild / scalaVersion := "2.13.6"
ThisBuild / crossScalaVersions := Seq("2.13.6", "3.0.0")

inThisBuild(
  List(
    organization := "eu.l-space",
    homepage := Some(url("https://gitlab.com/L-space/Types")),
    licenses := List("MIT" -> url("https://opensource.org/licenses/MIT")),
    developers := List(
      Developer(
        "thijsbroersen",
        "Thijs Broersen",
        "thijsbroersen@gmail.com",
        url("https://gitlab.com/ThijsBroersen")
      ),
      Developer(
        "thijsbroersen",
        "Thijs Broersen",
        "thijsbroersen@gmail.com",
        url("https://github.com/ThijsBroersen")
      )
    )
  )
)

//addCompilerPlugin("org.scalameta" % "semanticdb-scalac" % "4.4.18" cross CrossVersion.full)

ThisBuild / dynverSeparator := "-"
(ThisBuild / dynverSonatypeSnapshots) := true
ThisBuild / version ~= (version =>
  """(\+\d\d\d\d\d\d\d\d-\d\d\d\d)-SNAPSHOT$""".r
    .findFirstIn(version)
    .fold(version)(version.stripSuffix(_) + "-SNAPSHOT")
)

lazy val commonSettings = commonSmlBuildSettings ++ Seq(
  Test / packageBin / publishArtifact := true,
  //  publishArtifact in (IntegrationTest, packageBin) := true,
  updateOptions := updateOptions.value.withCachedResolution(true),
  Compile / run / fork := true
  //  Test / fork := true,
  //  Test / testForkedParallel := true
)

val skipInPublish = Seq(
  (publish / skip) := true,
  publish := {}
)

lazy val Types = project
  .in(file("."))
  .settings(skipInPublish)
  .aggregate(types.jvm, types.js)

lazy val types =
  (crossProject(JSPlatform, JVMPlatform)
    .withoutSuffixFor(JVMPlatform)
    .crossType(CrossType.Pure) in file("types"))
    .settings(commonSettings)
    .settings(
      name := "types",
      libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.9" % "test"
    )
    .jsSettings(
      scalaJSLinkerConfig ~= { _.withOptimizer(false) },
      (Test / jsEnv) := new org.scalajs.jsenv.nodejs.NodeJSEnv()
    )
    .jvmSettings(
    )
