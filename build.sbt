import play.Play.autoImport._
import PlayKeys._

name         := "MultipleModulesProject"

version      := Common.globalVersion

scalaVersion := "2.11.2"

crossScalaVersions  := Seq("2.11.2", "2.11.1", "2.10.4")


val commonlibraryDependencies = Seq(
    "org.scalaz" %% "scalaz-core" % "7.0.6" withSources
    )

val playlibraryDependencies = commonlibraryDependencies ++ Seq(
    filters,
    cache,
    ws,
    "com.typesafe.play.plugins" %% "play-plugins-mailer" % "2.3.0" withSources,
    "com.typesafe.play"       %% "play-json"                   % "2.3.0" withSources,
    "com.typesafe.akka" %% "akka-actor" % "2.3.4" withSources,
    "com.typesafe.akka" %% "akka-remote" % "2.3.4" withSources,
    "org.scalaz" %% "scalaz-core" % "7.0.6" withSources
    )

organization := Common.organizationName

    resolvers ++= Seq(
    Resolver.url("scala-js-releases",
        url("http://dl.bintray.com/content/scala-js/scala-js-releases"))(
            Resolver.ivyStylePatterns),
        "Typesafe releases" at "http://repo.typesafe.com/typesafe/releases/",
        "webjars" at "http://webjars.github.com/m2",
        Resolver.url("play-plugin-releases", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns),
        Resolver.url("play-plugin-releases", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns),
        Resolver.url("play-plugin-snapshots", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/"))(Resolver.ivyStylePatterns),
        Resolver.url("sbt-plugin-snapshots", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/"))(Resolver.ivyStylePatterns),
        Resolver.url("Sonatype Snapshots",url("http://oss.sonatype.org/content/repositories/snapshots/"))(Resolver.ivyStylePatterns),
        Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
        Resolver.url("Objectify Play Snapshot Repository", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns),
        Resolver.url("Graylog2 Play Repository", url("http://graylog2.github.io/play2-graylog2/releases/"))(Resolver.ivyStylePatterns),
        Resolver.url("Graylog2 Play Snapshot Repository", url("http://graylog2.github.io/play2-graylog2/snapshots/"))(Resolver.ivyStylePatterns),
        "Mandubian repository snapshots" at "https://github.com/mandubian/mandubian-mvn/raw/master/snapshots/",
        "Mandubian repository releases" at "https://github.com/mandubian/mandubian-mvn/raw/master/releases/",
        "Local ivy2 Repository" at "file:////workspces/ivy2/local"
    )

    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:reflectiveCalls",
        "-language:implicitConversions", "-language:postfixOps", "-language:dynamics","-language:higherKinds",
        "-language:existentials", "-language:experimental.macros", "-Xmax-classfile-name", "140")

    logBuffered in Test := false

    Keys.fork in Test := false

    parallelExecution in Test := false
    
    aggregate in update := false
    
    autoAPIMappings := true

  initialCommands := """ // make app resources accessible
|Thread.currentThread.setContextClassLoader(getClass.getClassLoader)
  |new play.core.StaticApplication(new java.io.File("."))
  |""".stripMargin

lazy val constants = project in file("constants")

lazy val utils = (project in file("utils")).settings(libraryDependencies ++= commonlibraryDependencies).dependsOn(constants)

lazy val root = (project in file(".")).settings(libraryDependencies ++= playlibraryDependencies).aggregate(constants, utils).enablePlugins(PlayScala)
