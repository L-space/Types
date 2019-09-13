// shadow sbt-scalajs' crossProject and CrossType until Scala.js 1.0.0 is released
import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

ThisBuild / organization := "eu.l-space"
ThisBuild / scalaVersion := "2.13.0"

dynverSonatypeSnapshots in ThisBuild := true
ThisBuild / version ~= (version => """(\+\d\d\d\d\d\d\d\d-\d\d\d\d)-SNAPSHOT$""".r
  .findFirstIn(version).fold(version)(version.stripSuffix(_) + "-SNAPSHOT"))

val settings = Seq(
  crossScalaVersions := Seq("2.11.12", "2.12.10", "2.13.0")
)

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

lazy val site = (project in file("site"))
//  .enablePlugins(MicrositesPlugin)
//  .dependsOn(services % "compile->compile;compile->test")
//  .settings(name := "lspace-site")
//  .settings(skip in publish := true)
//  .settings(projectSettings)
//  .settings(
//    resourceGenerators in Compile += makeSettingsYml.taskValue,
//    makeMicrosite := (makeMicrosite dependsOn makeSettingsYml).value,
//    scalacOptions in Tut := compilerOptions
//  )
//  .settings(
//    micrositeName := "L-space",
//    micrositeDescription := "L-space, a graph computing framework for Scala",
//    micrositeDataDirectory := (resourceManaged in Compile).value / "site" / "data",
//    //    unmanagedResources ++= Seq(
//    //
//    //    ),
//    //    micrositeDocumentationUrl := "/yoursite/docs",
//    //    micrositeDocumentationLabelDescription := "Documentation",
//    micrositeAuthor := "Thijs Broersen",
//    micrositeHomepage := "https://docs.l-space.eu",
//    micrositeOrganizationHomepage := "https://l-space.eu",
//    //    micrositeOrganizationHomepage := "",
//    excludeFilter in ghpagesCleanSite := //preserves github-settings for custom domain, each time CNAME is written custom domain is reset?
//      new FileFilter{
//        def accept(f: File) = (ghpagesRepository.value / "CNAME").getCanonicalPath == f.getCanonicalPath
//      } || "versions.html",
//    micrositeGithubOwner := "L-space",
//    micrositeGithubRepo := "L-space",
//    micrositeGitterChannelUrl := "L-space/L-space",
//    micrositeFooterText := Some(
//      "<b>Knowledge is Power</b> <- <i>BOOKS = KNOWLEDGE = POWER = (FORCE X DISTANCE ÷ TIME)</i>")
//  )
