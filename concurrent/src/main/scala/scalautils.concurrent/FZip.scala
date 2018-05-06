package scalautils.concurrent

import scala.concurrent.{ExecutionContext, Future}

object FZip{
  def apply[A,B](f1: => Future[A], f2: => Future[B])(implicit ec:ExecutionContext): Future[(A,B)] = f1 zip f2
  def apply[A,B,C](f1: => Future[A], f2: => Future[B], f3:  Future[C])(implicit ec:ExecutionContext): Future[(A,B,C)] = {
    (f1 zip f2 zip f3).map{ case ((a,b),c) => (a,b,c)}
  }
  def apply[A,B,C,D](f1: => Future[A], f2: => Future[B], f3: => Future[C], f4: => Future[D])(implicit ec:ExecutionContext): Future[(A,B,C,D)] = {
    (f1 zip f2 zip f3 zip f4).map{ case (((a,b),c),d) => (a,b,c,d)}
  }
  def apply[A,B,C,D,E](f1: => Future[A], f2: => Future[B], f3: => Future[C], f4: => Future[D],
                       f5: => Future[E])(implicit ec:ExecutionContext): Future[(A,B,C,D,E)] = {
    (f1 zip f2 zip f3 zip f4 zip f5).map{ case ((((a,b),c),d),e) => (a,b,c,d,e)}
  }
  def apply[A,B,C,D,E,F](f1: => Future[A], f2: => Future[B], f3: => Future[C], f4: => Future[D], f5: => Future[E],
                         f6: => Future[F])(implicit ec:ExecutionContext): Future[(A,B,C,D,E,F)] = {
    (f1 zip f2 zip f3 zip f4 zip f5 zip f6).map{ case (((((a,b),c),d),e),f) => (a,b,c,d,e,f)}
  }
  def apply[A,B,C,D,E,F,G](f1: => Future[A], f2: => Future[B], f3: => Future[C], f4: => Future[D], f5: => Future[E],
                           f6: => Future[F], f7: => Future[G])(implicit ec:ExecutionContext): Future[(A,B,C,D,E,F,G)] = {
    (f1 zip f2 zip f3 zip f4 zip f5 zip f6 zip f7).map{ case ((((((a,b),c),d),e),f),g) => (a,b,c,d,e,f,g)}
  }
  def apply[A,B,C,D,E,F,G,H](f1: => Future[A], f2: => Future[B], f3: => Future[C], f4: => Future[D], f5: => Future[E],
                             f6: => Future[F], f7: => Future[G], f8: => Future[H])(implicit ec:ExecutionContext): Future[(A,B,C,D,E,F,G,H)] = {
    (f1 zip f2 zip f3 zip f4 zip f5 zip f6 zip f7 zip f8).map{ case (((((((a,b),c),d),e),f),g),h) => (a,b,c,d,e,f,g,h)}
  }
}
