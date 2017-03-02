name := """project1.03"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava,PlayEbean)

scalaVersion := "2.11.8" //Поменян с 2.11.7

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs

)
