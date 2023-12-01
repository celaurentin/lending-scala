package model

import scala.collection.immutable

import enumeratum._

sealed trait ReportType extends EnumEntry
object ReportType extends Enum[ReportType] with PlayJsonEnum[ReportType] {

  override val values: immutable.IndexedSeq[ReportType] = findValues

  case object Amount extends ReportType
  case object Count  extends ReportType

}
