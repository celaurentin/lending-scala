package model

import enumeratum._

import scala.collection.immutable

sealed abstract class LoanStatus(override val entryName: String) extends EnumEntry
object LoanStatus extends Enum[LoanStatus] with PlayJsonEnum[LoanStatus]{

  override val values: immutable.IndexedSeq[LoanStatus] = findValues

  case object ChargedOff extends LoanStatus("Charged Off")
  case object Current extends LoanStatus("Current")
  case object Default extends LoanStatus("Default")
  case object FullyPaid extends LoanStatus("Fully Paid")
  case object InGrace extends LoanStatus("In Grace Period")
  case object Late30 extends LoanStatus("Late (16-30 days)")
  case object Late120 extends LoanStatus("Late (31-120 days)")
}
