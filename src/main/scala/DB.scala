import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._
import slick.model.{Model, QualifiedName}
import slick.profile.RelationalProfile.ColumnOption.Default
import slick.profile.SqlProfile.ColumnOption.SqlType

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

/**
  * Main
  * ../app/models/Tables.scala
  */
object DB extends App {

  import com.typesafe.config.ConfigFactory

  val slickDriver = "slick.driver.MySQLDriver"
  val jdbcDriver = "com.mysql.jdbc.Driver"
  val dbSettingConf = ConfigFactory.load.getConfig("db_setting")
  val url = dbSettingConf.getString("url")
  val outputFolder = dbSettingConf.getString("outputFolder")
  val outputFileName = dbSettingConf.getString("outputFileName")
  val pkg = dbSettingConf.getString("pkg")
  val user = dbSettingConf.getString("user")
  val password = dbSettingConf.getString("password")
  val driver: JdbcProfile = slick.driver.MySQLDriver
  val db = Database.forURL(url, driver = jdbcDriver, user = user, password = password)
  val model = Await.result(db.run(driver.createModel(None, ignoreInvalidDefaults = false)(ExecutionContext.global).withPinnedSession), Duration.Inf)
  // Remove create_date and update_date from outputFile
  val ts = for {
    t <- model.tables.filter(_.name.table != "play_evolutions")
    c = t.columns.filter(_.name != "create_date").filter(_.name != "update_date")
  } yield {
    val cc = c.head.table match {
      case QualifiedName(x, _, _) =>
        for (a <- c) yield {
          if (a.name == "private") {
            a.copy(nullable = true, options = Set(SqlType("BIT"), Default(Some(false))))
          }
          else a
        }
      case _ => c
    }
    slick.model.Table(t.name, cc, t.primaryKey, ArrayBuffer(), ArrayBuffer(), t.options)
  }
  val fModel = Model(tables = ts)
  val codeGenFuture = new CustomGenerator(fModel).writeToFile(slickDriver, outputFolder, pkg, outputFileName, s"$outputFileName.scala")
}
