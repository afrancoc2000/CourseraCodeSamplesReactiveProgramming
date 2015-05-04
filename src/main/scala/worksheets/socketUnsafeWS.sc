import coursera.socket.unsafe.Socket

val socket = Socket()

socket.memoryError = false
socket.sendingError = false

val packet = socket.readFromMemory()

val a = 3 + 4

val confirmation = socket.sendToEurope(packet)

