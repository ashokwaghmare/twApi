package controllers

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import javax.inject._

import play.api._
import play.api.http.{ContentTypeOf, Writeable}
import play.api.libs.json._
import play.api.libs.ws.{WS, WSClient}
import play.api.mvc._
import services.{JsonUtil, ProductService, RestClient}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.parsing.json.{JSONArray, JSONObject}
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.joda.time.LocalDate


@Singleton
class HomeController @Inject()(productService: ProductService) extends Controller {

  def countOfproduct = Action.async {
      val outPutJson = productService.numberOfProducts().map { size =>
        JsObject(Seq(("output" -> Json.obj("count"->JsNumber(size)))))
      }
    outPutJson.flatMap( outJson =>
        productService.postResult(outJson).map{ response => Ok(response)}
      )
  }

  def activeProduct = Action.async {

    productService.activeProduct().map { size =>
      Ok(size.toString())
      //JsObject(Seq(("output" -> Json.obj("count"->JsNumber(size)))))
    }
    //Future(Ok(""))
  }

  implicit val writer = new Writes[services.Product] {
    def writes(t: services.Product): JsValue = {
      val endDate = new SimpleDateFormat("yyyy-dd-MM").format(t.endDate.get.toDate)
      val startDate = new SimpleDateFormat("yyyy-dd-MM").format(t.startDate.get.toDate)

      val endDateWithNull = if(endDate.equals("0001-01-01")) JsNull else JsString(endDate)
      val startDateWithNull = if(startDate.equals("0001-01-01")) JsNull else JsString(startDate)

      Json.obj("name"-> JsString(t.name), "price" -> JsNumber(t.price),
        "category" -> JsString(t.category), "endDate" -> endDateWithNull,
        "startDate" -> startDateWithNull)
    }


  }



  def activeProductByCategory = Action.async {

    productService.activeProduct().flatMap { products =>
      val productBycat = productService.groupProductByCategory(products)
      val mp1 = Json.parse(JsonUtil.toJson(Map("output" ->productBycat.map(p => p._1 -> p._2.size))))

      //ver-1
      //val mp = Json.toJson(productBycat.map(p => p._1 -> Json.toJson(p._2)))

       //val mp = Json.toJson(productBycat.map(p => p._1 -> Json.parse(JsonUtil.toJson(p._2))))

      //val p = Json.toJson(productBycat.map(p => Json.obj(p._1 -> JsNumber(p._2.size))))

      //val mp1 = Json.obj("output" -> p)

      //Ok(mp)
      //productService.postResult(Json.obj("output" -> mp)).map{ response => Ok(response)}
      productService.postResult(mp1).map{ response => Ok(response)}
    }

  }

}
