package service

import com.github.tototoshi.csv.CSVReader
import com.google.inject.Singleton
import model.{FicoRange, ReportFilter, LoanGrade, LoanPurpose, LoanRecord, LoanStatus, ReportType}

import java.io.InputStreamReader

trait LoanService {
  def getLoansReport(reportType: ReportType, filter: ReportFilter): List[LoanRecord]
}

@Singleton
class LoanServiceImpl extends LoanService {

  final val filePath = "/data/LoanStats_securev1_2017Q4.csv"
  private var loanRecords: List[LoanRecord] = List.empty

  override def getLoansReport(reportType: ReportType, filter: ReportFilter): List[LoanRecord] = getRecords


  private def getRecords: List[LoanRecord] = {
    if (loanRecords.isEmpty) {
      val inputStream = this.getClass.getResourceAsStream(filePath)
      val reader = CSVReader.open(new InputStreamReader(inputStream))
      loanRecords = reader.toStreamWithHeaders.map { row =>
        println(row)
        LoanRecord(
          id = row("id").toLong,
          amount = row("loan_amnt").toInt,
          term = row("term"),
          grade = LoanGrade.withName(row("grade")),
          jobTitle = row("emp_title"),
          issueDate = row("issue_d"),
          status = LoanStatus.withName(row("loan_status")),
          state = row("addr_state"),
          purpose = LoanPurpose.withName(row("purpose")),
          ficoRange = FicoRange(row("fico_range_low").toInt, row("fico_range_high").toInt)
        )
      }.collect({ case lr: LoanRecord => lr }).toList
    }
    loanRecords
  }
}
