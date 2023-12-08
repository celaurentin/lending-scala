package controllers

import javax.inject._
import scala.concurrent.ExecutionContext
import play.api.libs.json.Json
import play.api.mvc._
import service.LoanServiceImpl

/**
 * This controller creates an async `Action` to handle HTTP requests to the
 * application's endpoint.
 */
@Singleton
class LendingController @Inject() (cc: ControllerComponents, loanService: LoanServiceImpl)(
    implicit assetsFinder: AssetsFinder,
    implicit val ec: ExecutionContext
) extends AbstractController(cc) {

  def getLoanReport(reportType: String, groupingKey: String): Action[AnyContent] = Action.async {
    loanService
      .getLoansReport(
        reportType,
        groupingKey
      )
      .map(r => Ok(Json.toJson(r)))
  }

}
