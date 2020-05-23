package org.ubseds.events

/**
 * @project AttendanceManager
 * @author t3pfaffe on 5/23/2020
 *
 * This type of observer will only be notified of changes via the whole.
 *  It will not know what has changed.
 * @tparam S type of the rawNumData being observed.
 */
trait InvalidationObserver[S] {

  /**
   * Called when an object being listened to notifies that a change has occurred.
   * @param subject a subject containing the whole representation of the rawNumData.
   */
  def receiveUpdate(subject: S)
}