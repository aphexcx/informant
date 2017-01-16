/**
  * Created by aphex on 1/15/17.
  */

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import co.datamonsters.facebot._
import co.datamonsters.facebot.api.{Message, Messaging}
import co.datamonsters.facebot.api.NotificationType.Regular
import scala.io.StdIn

object runDebug {
  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem("facebotActorSystem")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val facebotConnection = AkkaHttpConnection(Credentials(
      "EAACXksYN6lgBAFVECWFZCLONZAZBr4MP8g55ne6JIqZBPog7SubQYZANKIj6ZAC7CJ9jZA7h3ixSlTvKKCyHkTuwRJlVZAS5Sh9SZCsf8daqvj6o3ZCpXMk49fuLwqm8ZBZAxS0VceBDUPD6iPjgckii6ziXECy2RGPPMKjZBF1RD78flWwZDZD",
      "token")) {
      case sendApi@Event(_, Messaging.MessageReceived(sender, _, message))
        if !message.isEcho =>
        sendApi.sendMessage(sender, Message("MESSAGE_TEXT"), Regular)
    }

    val bindingFuture =
      Http().bindAndHandle(facebotConnection.facebookRestRoute, "localhost", 8701)
    StdIn.readLine() // let it run until user presses return
    bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())

  }
}