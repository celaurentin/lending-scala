package service

import javax.inject.Inject

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import com.google.inject.Singleton
import controllers.model.DataPoint
import controllers.model.LoanReport
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

  private def getGroupingKey[T](record: LoanRecord, filter: ReportFilter) =
    filter match {
      case ReportFilter.JobTitle => record.jobTitle
      case ReportFilter.State    => record.state
      case ReportFilter.Grade    => record.grade
      case ReportFilter.Purpose  => record.purpose
    }

  private def totalAmount(records: List[LoanRecord]): Int =
    records.foldLeft(0)((acumm, loan) => acumm + loan.amount)

  private def amountByFilter(records: List[LoanRecord], filter: ReportFilter): List[(Object, Int)] =
    records
      .groupBy(getGroupingKey(_, filter))
      .map(byFilter => (byFilter._1, totalAmount(byFilter._2)))
      .toList
      .sortBy(_._2)
      .reverse

  private def countByFilter(records: List[LoanRecord], filter: ReportFilter): List[(Object, Int)] =
    records
      .groupBy(getGroupingKey(_, filter))
      .map(byFilter => (byFilter._1, byFilter._2.size))
      .toList
      .sortBy(_._2)
      .reverse

  private def buildDollarReport(filter: ReportFilter, data: List[LoanRecord]): LoanReport = {
    val dataPoints = amountByFilter(data, filter).map { dp =>
      DataPoint(
        label = dp._1.toString,
        value = dp._2.toString
      )
    }
    LoanReport(
      title = s"Total Loans by ${filter.entryName}",
      xLabel = s"${filter.entryName}s",
      yLabel = "Total Loans - Millions",
      data = dataPoints
    )
  }

  private def buildCountReport(filter: ReportFilter, data: List[LoanRecord]): LoanReport = {
    val dataPoints = countByFilter(data, filter).map { dp =>
      DataPoint(
        label = dp._1.toString,
        value = dp._2.toString
      )
    }
    LoanReport(
      title = s"Total Loans by ${filter.entryName}",
      xLabel = s"${filter.entryName}s",
      yLabel = "Total Loans - Count",
      data = dataPoints
    )
  }

}
