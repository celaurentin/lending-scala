package service

import javax.inject.Inject

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import com.google.inject.Singleton
import controllers.model.DataPoint
import controllers.model.LoanReport
import model.LoanGrade
import model.LoanRecord
import model.ReportFilter
import model.ReportType

trait LoanService {
  def getLoansReport(
      reportType: String,
      reportFilter: String
  ): Future[LoanReport]
}

@Singleton
class LoanServiceImpl @Inject() (loanDataAccessService: LoanDataAccessServiceImpl, implicit val ec: ExecutionContext)
    extends LoanService {

  final val filePath            = "/data/LoanStats_securev1_2017Q4.csv"
  final val defaultReportType   = ReportType.Amount
  final val defaultReportFilter = ReportFilter.Grade

  override def getLoansReport(
      reportType: String,
      reportFilter: String
  ): Future[LoanReport] = Future {

    val _type  = ReportType.withNameInsensitiveOption(reportType).getOrElse(defaultReportType)
    val filter = ReportFilter.withNameInsensitiveOption(reportFilter).getOrElse(defaultReportFilter)

    if (_type == ReportType.Amount)
      loanDataAccessService
        .getRecords(filePath)
        .map(data => buildDollarReport(filter, data))
    else
      loanDataAccessService
        .getRecords(filePath)
        .map(data => buildCountReport(filter, data))

  }.flatten

  private def totalAmount(records: List[LoanRecord]): Int =
    records.foldLeft(0)((acumm, loan) => acumm + loan.amount)

  private def amountByGrade(records: List[LoanRecord]): List[(LoanGrade, Int)] =
    records
      .groupBy(_.grade)
      .map(byGrade => (byGrade._1, totalAmount(byGrade._2)))
      .toList
      .sortBy(_._2).reverse
  private def buildDollarReport(filter: ReportFilter, data: List[LoanRecord]): LoanReport = {
    val dataPoints = amountByGrade(data).map { dp =>
      DataPoint(
        label = dp._1.entryName,
        value = dp._2.toString
      )
    }
    LoanReport(
      title = "Total Loans by Grade",
      xLabel = "Grades",
      yLabel = "Total Loans - Millions",
      data = dataPoints
    )
  }

  private def buildCountReport(filter: ReportFilter, data: List[LoanRecord]): LoanReport = ???

}
