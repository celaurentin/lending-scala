package model

import play.api.libs.json._


case class LoanRecord(
    id: Long,
    amount: Int,
    term: String,
    grade: LoanGrade,
    jobTitle: String,
    issueDate: String,
    status: LoanStatus,
    state: String,
    purpose: LoanPurpose,
    ficoRange: FicoRange
)

object LoanRecord {

  implicit val LoanRecordFormat: OWrites[LoanRecord] = Json.writes[LoanRecord]
}
