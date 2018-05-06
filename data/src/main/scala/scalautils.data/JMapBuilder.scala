package scalautils.data

import scala.collection.JavaConverters._
import scala.collection.TraversableLike
import scala.collection.generic.CanBuildFrom
import scala.language.higherKinds

object JMapBuilder {

  def empty[A, B](initialCapacity: Option[Int] = None, loadFactor: Option[Float] = None): scala.collection.mutable.Map[A, B] = {
    val jMap = (initialCapacity, loadFactor) match {
      case (Some(ini), Some(factor)) => new java.util.HashMap[A, B](ini, factor)
      case (Some(ini), None) => new java.util.HashMap[A, B](ini)
      case _ => new java.util.HashMap[A, B]()
    }
    jMap.asScala
  }

  implicit class CollectionHelper[A, B[A] <: TraversableLike[A, B[A]]](values: B[A]){
    def jGroupBy[C](f: A => C)(implicit cbf: CanBuildFrom[B[A], A, B[A]]): scala.collection.mutable.Map[C, B[A]] = {
      val builderMapping = empty[C, scala.collection.mutable.Builder[A, B[A]]]()
      for(elem <- values){
        val key = f(elem)
        val builder = builderMapping.getOrElseUpdate(key, cbf.apply())
        builder.+=(elem)
      }
      val result = empty[C, B[A]]()
      builderMapping.foreach( kv => result.put(kv._1, kv._2.result()))
      result
    }

    def toJMap[C, D](initialCapacity: Option[Int] = None, loadFactor: Option[Float] = None)
                    (implicit ev: A <:< (C, D)): scala.collection.mutable.Map[C, D] = {
      val resultMp = empty[C, D](initialCapacity, loadFactor)
      values.foreach(kv => resultMp.put(kv._1, kv._2))
      resultMp
    }
  }

  implicit class MapHelper[A, B](values: scala.collection.Map[A, B]){
    type M[A, B] = scala.collection.mutable.Map[A, B]
    type AB[A, B] = (A, B)

    def toJMap(initialCapacity: Option[Int] = None, loadFactor: Option[Float] = None): M[A, B] = {
      val result = empty[A, B]()
      values.foreach( kv => result.put(kv._1, kv._2))
      result
    }

    def jMapValues[C](f: B => C, initialCapacity: Option[Int] = None, loadFactor: Option[Float] = None): M[A, C] ={
      val result = empty[A, C]()
      values.foreach( kv => result.put(kv._1, f(kv._2)))
      result
    }

    def jGroupBy[C](f: AB[A, B] => C)(implicit cbf: CanBuildFrom[M[A, B], AB[A, B], M[A, B]]): M[C, M[A, B]] = {
      val builderMapping = empty[C, scala.collection.mutable.Builder[AB[A, B], M[A, B]]]()
      for(elem <- values){
        val key = f(elem)
        val builder = builderMapping.getOrElseUpdate(key, cbf.apply())
        builder.+=(elem)
      }
      val result = empty[C, M[A, B]]()
      builderMapping.foreach( kv => result.put(kv._1, kv._2.result()))
      result
    }

    def intersect(anotherMap: scala.collection.Map[A, B]): M[A, B] = {
      val result = empty[A, B]()
      for(elem <- values){
        if(anotherMap.contains(elem._1)){
          result.put(elem._1, elem._2)
        }
      }
      result
    }

    def jFilter(f: AB[A, B] => Boolean): M[A, B] = {
      val result = empty[A, B]()
      values.foreach( kv => if(f(kv)) result.put(kv._1, kv._2))
      result
    }
  }
}

