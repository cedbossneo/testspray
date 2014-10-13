name := "testspray"

version := "1.0"

scalaVersion  := "2.11.2"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers += "spray repo" at "http://repo.spray.io"

libraryDependencies ++= {
  val akkaV = "2.3.6"
  val sprayV = "1.3.2"
  val reactiveMongoV = "0.10.5.0.akka23"
  Seq(
    "io.spray"            %%  "spray-can"           % sprayV,
    "io.spray"            %%  "spray-routing"       % sprayV,
    "io.spray"            %%  "spray-json"          % "1.3.0",
    "com.typesafe.akka"   %%  "akka-actor"          % akkaV,
    "org.reactivemongo"   %%  "reactivemongo"       % reactiveMongoV
  )
}