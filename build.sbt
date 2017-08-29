name := """tw-api"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "org.jsoup" % "jsoup" % "1.8.3",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.9.0",
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.10" % "2.9.0",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.9.0",
  "org.specs2" % "specs2_2.9.1" % "1.8" % "test",
  "org.mockito" % "mockito-all" % "1.9.5" % "test"
)

