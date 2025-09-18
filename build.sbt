name := "CPL"

version := "1.0"

scalaVersion := "3.5.0"

scalacOptions ++= Seq(
  "-encoding", "UTF-8", // Character encoding used by source files.
  "-deprecation",       // Emit warning and location for usages of deprecated APIs.
  "-feature",           // Emit warning and location for usages of features that should be imported explicitly.
  "-unchecked",         // Enable additional warnings where generated code depends on assumptions.
  "-Yexplicit-nulls",   // https://docs.scala-lang.org/scala3/reference/other-new-features/explicit-nulls.html
  "-new-syntax",        // Require `then` and `do` in control expressions.
  "-source:future",
  "-Xfatal-warnings",
)

Test / scalacOptions += "-language:adhocExtensions"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % Test

libraryDependencies += "org.scala-lang.modules" %% "scala-parallel-collections" % "1.2.0"

// GraalVM: JS, Python, R, ...
libraryDependencies += "org.graalvm.polyglot" % "polyglot" % "24.1.2"
libraryDependencies += "org.graalvm.polyglot" % "js" % "24.1.2"
