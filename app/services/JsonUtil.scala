package services

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper, SerializationFeature}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

object JsonUtil {

  val mapper = new ObjectMapper with ScalaObjectMapper

  mapper.registerModule(DefaultScalaModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS , false)
  import java.text.SimpleDateFormat
  val df = new SimpleDateFormat("yyyy-dd-MM")
  mapper.setDateFormat(df)

  def toJson(value: Any): String = {
    mapper.writeValueAsString(value)
  }

  def fromJson[T](jsonString: String)(implicit m : Manifest[T]): T = {
    mapper.readValue[T](jsonString)
  }

}
