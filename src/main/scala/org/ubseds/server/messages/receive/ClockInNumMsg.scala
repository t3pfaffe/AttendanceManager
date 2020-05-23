package org.ubseds.server.messages.receive

import org.ubseds.server.messages.ServerMessageLike

/**
 * @project AttendanceManager
 * @author t3pfaffe on 5/20/2020
 */
class ClockInNumMsg extends ServerMessageLike() {
  override val name: String = ClockInNumMsg.name

}

object ClockInNumMsg {
  val name: String = "clockIn_ubnum"
}


