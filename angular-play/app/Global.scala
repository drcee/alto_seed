import akka.ChatActors
import play.api.GlobalSettings

/**
 * Created by drcee on 15/09/2014.
 */
object Global extends GlobalSettings {

  override def onStart(application: play.api.Application) {
    ChatActors
  }

  override def onStop(application: play.api.Application) {
    ChatActors.system.shutdown()
  }
}
