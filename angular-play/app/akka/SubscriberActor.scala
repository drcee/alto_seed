package akka

import akka.actor.{ActorSystem, Props, Actor}
import com.ats.analytics.strategy.BULLISH_ENG_CANDLE
import com.ats.domain.{RunStrategy, TIME_EXIT, SMA_CROSS, StrategyBuilder}
import com.ats.domain.price.P
import com.ats.domain.time.TimePeriod.{DAILY, HOURLY}
import com.ats.events._
import controllers.PushController
import model.JsonFormats
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.duration._
/**
 * Created by drcee on 16/09/2014.
 */
object SubscriberActors {

  /** SSE-Chat actor system */
  val system = ActorSystem("price-feed")

  val subscriber = system.actorOf(Props(new SubscriberActor()))

  //val priceBroker =  system.actorOf(Props(classOf[PriceBroker],"SPX",subscriber),"priceBroker")
  //priceBroker ! RunTestScenario(true)

  val messageBroker = system.actorOf(Props(classOf[MessageBroker], "SPX",subscriber), "messageBroker") //  "priceActor-SPX")

  val longBullishEng = new StrategyBuilder("spx-bullishEng_sma") goLong "SPX" over DAILY withTradeSetups(BULLISH_ENG_CANDLE,SMA_CROSS) exitTradeOn TIME_EXIT(7) build

  messageBroker ! RegisterStrategy(RunStrategy(longBullishEng))
  messageBroker ! RunTestScenario(true)
}

//just using this to push messages through on a schedule
class SubscriberSupervisor extends Actor {

  val price = P("123.45","456","789","222",123L,HOURLY,"SPX")

  val subscriber = context.actorOf(Props(new SubscriberActor()))
  context.system.scheduler.schedule(1 seconds, 8 seconds, subscriber , PriceMessage(price))

  def receive = {
    case _ =>
  }
}


class SubscriberActor extends Actor{

  import JsonFormats._

  def receive = {

    case PriceMessage(price) =>
        //push through to channel
        //PushController.messageChannel.push(price)

    case  MultiSignalMessage(price,strategy,factors) =>
        factors.map(value => PushController.messageChannel.push(value))
    case unknown =>

  }
}
