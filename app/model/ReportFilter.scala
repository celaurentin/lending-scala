package model

import enumeratum._

import scala.collection.immutable

sealed trait Filter extends EnumEntry
object Filter extends Enum[Filter] {

  override val values: immutable.IndexedSeq[Filter] = findValues

  case object JobTitle extends Filter
  case object State extends Filter
  case object Grade extends Filter
  case object Purpose extends Filter

}
