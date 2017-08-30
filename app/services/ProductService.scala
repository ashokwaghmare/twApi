package services

import javax.inject.Inject

import org.joda.time.{DateTime, LocalDate}
import play.api.libs.json.{JsArray, JsValue, Json}
import util.{DateUtil, StringUtil}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class Product(name: String, price: Int, category: String, endDate: Option[LocalDate] = Some(LocalDate.now()), startDate: Option[LocalDate] = Some(LocalDate.now()))

case class JsProduct(name: JsValue, price: JsValue, category: JsValue, endDate: JsValue, startDate: JsValue){

 val endDateStr = StringUtil.mayBeString(Some(endDate))
 val startDateStr = StringUtil.mayBeString(Some(startDate))

  val parsedEndDate = DateUtil.parseDate(endDateStr)
  val parsedStartDate = DateUtil.parseDate(startDateStr)
  val cat = StringUtil.mayBeString(Some(category))
  val nm = StringUtil.mayBeString(Some(name))
  val pr = StringUtil.mayBeInt(Some(price))

  def getProduct = Product(nm, pr, cat, parsedEndDate, parsedStartDate)

}

class ProductService @Inject()(restClient: RestClient) {
  val headers = Map("userId"->"BywC6BhOW")
  val headersForPost  = Map("userId"->"BywC6BhOW", "Content-Type" -> "application/json")
  val inputPutUrl = "http://tw-http-hunt-api-1062625224.us-east-2.elb.amazonaws.com/challenge/input"
  val outPutUrl = "http://tw-http-hunt-api-1062625224.us-east-2.elb.amazonaws.com/challenge/output"

  def numberOfProducts() = {
    restClient.get(inputPutUrl, headers).map {
      case Right(response) => {
        println("products::"+response)
        (response \\ "name").size
      }
    }
  }

  def activeProduct():Future[Seq[Product]] = {
    restClient.get(inputPutUrl, headers).map {
      case Right(response) => {
        println("products::"+response)
        val jsArray = response.as[JsArray].value

        println("jsArray>>"+jsArray)

        val allProducts = jsArray.map(a => JsProduct((a \\ "name").head, (a \\ "price").head, (a \\ "category").head,
          (a \\ "endDate").head, (a \\ "startDate").head).getProduct)

        println("allProducts>>"+allProducts)


        allProducts.filter(p => DateUtil.isBetweenInclusive(p.startDate.get, p.endDate.get, LocalDate.parse("2017-08-25")))

      }
    }
  }

  def groupProductByCategory(products: Seq[Product]): Map[String, Seq[Product]] = products.groupBy(p => p.category)

  def postResult(json: JsValue) = {
    restClient.post(outPutUrl, headersForPost, json).map {
      case Right(response) => response.body
    }

  }



}
