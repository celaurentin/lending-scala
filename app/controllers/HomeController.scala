package controllers

import javax.inject._

import model.ReportFilter
import model.ReportType
import play.api.libs.json.Json
import play.api.mvc._
import service.LoanServiceImpl

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (cc: ControllerComponents, loanService: LoanServiceImpl)(
    implicit assetsFinder: AssetsFinder
) extends AbstractController(cc) {

  def getLoanReport(reportType: String, reportFilter: String): Action[AnyContent] = Action {
    val results = loanService.getLoansReport(ReportType.withName(reportType), ReportFilter.withName(reportFilter))
    Ok(Json.toJson(results))
  }

}
