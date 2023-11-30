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

  def getLoanReport: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    // TODO: val queryParams = request.queryString
    val results = loanService.getLoansReport(ReportType.Amount, ReportFilter.JobTitle)
    Ok(Json.toJson(results))
  }

}
