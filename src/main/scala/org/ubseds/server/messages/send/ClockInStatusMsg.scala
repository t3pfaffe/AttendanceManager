package org.ubseds.server.messages.send

import java.net.HttpURLConnection

import com.corundumstudio.socketio.protocol.Packet

/**
 * @project AttendanceManager
 * @author t3pfaffe on 5/22/2020
 */
class ClockInStatusMsg extends ServerSendMessage("clock_status") {
  override def getDefaultData: String = HttpURLConnection.HTTP_INTERNAL_ERROR.toString

  def getMessage(httpStatus: Int): Packet = getMessage(httpStatus.toString)

  def getMessage(status: Boolean): Packet = {
    if (status) getMessage(ClockInStatusMsg.ACCEPTED.toString)
    else getMessage(ClockInStatusMsg.BAD_REQUEST.toString)
  }

}

object ClockInStatusMsg {
  val BAD_REQUEST: Int    = HttpURLConnection.HTTP_BAD_REQUEST
  val INTERNAL_ERROR: Int = HttpURLConnection.HTTP_INTERNAL_ERROR
  val ACCEPTED:  Int      = HttpURLConnection.HTTP_ACCEPTED
  val DUPLICATE: Int      = HttpURLConnection.HTTP_CONFLICT
}
