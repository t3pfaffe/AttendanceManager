package org.ubseds.events

/**
 * @project AttendanceManager
 * @author t3pfaffe on 5/23/2020
 *         with help from stack-overflow of course
 */
trait Subject[S] {
  private var observers: List[InvalidationObserver[S]] = List()
  private var deltaObservers: List[ChangeObserver[S]] = List()


  def addObserver(observer: InvalidationObserver[S]): Unit = observers = observer :: observers

  def removeObserver(observer: InvalidationObserver[S]): Unit = observers = observers.filterNot(_ == observer)

  protected def notifyObservers(subject: S): Unit = observers.foreach(_.receiveUpdate(subject))


  def addObserver(observer: ChangeObserver[S]): Unit = deltaObservers = observer :: deltaObservers

  def removeObserver(observer: ChangeObserver[S]): Unit = deltaObservers = deltaObservers.filterNot(_ == observer)

  protected def notifyObservers(deltaSubject: S, positive: Boolean, wholeSubject: S): Unit = {
    observers.foreach(_.receiveUpdate(wholeSubject))
    deltaObservers.foreach(_.receiveDeltaUpdate(deltaSubject, positive, wholeSubject))
  }
}
