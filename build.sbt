lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """dv01-backend-challenge""",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.12.9",
    libraryDependencies ++= Seq(
      guice,
      "com.h2database" % "h2" % "1.4.199",
      "com.github.tototoshi" %% "scala-csv" % "1.3.5",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )
