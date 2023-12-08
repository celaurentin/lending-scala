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
      groupingKey: String
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
      groupingKey: String
  ): Future[LoanReport] = Future {

    val _type        = ReportType.withNameInsensitiveOption(reportType).getOrElse(defaultReportType)
    val _groupingKey = ReportFilter.withNameInsensitiveOption(groupingKey).getOrElse(defaultReportFilter)

    if (_type == ReportType.Amount)
      loanDataAccessService
        .getRecords(filePath)
        .map(data => buildReport(_groupingKey, totalAmount, data, "Millions"))
    else
      loanDataAccessService
        .getRecords(filePath)
        .map(data => buildReport(_groupingKey, totalCount, data, "Count"))

  }.flatten

  private def getGroupingKey(record: LoanRecord, groupingKey: ReportFilter) =
    groupingKey match {
      case ReportFilter.JobTitle => record.jobTitle
      case ReportFilter.State    => record.state
      case ReportFilter.Grade    => record.grade
      case ReportFilter.Purpose  => record.purpose
    }

  private def totalAmount(records: List[LoanRecord]): Int =
    records.foldLeft(0)((acumm, loan) => acumm + loan.amount)

  private def totalCount(records: List[LoanRecord]): Int = records.size

  private def totalByFilter(
      records: List[LoanRecord],
      f: List[LoanRecord] => Int,
      groupingKey: ReportFilter
  ): List[(Object, Int)] =
    records
      .groupBy(getGroupingKey(_, groupingKey))
      .map {
        case (groupKeyValue, data) => (groupKeyValue, f(data))
      }
      .toList
      .sortBy(_._2)
      .reverse

  private def buildReport(
      groupingKey: ReportFilter,
      f: List[LoanRecord] => Int,
      data: List[LoanRecord],
      yLabel: String
  ): LoanReport = {
    val dataPoints = totalByFilter(data, f, groupingKey).map {
      case (label, value) =>
        DataPoint(
          label = label.toString,
          value = value.toString
        )
    }
    LoanReport(
      title = s"Total Loans by ${groupingKey.entryName}",
      xLabel = s"${groupingKey.entryName}s",
      yLabel = s"Total Loans - $yLabel",
      data = dataPoints
    )
  }

}
