package org.ubseds.server.messages.send

import com.corundumstudio.socketio.protocol.Packet
import org.joda.time.DateTime
import org.ubseds.database.datatypes.{ClockMap, Person}

/**
 * @project AttendanceManager
 * @author t3pfaffe on 5/23/2020
 */
class ClockedInUpdateMsg extends ServerSendMessage("clockedIn_update") {
  override def getDefaultData: String = ""

  def getMessage(clockedInMap: Map [Person, DateTime]): Packet = {
    val msg: String = new ClockMap(clockedInMap).toJson()
    getMessage(msg)
  }

}
