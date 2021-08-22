ThisBuild / scalaVersion := "3.0.1"

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

ThisBuild / githubWorkflowTargetTags ++= Seq("v*")
ThisBuild / githubWorkflowPublishTargetBranches :=
  Seq(RefPredicate.StartsWith(Ref.Tag("v")))

ThisBuild / githubWorkflowPublish := Seq(
  WorkflowStep.Sbt(
    List("ci-release"),
    env = Map(
      "PGP_PASSPHRASE" -> "${{ secrets.PGP_PASSPHRASE }}",
      "PGP_SECRET" -> "${{ secrets.PGP_SECRET }}",
      "SONATYPE_PASSWORD" -> "${{ secrets.SONATYPE_PASSWORD }}",
      "SONATYPE_USERNAME" -> "${{ secrets.SONATYPE_USERNAME }}"
    )
  )
)

ThisBuild / crossScalaVersions := Seq("2.13.6", "3.0.1")
ThisBuild / githubWorkflowJavaVersions  := Seq("graalvm-ce-java16@21.1.0", "adopt@1.11.0-11")

lazy val Types = project
  .in(file("."))
  .settings(skipInPublish)
  .aggregate(types.jvm, types.js)

lazy val types =
  (crossProject(JSPlatform, JVMPlatform)
    .withoutSuffixFor(JVMPlatform)
    .crossType(CrossType.Pure) in file("types"))
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
