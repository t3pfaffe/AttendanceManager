package org.ubseds.server.messages.send

import java.util

import com.corundumstudio.socketio.protocol.{Packet, PacketType}
import org.ubseds.server.messages.ServerMessageLike

/**
 * @project AttendanceManager
 * @author t3pfaffe on 5/20/2020
 */
abstract class ServerSendMessage(val name: String) extends ServerMessageLike {

  def getMessage(data: String = getDefaultData): Packet = {
    val packetToSend: Packet = new Packet(PacketType.MESSAGE)
    packetToSend.setSubType(PacketType.EVENT)
    packetToSend.setName(name)
    packetToSend.setData(util.Arrays.asList(data))
    packetToSend
  }

  def getDefaultData: String
}

object ServerSendMessage {
  val name: String = ServerSendMessage.name
}
