package org.ubseds.server.messages.receive

import org.ubseds.server.messages.ServerMessageLike

/**
 * @project AttendanceManager
 * @author t3pfaffe on 5/20/2020
 */
class NumClockMsg extends ServerMessageLike() {
  override val name: String = NumClockMsg.name

}

object NumClockMsg {
  val name: String = "clock_ubnum"
}


