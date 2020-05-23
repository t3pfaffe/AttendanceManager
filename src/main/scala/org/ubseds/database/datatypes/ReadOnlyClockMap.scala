package org.ubseds.database.datatypes

import org.joda.time.DateTime
import org.ubseds.events.{ChangeObserver, InvalidationObserver, Subject}

/**
 * @project AttendanceManager
 * @author t3pfaffe on 5/23/2020
 */
class ReadOnlyClockMap(clockMap: ClockMap) extends Subject[Map [Person, DateTime]] with ChangeObserver[Map [Person, DateTime]]{
  clockMap.addObserver(this)

  private var _clocked:  Map[Person, DateTime] = clockMap.clocked()

  /**
   * @return a Person mapped to a DateTime object representing when a clock was performed.
   */
  def clocked(): Map [Person, DateTime] = _clocked

  /**
   * Called when an object being listened to notifies that a change has occurred.
   *
   * @param deltaClockedIn a subject containing only what has changed from last update
   * @param positive     whether or not this was a addition or subtraction change
   * @param newClockedIn a subject containing the whole; not just the delta
   */
  override def receiveDeltaUpdate(deltaClockedIn: Map[Person, DateTime], positive: Boolean, newClockedIn: Map[Person, DateTime]): Unit = {
    _clocked = newClockedIn
    super.notifyObservers(deltaClockedIn, positive, newClockedIn)
  }

}
