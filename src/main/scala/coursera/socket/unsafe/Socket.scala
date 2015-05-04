package coursera.socket.unsafe

import coursera.socket.SocketException

object Socket {
  def apply(): Socket = new Socket(){
    var memoryError: Boolean = true
    var sendingError: Boolean = true
  }
}

trait Socket {

  var memoryError: Boolean
  var sendingError: Boolean

  def readFromMemory(): Array[Byte] = {
    Thread.sleep(3000)
    if(memoryError) throw new SocketException("Ups, not enough memory")
    new Array[Byte](10).map(_ => (math.random * 128).toByte)
  }

  def sendToEurope(packet: Array[Byte]): Array[Byte] = {
    Thread.sleep(6000)
    if(sendingError) throw new SocketException("Sorry, the trip didn't go well")
    packet
  }

  def sendPacketToEuropeAndBack(): Unit = {
    val socket = Socket()
    val packet = socket.readFromMemory()
    val confirmation = socket.sendToEurope(packet)
  }

}

