package model

import play.api.libs.json.Json
import play.api.libs.json.OWrites

case class FicoRange(
  low: Int,
  high: Int
)
