import lmcoursier.definitions.Authentication
import sbt.librarymanagement.ivy.Credentials.toDirect
import aether.AetherKeys._
import com.typesafe.sbt.packager.docker.Cmd

import scala.util.Try

ThisBuild / scalaVersion := "2.13.5"
ThisBuild / crossScalaVersions := Seq("2.13.5")

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
      )
    )
  ))

lazy val credential: Option[DirectCredentials] = Try(
  toDirect(Credentials(Path.userHome / ".sbt" / ".credentials.gitlab"))).toOption
val gitlabAuthenticationHeader = {
  if (sys.env.contains("CI")) {
    Some("Job-Token" -> sys.env("CI_JOB_TOKEN"))
  } else {
    credential.map(credential => "Private-Token" -> credential.passwd)
  }
}
val authByRepoId = gitlabAuthenticationHeader.toVector.map { gitlabAuthenticationHeader =>
  (
    "gitlab-com-carexs-stream",
    Authentication(
      user = "",
      password = "",
      optional = false,
      realmOpt = None,
      headers = Seq(gitlabAuthenticationHeader),
      true,
      false
    )
  )
}

ThisBuild / dynverSeparator := "-"
(ThisBuild / dynverSonatypeSnapshots) := true
ThisBuild / version ~= (version =>
  """(\+\d\d\d\d\d\d\d\d-\d\d\d\d)-SNAPSHOT$""".r
    .findFirstIn(version)
    .fold(version)(version.stripSuffix(_) + "-SNAPSHOT"))

Global / excludeLintKeys += aether.AetherKeys.aetherCustomHttpHeaders

lazy val commonSettings = commonSmlBuildSettings ++ Seq(
  publishArtifact in (Test, packageBin) := true,
  //  publishArtifact in (IntegrationTest, packageBin) := true,
  resolvers +=
    "gitlab-com-carexs-stream".at("https://gitlab.com/api/v4/packages/maven"),
  csrConfiguration ~=
    (_.withAuthenticationByRepositoryId(authByRepoId)),
  updateClassifiers / csrConfiguration ~= (_.withAuthenticationByRepositoryId(authByRepoId)),
  updateOptions := updateOptions.value.withCachedResolution(true),
  Compile / run / fork := true,
  //  Test / fork := true,
  //  Test / testForkedParallel := true,
  publishTo := Some("gitlab-stream-core".at("https://gitlab.com/api/v4/projects/25800855/packages/maven")),
  aether.AetherKeys.aetherCustomHttpHeaders := gitlabAuthenticationHeader.toList.toMap
)

lazy val Types = project
  .in(file("."))
  .settings((publish / skip) := true)
  .aggregate(types.jvm, types.js)

lazy val types =
  (crossProject(JSPlatform, JVMPlatform)
    .withoutSuffixFor(JVMPlatform)
    .crossType(CrossType.Pure) in file("types"))
    .settings(commonSettings)
    .settings(
      name := "types",
      libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.7" % "test"
    )
    .jsSettings(
      scalaJSLinkerConfig ~= { _.withOptimizer(false) },
      (Test / jsEnv) := new org.scalajs.jsenv.nodejs.NodeJSEnv()
    )
    .jvmSettings(
      )
