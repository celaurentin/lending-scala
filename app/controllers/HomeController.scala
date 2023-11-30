package controllers

import java.io.InputStreamReader

import com.github.tototoshi.csv.CSVReader
import javax.inject._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) (implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {

  private final val filePath = "/data/LoanStats_securev1_2017Q4.csv"
  def index = Action {
    val inputStream = this.getClass.getResourceAsStream(filePath)
    val reader = CSVReader.open(new InputStreamReader(inputStream))
    val header = reader.toStream.head
    Ok(s"Loaded file $filePath with header: [${header.mkString(", ")}]")
  }

}
