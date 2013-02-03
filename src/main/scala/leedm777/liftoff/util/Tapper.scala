package leedm777.liftoff.util

object Tapper {

  implicit class Tapped[T](val obj: T) extends AnyVal {
    def tap(fn: T => Unit): T = {
      fn(obj)
      obj
    }
  }

}
