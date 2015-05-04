package coursera.socket.safe

import scala.util.{Failure, Success}
import scala.concurrent.{ExecutionContext, Future}
import ExecutionContext.Implicits.global
import scala.collection.immutable.Queue
import akka.serialization._
import coursera.socket.{SocketException, EmailMessage}

object Socket {
  def apply(): Socket = new Socket(){
    var memoryError: Boolean = true
    var sendingError: Boolean = true
  }
}

trait Socket {

//  val serialization: Serialization = ???
  var memoryError: Boolean
  var sendingError: Boolean

  val memory = Queue[EmailMessage](
    EmailMessage(from = "Erik", to = "Roland"),
    EmailMessage(from = "Martin", to = "Erik"),
    EmailMessage(from = "Roland", to = "Martin")
  )

  def readFromMemory(): Future[Array[Byte]] = {
    Future {
//      val email = memory.dequeue
//      val serializer = serialization.findSerializerFor(email)
//      serializer.toBinary(email)

      Thread.sleep(3000)
      if(memoryError) throw new SocketException("Ups, not enough memory")
      new Array[Byte](10).map(_ => (math.random * 128).toByte)
    }
  }

  def sendToEurope(packet: Array[Byte]): Future[Array[Byte]] = {
//    Http("mail.server.eu", Request(packet)).filter(_.isOK).map(_.body)
    Future{
      Thread.sleep(6000)
      if(sendingError) throw new SocketException("Sorry, the trip didn't go well")
      packet
    }
  }

  def sendTo(url: String, packet: Array[Byte]): Future[Array[Byte]] =
    Http(url, Request(packet)).filter(_.isOK).map(_.body)

  def sendToSafeI(packet: Array[Byte]): Future[Array[Byte]] =
    sendTo("...europe...", packet) recoverWith {
      case europeError => sendTo("...usa...", packet) recover {
        case usaError  => usaError.getMessage.getBytes
      }
    }

  def sendToSafeII(packet: Array[Byte]): Future[Array[Byte]] =
    sendTo("...europe...", packet) fallbackTo { sendTo("...usa...", packet) } recover {
      case europeError => europeError.getMessage.getBytes
    }

  def sendPacketToEuropeAndBackI(): Unit = {

    val socket = Socket()

    val packet: Future[Array[Byte]] = socket.readFromMemory()

    val confirmation: Unit /* Future[Array[Byte]] */ =
      packet onComplete {
        case Success(p) => socket.sendToEurope(p)
        case Failure(t) => ???
      }

  }

  def sendPacketToEuropeAndBackII(): Unit = {
    val socket = Socket()

    val packet: Future[Array[Byte]] =
      socket.readFromMemory()

    packet onComplete {
      case Success(p) => {
        val confirmation: Future[Array[Byte]] = socket.sendToEurope(p)
        ???
      }
      case Failure(t) => ???
    }
  }

  def sendPacketToEuropeAndBackIII(): Unit = {
    val socket = Socket()
    val packet: Future[Array[Byte]] = socket.readFromMemory()
    val confirmation: Future[Array[Byte]] = packet.flatMap(socket.sendToEurope(_))
  }

  def sendPacketToEuropeAndBackIV(): Unit = {
    val socket = Socket()
    val confirmation: Future[Array[Byte]] = for {
      packet       <- socket.readFromMemory()
      confirmation <- socket.sendToSafeII(packet)
    } yield confirmation
  }

  def sendToAndBackUp(packet: Array[Byte]):Future[(Array[Byte], Array[Byte])] = {
    val europeConfirm = sendTo("...europe...", packet)
    val usaConfirm = sendTo("...usa...", packet)
    europeConfirm.zip(usaConfirm)
  }

}

