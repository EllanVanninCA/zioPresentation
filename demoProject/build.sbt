import Dependencies._

ThisBuild / scalaVersion := "2.13.3"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "ca.ellanVannin"
ThisBuild / organizationName := "Ellan Vannin"

lazy val root = (project in file("."))
  .settings(
    name := "My ZIO Demo",
    libraryDependencies ++= List(
      //      scalaTest % Test,
      library.zio
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.

lazy val library =
  new {

    object Version {
      val zio = "1.0.1"
    }

    val zio = "dev.zio" %% "zio" % Version.zio
  }