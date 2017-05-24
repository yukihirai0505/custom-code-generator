name := """code-generater"""
version := "1.0"
scalaVersion := "2.11.7"

libraryDependencies ++= {
  val slickV = "3.1.1"
  val slickJodaMapperV = "2.2.0"
  val jodaTimeV = "2.9.3"
  val jodaConvertV = "1.8.1"
  Seq(
    "org.scalatest" %% "scalatest" % "2.2.4" % "test",
    "com.jsuereth" %% "scala-arm" % "1.4",
    "com.typesafe" % "config" % "1.3.1",
    "com.typesafe.slick" % "slick_2.11" % slickV,
    "com.typesafe.slick" % "slick-codegen_2.11" % slickV,
    "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
    "com.github.tototoshi" % "slick-joda-mapper_2.11" % slickJodaMapperV,
    "joda-time" % "joda-time" % jodaTimeV,
    "org.joda" % "joda-convert" % jodaConvertV
  )
}

