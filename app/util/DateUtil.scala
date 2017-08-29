package util

import org.joda.time.LocalDate
import play.api.libs.json.JsValue

object DateUtil {

  def parseDate(date:String) = date match {
    case "" => None
    case "null" => None
    case _ =>  Some(LocalDate.parse(date))
  }

  def mayBeString(js: Option[JsValue]) = {

      js.flatMap { json =>
        json.asOpt[String] map { str =>
          str
        }
      } getOrElse "0"
  }

  def isBetweenInclusive(start: LocalDate, end: LocalDate, target: LocalDate): Boolean =if(!start.equals(LocalDate.parse("0000-01-01")) && !end.equals(LocalDate.parse("0000-01-01"))) target.isAfter(start) && target.isBefore(end) else target.isAfter(start) && end.equals(LocalDate.parse("0000-01-01"))



  def isAfter(start: LocalDate, target: LocalDate):Boolean = target.isAfter(start)

  def noEndDate(target: LocalDate) = target.equals(LocalDate.parse("0000-01-01"))

}
