import slick.codegen.SourceCodeGenerator
import slick.{model => m}

class CustomGenerator(model: m.Model) extends SourceCodeGenerator(model) {

  // add some custom imports
  override def code = "import com.github.tototoshi.slick.MySQLJodaSupport._\n" + "import org.joda.time.DateTime\n" + super.code
  override def Table = new Table(_) {
    override def autoIncLastAsOption = true
    override def Column = new Column(_) {
      override def rawType = model.tpe match {
        case "java.sql.Timestamp" => "DateTime" // kill j.s.Timestamp
        case "java.sql.Date" => "DateTime" // kill j.s.Timestamp
        case _ => super.rawType
      }
    }
  }
}