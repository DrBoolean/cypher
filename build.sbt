name := """Cypher tests"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

resolvers ++= Seq(
  "anormcypher" at "http://repo.anormcypher.org/"
)

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "com.gilt"  %%  "jerkson"       % "0.6.6",
  "org.scalaj" %% "scalaj-http" % "1.0.1",
  "org.anormcypher" %% "anormcypher" % "0.6.0",
  "org.scalaz" %% "scalaz-core" % "7.1.0")
