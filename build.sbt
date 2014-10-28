import com.typesafe.sbt.packager.Keys._
import sbtdocker.Plugin.DockerKeys._
import sbtdocker.{Dockerfile, ImageName}

name := "testspray"

version := "1.0"

scalaVersion  := "2.11.2"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Sonatype Repository" at "https://oss.sonatype.org/content/repositories/snapshots/"

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= {
  val akkaV = "2.3.6"
  val sprayV = "1.3.2"
  val reactiveMongoV = "0.10.5.0.akka23"
  Seq(
    "io.spray" %% "spray-can" % sprayV,
    "io.spray" %% "spray-routing" % sprayV,
    "io.spray" %% "spray-json" % "1.3.0",
    "io.spray" %% "spray-testkit" % sprayV % "test",
    "org.specs2" %% "specs2" % "2.4.6" % "test",
    "com.github.simplyscala" %% "scalatest-embedmongo" % "0.2.2" % "test",
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-cluster" % akkaV,
    "org.reactivemongo" %% "reactivemongo" % reactiveMongoV
  )
}

Revolver.settings.settings

packageArchetype.java_server

sbtdocker.Plugin.dockerSettings

docker <<= docker.dependsOn(com.typesafe.sbt.packager.universal.Keys.stage.in(Compile))

// Define a Dockerfile
dockerfile in docker <<= (name, stagingDirectory in Universal) map {
  case (appName, stageDir) =>
    val workingDir = s"/opt/$appName"
    new Dockerfile {
      // Use a base image that contain Java
      from("relateiq/oracle-java8")
      maintainer("Cedric Hauber")
      expose(8080)
      add(stageDir, workingDir)
      run("chmod", "+x", s"/opt/$appName/bin/$appName")
      workDir(workingDir)
      entryPointShell(s"bin/$appName", appName, "$@")
    }
}

imageName in docker := {
  ImageName(
    namespace = Some("cedbossneo"),
    repository = name.value
    //,tag = Some("v" + version.value))
  )
}
