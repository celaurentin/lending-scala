package service

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContextExecutor
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
class LoanServiceImpl @Inject() (loanDataAccessService: LoanDataAccessServiceImpl) extends LoanService {

  final val filePath            = "/data/LoanStats_securev1_2017Q4.csv"
  final val defaultReportType   = ReportType.Amount
  final val defaultReportFilter = ReportFilter.Grade

  val executorService: ExecutorService      = Executors.newFixedThreadPool(2)
  implicit val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(executorService)

  override def getLoansReport(
      reportType: Option[ReportType],
      reportFilter: Option[ReportFilter]
  ): Future[List[LoanRecord]] = Future {

    println(reportType.getOrElse(defaultReportType))
    println(reportFilter.getOrElse(defaultReportFilter))
    val result = loanDataAccessService.getRecords(filePath)
    result
  }
}
