package services

import javax.inject.Inject

import play.api.libs.json.{JsNumber, JsObject, JsValue, Json}
import play.api.libs.ws.WSClient

import scala.concurrent.ExecutionContext.Implicits.global

class RestClient @Inject()(ws :WSClient) {

  def get(url: String, headers: Map[String, String]) = {

    ws.url(url).withHeaders(headers.toSeq:_*).get().map{ response =>
      response.status match {
        case 200 => Right(response.json)
        case _ => Left((response.status, response.statusText))
      }
    }
  }

  def post(url: String, headers: Map[String, String], body: JsValue) = {

    println("posting????....>>"+body)
    //val inputJson = JsObject(Seq(("output" -> Json.obj("count"->JsNumber(5)))))

    ws.url(url).withHeaders(headers.toSeq:_*).post(body).map{ response =>
      response.status match {
        case 200 => {println("after posting>>"+response); Right(response)}
        case _ => {println("wrong..post..."+response.body);  Left((response.status, response.body))}
      }
    }
  }

}
