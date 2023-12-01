package service

import javax.inject.Inject

import com.google.inject.Singleton
import model.LoanRecord
import model.ReportFilter
import model.ReportType

trait LoanService {
  def getLoansReport(reportType: Option[ReportType], reportFilter: Option[ReportFilter]): List[LoanRecord]
}

@Singleton
class LoanServiceImpl @Inject() (loanDataAccessService: LoanDataAccessServiceImpl) extends LoanService {

  final val filePath            = "/data/LoanStats_securev1_2017Q4.csv"
  final val defaultReportType   = ReportType.Amount
  final val defaultReportFilter = ReportFilter.Grade

  override def getLoansReport(reportType: Option[ReportType], reportFilter: Option[ReportFilter]): List[LoanRecord] = {

    println(reportType.getOrElse(defaultReportType))
    println(reportFilter.getOrElse(defaultReportFilter))
    val result = loanDataAccessService.getRecords(filePath)
    result
  }
}
