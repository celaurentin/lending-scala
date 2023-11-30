package model

import enumeratum._

import scala.collection.immutable

sealed trait ReportFilter extends EnumEntry
object ReportFilter extends Enum[ReportFilter] with PlayJsonEnum[ReportFilter]  {

  override val values: immutable.IndexedSeq[ReportFilter] = findValues

  case object JobTitle extends ReportFilter
  case object State extends ReportFilter
  case object Grade extends ReportFilter
  case object Purpose extends ReportFilter

}
