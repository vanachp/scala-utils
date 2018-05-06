package scalautils.concurrent

import scala.collection.TraversableOnce
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.Map
import scala.concurrent.{ExecutionContext, Future}

object FReduce {
  def apply[T, M[T] <: TraversableOnce[T]](futures: TraversableOnce[Future[M[T]]])(zero: M[T])
                                          (implicit executor: ExecutionContext, cbf: CanBuildFrom[M[T], T, M[T]]): Future[M[T]] = {
    Future.fold(futures)(zero) { (a, b) => (cbf() ++= a ++= b).result() }
  }

  def applyMap[T, R](futures: Iterable[Future[Map[T, R]]])(zero: Map[T, R])
                    (implicit executor: ExecutionContext): Future[Map[T, R]] = {
    Future.fold(futures)(zero) { (a, b) => a ++= b }
  }
}