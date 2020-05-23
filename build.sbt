name := "AttendanceManager"

version := "0.1"

scalaVersion := "2.13.2"

libraryDependencies ++= Seq(
  "com.corundumstudio.socketio" % "netty-socketio" % "1.7.18",
  "com.typesafe.play" % "play-json_2.13" % "2.8.1",
  "mysql" % "mysql-connector-java" % "8.0.15",
  "org.slf4j" % "slf4j-simple" % "1.7.28"
)
