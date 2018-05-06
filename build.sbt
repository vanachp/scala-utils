name := "scala-utils"

val SCALA_VERSION = "2.12.6"

lazy val scalautils = project
  .in(file("."))
  .settings(basicSettings)
  .aggregate(concurrent, data)
  .dependsOn(concurrent, data)

lazy val concurrent = project
  .in(file("concurrent"))
  .settings(basicSettings)

lazy val data = project
  .in(file("data"))
  .settings(basicSettings)

lazy val basicSettings = Seq(
  scalaVersion := SCALA_VERSION,
  updateOptions := updateOptions.value.withCachedResolution(true),
  scalacOptions in Compile ++= Seq(
    "-unchecked",
    "-deprecation",
    "-feature",
    "-Xlint",
    "-Ywarn-unused:implicits",
    "-Ywarn-unused:imports",
    "-Ywarn-unused:locals",
    "-Ywarn-unused:params",
    "-Xfatal-warnings",
    "-Ywarn-dead-code"
  )
)