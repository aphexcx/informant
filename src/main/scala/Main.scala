
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

import scala.language.postfixOps

/**
  * Created by aphex on 1/15/17.
  */
object Main extends App {
  val PAGE = "https://www.amazon.fr/gp/product/B00R7QJBCS/ref=as_li_ss_sm_fb_fr_asin_tl?ie=UTF8&camp=3210&creative=24806&creativeASIN=B00R7QJBCS&linkCode=shr&tag=yondon09-21&qid=1482424536&sr=8-1&keywords=27qhd"

  val browser = JsoupBrowser()

  def price: Float = {
    val priceField: String = browser.get(PAGE) >> element("""span#priceblock_ourprice""") text

    (priceField filter (_.isDigit) toFloat) / 100
  }

  var currentPrice = price

  while(true) {
    val newPrice = price
    println(newPrice)
    if (newPrice < currentPrice) {
      sendMessage(newPrice)
    }
    currentPrice = newPrice
  }
}
