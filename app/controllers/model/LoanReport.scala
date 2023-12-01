package controllers.model

import play.api.libs.json._

case class LoanReport(
    title: String,
    xLabel: String,
    yLabel: String,
    data: List[DataPoint]
)

case class DataPoint(
    label: String,
    value: String
)


object LoanReport {
  implicit val LoanReportFormat: OWrites[LoanReport] = Json.writes[LoanReport]

}

object DataPoint {
  implicit val DataPointFormat: OWrites[DataPoint] = Json.writes[DataPoint]

}
