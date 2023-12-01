package service

import java.io.InputStreamReader
import javax.inject.Singleton

import com.github.tototoshi.csv.CSVReader
import model._
import service.LoanFields._

trait LoanDataAccessService {

  def getRecords(filePath: String): List[LoanRecord]
}

@Singleton
class LoanDataAccessServiceImpl extends LoanDataAccessService {

  private var loanRecords: List[LoanRecord] = List.empty

  override def getRecords(filePath: String): List[LoanRecord] = {
    if (loanRecords.isEmpty) {
      val inputStream = this.getClass.getResourceAsStream(filePath)
      val reader      = CSVReader.open(new InputStreamReader(inputStream))
      loanRecords = reader.toStreamWithHeaders
        .map { row =>
          LoanRecord(
            id = row(id).toLong,
            amount = row(loanAmount).toInt,
            term = row(loanTerm),
            grade = LoanGrade.withName(row(loanGrade)),
            jobTitle = row(jobTitle),
            issueDate = row(issueDate),
            status = LoanStatus.withName(row(loanStatus)),
            state = row(state),
            purpose = LoanPurpose.withName(row(loanPurpose)),
            ficoRange = FicoRange(row(ficoRangeLow).toInt, row(ficoRangeHigh).toInt)
          )
        }
        .collect { case lr: LoanRecord => lr }
        .toList
    }
    loanRecords
  }
}

object LoanFields {
  val id            = "id"
  val loanAmount    = "loan_amnt"
  val loanTerm      = "term"
  val loanGrade     = "grade"
  val jobTitle      = "emp_title"
  val issueDate     = "issue_d"
  val loanStatus    = "loan_status"
  val state         = "addr_state"
  val loanPurpose   = "purpose"
  val ficoRangeLow  = "fico_range_low"
  val ficoRangeHigh = "fico_range_high"
}
