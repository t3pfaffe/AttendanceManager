package server

import com.corundumstudio.socketio.listener.{ConnectListener, DataListener, DisconnectListener}
import com.corundumstudio.socketio.{AckRequest, Configuration, SocketIOClient, SocketIOServer}
import org.joda.time.DateTime
import org.ubseds.database.datatypes.Person
import org.ubseds.database.{ClockManager, DuplicateKeyException}
import org.ubseds.events.InvalidationObserver
import org.ubseds.server.messages.receive.NumClockMsg
import org.ubseds.server.messages.send.{ClockInStatusMsg, ClockedInUpdateMsg, MotdSendMsg}

/**
 * @project AttendanceManager
 * @author t3pfaffe on 5/20/2020
 */
class AttendanceManagerServer() {

  val MOTD_Message = new MotdSendMsg("Welcome to UB SEDS Lab")

  val config: Configuration = new Configuration {
    setHostname("localhost")
    setPort(8080)
  }

  val server: SocketIOServer = new SocketIOServer(config)


  server.addDisconnectListener(new DisconnectionListener())
  server.addConnectListener(new ConnectionListener(this))
  server.addEventListener( NumClockMsg.name, classOf[String], new NewClockInListener(this))

  val clockManager = new ClockManager()
  clockManager.clockedIn.addObserver(new ClockedInListListener(this))

  server.start()
}

object AttendanceManagerServer {
  def main(args: Array[String]): Unit = {
    new AttendanceManagerServer()
  }
}

class DisconnectionListener() extends DisconnectListener {
  override def onDisconnect(socket: SocketIOClient): Unit = {
    //hmm
  }
}

class ConnectionListener(server: AttendanceManagerServer) extends ConnectListener {
  override def onConnect(client: SocketIOClient): Unit = {
    val clockedInUpdateMsg = new ClockedInUpdateMsg()

    client.send(server.MOTD_Message.getMessage())
    client.send(clockedInUpdateMsg.getMessage(server.clockManager.clockedIn.clocked()))
  }
}

class NewClockOffListener(server: AttendanceManagerServer) extends DataListener[String] {
  override def onData(client: SocketIOClient, rawNumData: String, ackSender: AckRequest): Unit = {
    var ackMsg: String = ClockInStatusMsg.ACCEPTED.toString

    try {
      val newPerson: Person = new Person(rawNumData)
      server.clockManager.addNewClockOut(newPerson, DateTime.now())

    } catch {
      case e: NumberFormatException => {
        ackMsg = ClockInStatusMsg.BAD_REQUEST.toString
      }
      case e: IllegalArgumentException => {
        ackMsg = ClockInStatusMsg.BAD_REQUEST.toString
      }
      case e: NoSuchElementException => {
        ackMsg = ClockInStatusMsg.BAD_REQUEST.toString
      }
      case e: Exception => {
        e.printStackTrace()
        ackMsg = ClockInStatusMsg.INTERNAL_ERROR.toString
      }
    }

    if(ackSender.isAckRequested) ackSender.sendAckData(ackMsg)
  }
}

class NewClockInListener(server: AttendanceManagerServer) extends DataListener[String] {
  override def onData(client: SocketIOClient, rawNumData: String, ackSender: AckRequest): Unit = {
    var ackMsg: String = ClockInStatusMsg.ACCEPTED.toString

    try {
      val newPerson: Person = new Person(rawNumData)
      server.clockManager.addNewClockIn(newPerson, DateTime.now())

    } catch {
      case e: NumberFormatException => {
        ackMsg = ClockInStatusMsg.BAD_REQUEST.toString
      }
      case e: IllegalArgumentException => {
        ackMsg = ClockInStatusMsg.BAD_REQUEST.toString
      }
      case e: DuplicateKeyException => {
        ackMsg = ClockInStatusMsg.DUPLICATE.toString
      }
      case e: Exception => {
        e.printStackTrace()
        ackMsg = ClockInStatusMsg.INTERNAL_ERROR.toString
      }
    }

    if(ackSender.isAckRequested) ackSender.sendAckData(ackMsg)
  }
}

class ClockedInListListener(server: AttendanceManagerServer) extends InvalidationObserver[Map[Person, DateTime]] {
  override def receiveUpdate(newClockedIn: Map[Person, DateTime]): Unit = {
    val clockedInUpdateMsg = new ClockedInUpdateMsg()
    server.server.getBroadcastOperations.send(clockedInUpdateMsg.getMessage(newClockedIn))
  }
}
