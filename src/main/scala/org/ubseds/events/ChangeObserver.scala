package org.ubseds.events

/**
 * @project AttendanceManager
 * @author t3pfaffe on 5/23/2020
 *
 * This type of observer will be notified of changes via a delta
 *  and a representation of the the whole.
 * @tparam S type of the rawNumData being observed.
 */
trait ChangeObserver[S] {

  /**
   * Called when an object being listened to notifies that a change has occurred.
   * @param deltaSubject a subject containing only what has changed from last update
   * @param positive     whether or not this was a addition or subtraction change
   * @param wholeSubject a subject containing the whole; not just the delta
   */
  def receiveDeltaUpdate(deltaSubject: S, positive: Boolean = true, wholeSubject: S)
}