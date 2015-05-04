import coursera.socket.safe.Socket
import scala.concurrent.ExecutionContext.Implicits._
import scala.util.{Failure, Success}

val socket = Socket()
socket.memoryError = false
socket.sendingError = false

val packet = socket.readFromMemory()

val a = 3 + 4

packet.onComplete {
  case Success(p) => {
    val confirmation = socket.sendToEurope(p)
    confirmation.onComplete{
      case Success(c) => println("Yeiiii!!!")
      case Failure(t) => println("Confirmation failed")
    }
  }
  case Failure(t) => println("Sorry, didn't work")
}

val confirmation2 = packet.flatMap(p => socket.sendToEurope(p))

confirmation2.onComplete{
  case Success(c) => println("Yeiiii!!!")
  case Failure(t) => println("Confirmation failed")
}