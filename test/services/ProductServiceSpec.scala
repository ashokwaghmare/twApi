package services

import org.apache.commons.io.IOUtils
import org.mockito.Matchers.{eq => isEq}
import org.mockito.{ArgumentCaptor, Matchers}
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeExample
import play.api.libs.iteratee.Enumerator
import play.api.libs.json.Json
import play.api.mvc.{Cookie, Cookies}
import play.api.test.Helpers._


class ProductServiceSpec extends Specification with BeforeExample with Mockito {

  var productService:ProductService = null
  var restClient: RestClient = null;
  def before ={
    restClient = mock[RestClient]
    productService = new ProductService(restClient)
  }

  "product service" should {

    "return active product list" in {

      val input = """[
                    |{
                    |"endDate": "2017-04-04",
                    |"startDate": "2017-01-30",
                    |"price": 260,
                    |"category": "Kitchen",
                    |"name": "Stainless Steel Cutter Peeler Tool Pineapple Seed Clip Home Kitchen Gadgets"
                    |},
                    |  {
                    |"endDate": "2017-12-04",
                    |"startDate": "2017-01-30",
                    |"price": 149,
                    |"category": "Kitchen",
                    |"name": "20.5cm Fruit Cutter Chef Kitchen Cutlery Knife Knives Choice - 07"
                    |},
                    |  {
                    |"endDate": null,
                    |"startDate": "2017-01-30",
                    |"price": 1737,
                    |"category": "Electronics",
                    |"name": "LETV LeEco Le 2 32GB Rose Gold"
                    |},
                    |  {
                    |"endDate": null,
                    |"startDate": "2018-01-30",
                    |"price": 999,
                    |"category": "Electronics",
                    |"name": "Nokia 1100"
                    |},
                    |  {
                    |"endDate": null,
                    |"startDate": "2018-01-30",
                    |"price": 499,
                    |"category": "Furniture",
                    |"name": "Homefab India Set of 2 Beautiful Marble Plain Black Curtains (HF342)"
                    |}
                    |]""".stripMargin

      val a = Json.parse(input).as[JsArray]

      restClient.get(Matchers.any[String], Matchers.any[Map[String, String]]) returns (Future.successful(Right(a)))


      productService.getActiveProduct().map{  prod =>
        prod mustEqual(List(Product("", "", "")))
      }

    }
  }

}
