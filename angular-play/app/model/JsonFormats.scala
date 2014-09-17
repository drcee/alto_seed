package model

import com.ats.domain.TradeSetupFactor
import com.ats.domain.price.Price
import org.joda.time.DateTime
import play.api.libs.json.{JsValue, JsNumber, JsString, JsObject}

/**
 * Created by drcee on 16/09/2014.
 */
object JsonFormats {

  implicit def tradeIdea(tradeIdea : TradeIdea) ={

    JsObject(
      Seq(
        "name" -> JsString(tradeIdea.name),
        "instrument" -> JsString(tradeIdea.instrument)
      )
    )
  }

    implicit def convertPrice(price : Price):JsValue = {
      JsObject(
        Seq(
          "messageType" -> JsString("PRICE"),
          "instrument" -> JsString(price.instrument),
          "timestamp" -> JsNumber(price.dateTime),
          "date" -> JsString(new DateTime(price.dateTime).toString("yyyy-MM-dd")),
          "open" -> JsNumber(price.open.asNumber),
          "high" -> JsNumber(price.high.asNumber),
          "low" -> JsNumber(price.low.asNumber),
          "close" -> JsNumber(price.close.asNumber)
        )
      )
    }

  implicit def convertTradeFactor(factor : TradeSetupFactor) = {
    JsObject{
      Seq(
        "messageType" -> JsString("TRADESETUPFACTOR"),
        "type" -> JsString(factor.toString)
      )
    }

  }
}
