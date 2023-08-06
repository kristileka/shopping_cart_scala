ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "shopping_cart"
  )
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe"   % "config"      % "1.4.2",
      "org.scalacheck" %% "scalacheck" % "1.17.0",
      "org.scalatest"  %% "scalatest"  % "3.2.15" % Test,
    )
  )
