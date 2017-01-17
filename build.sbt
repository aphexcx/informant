name := "Informant"
version := "1.0"

enablePlugins(JavaServerAppPackaging)

lazy val http4sVersion = "0.14.4"

val rootSettings = Seq(
  scalaVersion := "2.12.1",
  libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "1.2.0"
)

val facebotSettings = Seq(
  scalaVersion := "2.11.8",
  organization := "co.datamonsters",
  version := "0.3.0"
)

lazy val core = project
  .settings(facebotSettings: _*)
  .settings(
    normalizedName := "facebot-core",
    libraryDependencies ++= Seq(
      "com.github.fomkin" %% "pushka-json" % "0.7.1",
      "org.slf4j" % "slf4j-api" % "1.7.21"
    ),
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
  )

lazy val http4s = project
  .settings(facebotSettings: _*)
  .settings(
    normalizedName := "facebot-http4s",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-blaze-server" % http4sVersion,
      "org.http4s" %% "http4s-blaze-client" % http4sVersion
    )
  )
  .dependsOn(core)

lazy val akkahttp = project
  .settings(facebotSettings: _*)
  .settings(
    normalizedName := "facebot-akka-http",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http-experimental" % "2.4.11"
    )

  )
  .dependsOn(core)

lazy val root = (project in file("."))
  .settings(rootSettings: _*)
  .settings(publish := {})
  .aggregate(core, http4s, akkahttp)
