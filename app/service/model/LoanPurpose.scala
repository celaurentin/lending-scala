package model

import enumeratum.EnumEntry.Snakecase
import enumeratum._

import scala.collection.immutable

sealed abstract class LoanPurpose extends EnumEntry with Snakecase
object LoanPurpose extends Enum[LoanPurpose] with PlayJsonEnum[LoanPurpose] {

  override val values: immutable.IndexedSeq[LoanPurpose] = findValues

  case object Car extends LoanPurpose
  case object CreditCard extends LoanPurpose
  case object DebtConsolidation extends LoanPurpose
  case object Educational extends LoanPurpose
  case object HomeImprovement extends LoanPurpose
  case object House extends LoanPurpose
  case object MajorPurchase extends LoanPurpose
  case object Medical extends LoanPurpose
  case object Moving extends LoanPurpose
  case object Other extends LoanPurpose
  case object RenewableEnergy extends LoanPurpose
  case object SmallBusiness extends LoanPurpose
  case object Vacation extends LoanPurpose
}
