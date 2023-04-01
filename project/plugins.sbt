// ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always

addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject"      % "1.2.0")
addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % "1.2.0")
addSbtPlugin("org.scala-js"       % "sbt-scalajs"                   % "1.13.0")
// addSbtPlugin("org.scala-native"   % "sbt-scala-native"              % "0.4.0")

addSbtPlugin("org.scalameta" % "sbt-mdoc" % "2.3.7")

addSbtPlugin("com.github.sbt" % "sbt-native-packager" % "1.9.16")

// addSbtPlugin("com.lightbend.sbt" % "sbt-proguard" % "0.4.0")

// addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "1.0.0")

//sbt-scalafmt, sbt-tpolecat
addSbtPlugin("com.softwaremill.sbt-softwaremill" % "sbt-softwaremill-common" % "2.0.12")
//sbt-ci-release
// addSbtPlugin("com.softwaremill.sbt-softwaremill" % "sbt-softwaremill-publish" % "2.0.12")
//sbt-updates, sbt-dependency-check
addSbtPlugin("com.softwaremill.sbt-softwaremill" % "sbt-softwaremill-extra"  % "2.0.12")

addSbtPlugin("com.codecommit" % "sbt-github-actions" % "0.14.2")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.0.7")
