package util

import org.joda.time.LocalDate
import play.api.libs.json.JsValue

object StringUtil {

  def mayBeString(js: Option[JsValue]) = {

      js.flatMap { json =>
        json.asOpt[String] map { str =>
          str
        }
      } getOrElse "0"
  }

  def mayBeInt(js: Option[JsValue]) = {

    js.flatMap { json =>
      json.asOpt[Int] map { str =>
        str
      }
    } getOrElse 0
  }

}
