package org.ubseds.database.datatypes

import play.api.libs.json.{JsValue, Json}

/**
 * @project AttendanceManager
 * @author t3pfaffe on 5/23/2020
 */
@throws(classOf[IllegalArgumentException])
class Person(val personNumber: Int) {

  @throws(classOf[NumberFormatException])
  def this(personNumber: String) {
    this(personNumber.toInt)
  }

  if (personNumber.toString.length != 8) throw new IllegalArgumentException("person number must be 8 digits")

  val abbreviatedPersonNumber: Int = personNumber.toString.substring(4,8).toInt

  override def equals(obj: Any): Boolean = {
    obj match {
      case p: Person => p.personNumber == this.personNumber
      case _ => super.equals(obj)
    }
  }

  def toJson(): JsValue = {
    Json.toJson("****-"+ this.abbreviatedPersonNumber)
  }
}
