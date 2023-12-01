package service

import javax.inject.Inject

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import com.google.inject.Singleton
import model.LoanRecord
import model.ReportFilter
import model.ReportType

trait LoanService {
  def getLoansReport(
      reportType: Option[ReportType],
      reportFilter: Option[ReportFilter]
  ): Future[List[LoanRecord]]
}

@Singleton
class LoanServiceImpl @Inject() (loanDataAccessService: LoanDataAccessServiceImpl, implicit val ec: ExecutionContext)
    extends LoanService {

  final val filePath            = "/data/LoanStats_securev1_2017Q4.csv"
  final val defaultReportType   = ReportType.Amount
  final val defaultReportFilter = ReportFilter.Grade

  override def getLoansReport(
      reportType: Option[ReportType],
      reportFilter: Option[ReportFilter]
  ): Future[List[LoanRecord]] = Future {
    println(reportType.getOrElse(defaultReportType))
    println(reportFilter.getOrElse(defaultReportFilter))
    loanDataAccessService.getRecords(filePath)
  }.flatten
}
