package org.ubseds.server.messages.receive

import org.ubseds.server.messages.ServerMessageLike

/**
 * @project AttendanceManager
 * @author t3pfaffe on 5/23/2020
 */
class ClockOffNumMsg extends ServerMessageLike() {
  override val name: String = ClockOffNumMsg.name
}

object ClockOffNumMsg {
  val name: String = "clockOff_ubnum"
}

