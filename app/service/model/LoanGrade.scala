package model

import enumeratum._

import scala.collection.immutable

sealed trait LoanGrade extends EnumEntry
object LoanGrade extends Enum[LoanGrade] with PlayJsonEnum[LoanGrade] {

  override val values: immutable.IndexedSeq[LoanGrade] = findValues

  case object A extends LoanGrade
  case object B extends LoanGrade
  case object C extends LoanGrade
  case object D extends LoanGrade
  case object E extends LoanGrade
  case object F extends LoanGrade
  case object G extends LoanGrade
}
