package org.ubseds.server.messages.send

/**
 * @project AttendanceManager
 * @author t3pfaffe on 5/20/2020
 */
class MotdSendMsg(message: String) extends ServerSendMessage("motd") {

  override def getDefaultData: String = message
}