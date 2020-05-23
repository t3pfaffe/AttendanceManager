package org.ubseds.database

import org.joda.time.DateTime
import org.ubseds.database.datatypes.{ClockMap, Person, ReadOnlyClockMap}

/**
 * @project AttendanceManager
 * @author t3pfaffe on 5/23/2020
 */
class ClockManager() {

  private val _clockedIn:  ClockMap = new ClockMap
  private val _clockedOut: ClockMap = new ClockMap

  /**
   * @return Current Persons clocked-in mapped to the time they clocked-in on.
   */
  val clockedIn: ReadOnlyClockMap  = _clockedIn.getReadOnly()

  /**
   * @return Cache of Persons who have clocked-out mapped to the time they clocked-in on.
   */
  val clockedOut: ReadOnlyClockMap = _clockedOut.getReadOnly()

  /**
   * Adds a person to clockedIn with a specified timestamp.
   * @param person   who is registering this clock.
   * @param dateTime when this clock is registered (defaults to null).
   */
  @throws(classOf[DuplicateKeyException])
  def addNewClockIn(person: Person, dateTime: DateTime = DateTime.now()):  Unit  = {
    if(_clockedIn.clocked().contains(person)) throw new DuplicateKeyException("key of [" + person.toString + "] already exists in this Map" + _clockedIn.clocked().toString())
    else _clockedIn.addClock(person, dateTime)
  }

  /**
   * Removes a person from clockedIn.
   * Adds a person to clockedOut with a specified timestamp.
   * @param person   who is registering this clock.
   * @param dateTime when this clock is registered (defaults to null).
   */
  @throws(classOf[NoSuchElementException])
  def addNewClockOut(person: Person, dateTime: DateTime = DateTime.now()): Unit = {
    _clockedIn.removeClock(person)
    _clockedOut.addClock(person, dateTime)

  }

}


class DuplicateKeyException(e: String) extends Exception(e) {}