package org.ubseds.database.datatypes

import org.joda.time.{DateTime, DateTimeZone}
import org.ubseds.events.Subject
import play.api.libs.json.{JsValue, Json}

/**
 * @project AttendanceManager
 * @author t3pfaffe on 5/23/2020
 */
class ClockMap(private var _clocked: Map[Person, DateTime] = Map()) extends Subject[Map [Person, DateTime]] {

  /**
   * @return a Person mapped to a DateTime object representing when a clock was performed.
   */
  def clocked(): Map[Person, DateTime] = _clocked

  /**
   * adds a person mapped to a dateTime object to to clocked
   * @param person   who is registering this clock
   * @param dateTime when this clock is registered (defaults to null)
   */
  def addClock(person: Person, dateTime: DateTime = null): Unit = {
    val newMapEntry = person -> dateTime
    _clocked += newMapEntry

    val delta: Map[Person, DateTime] = Map(newMapEntry)
    super.notifyObservers(delta, true, this.clocked())
  }

  /**
   * removes all instances of the argument from clocked
   * @param person the argument to filter out
   */
  @throws(classOf[NoSuchElementException])
  def removeClock(person: Person): Unit = {
    if(clocked.contains(person)) { //avoid notifying when no change occurs
      _clocked -= person

      val delta: Map[Person, DateTime] = Map(person -> null)
      super.notifyObservers(delta, false, clocked())
    } else {
      throw new NoSuchElementException(person + " did not exist in the map to be removed!")
    }
  }

  /**
   * removes all instances containing both arguments from clocked
   * @param person   the argument to filter out
   * @param dateTime the argument to filter out
   */
  @throws(classOf[NoSuchElementException])
  def removeClock(person: Person, dateTime: DateTime): Unit = {
    val preLen = clocked().size
    _clocked   = clocked().filterNot(t => t._1 == person && t._2 == dateTime)

    if(preLen > clocked().size) { //avoid notifying when no change occurs
      val delta: Map[Person, DateTime] = Map(person -> dateTime)
      super.notifyObservers(delta, false, clocked())
    } else {
      throw new NoSuchElementException(person + " -> " + dateTime + " did not exist in the map to be removed!")
    }
  }

  /**
   *
   * @return a readonly iteration of this ClockMap that can only be read.
   */
  def getReadOnly(): ReadOnlyClockMap = new ReadOnlyClockMap(this)


  /**
   * @return string representation of the clocked map
   */
  override def toString(): String = clocked().toString()

  /**
   * @return JSON string representation of the clocked map
   */
  def toJson(): String = {
    val clockedJSON: List[JsValue] = clocked().map(t => tupleToJson(t) ).toList
    Json.stringify(Json.toJson(clockedJSON))
  }

  private def tupleToJson(element: Tuple2[Person, DateTime]): JsValue = {
    val elementMap: Map[String, JsValue] = Map(
      "username"  -> element._1.toJson(),
      "timestamp" -> dateTimeToJson(element._2)
    )
    Json.toJson(elementMap)
  }

  private def dateTimeToJson(dateTime: DateTime): JsValue = {
    val date = dateTime.toDateTime(DateTimeZone.UTC)
    val unixTimestamp: Long = date.getMillis()/1000;
    Json.toJson(unixTimestamp.toString)
  }
}

