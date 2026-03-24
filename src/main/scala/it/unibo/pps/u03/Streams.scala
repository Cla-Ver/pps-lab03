package u03

import scala.annotation.tailrec

object Streams extends App :

  import Sequences.*

  enum Stream[A]:
    private case Empty()
    private case Cons(head: () => A, tail: () => Stream[A])

  object Stream:

    def empty[A](): Stream[A] = Empty()

    def cons[A](hd: => A, tl: => Stream[A]): Stream[A] =
      lazy val head = hd
      lazy val tail = tl
      Cons(() => head, () => tail)

    def toList[A](stream: Stream[A]): Sequence[A] = stream match
      case Cons(h, t) => Sequence.Cons(h(), toList(t()))
      case _ => Sequence.Nil()

    def map[A, B](stream: Stream[A])(f: A => B): Stream[B] = stream match
      case Cons(head, tail) => cons(f(head()), map(tail())(f))
      case _ => Empty()

    def filter[A](stream: Stream[A])(pred: A => Boolean): Stream[A] = stream match
      case Cons(head, tail) if (pred(head())) => cons(head(), filter(tail())(pred))
      case Cons(head, tail) => filter(tail())(pred)
      case _ => Empty()

    def take[A](stream: Stream[A])(n: Int): Stream[A] = (stream, n) match
      case (Cons(head, tail), n) if n > 0 => cons(head(), take(tail())(n - 1))
      case _ => Empty()

    def iterate[A](init: => A)(next: A => A): Stream[A] =
      cons(init, iterate(next(init))(next))

    def takeWhile[A](stream: Stream[A])(pred: A => Boolean): Sequence[A] = stream match
      case Cons(h, t) if pred(h()) => Sequence.Cons(h(), takeWhile(t())(pred))
      case _ => Sequence.Nil()

    def fill[A](n: Int)(element: A): Stream[A] = n match
      case 0 => Empty()
      case n => cons(element, fill(n-1)(element))

    /*def interleave[A](stream1: Stream[A])(stream2: Stream[A]): Stream[A] =
      def interl[A](stream1: Stream[A], stream2: Stream[A], current: Stream[A], firstTurn: Boolean): Stream[A] = (stream1, stream2) match
        case (Empty(), Empty()) => current
        case (Cons(h, t), s2) if firstTurn || s2 == Empty() => interl(stream1, stream2, cons(h(), take(stream1)(1)), !firstTurn)
        case (s1,  Cons(h, t)) => interl(stream1, stream2, cons(h(), take(stream2)(1)), !firstTurn)

      interl(stream1, stream2, Empty(), true)*/

  end Stream

@main def tryStreams =
  import Streams.* 

  val str1 = Stream.iterate(0)(_ + 1) // {0,1,2,3,..}
  val str2 = Stream.map(str1)(_ + 1) // {1,2,3,4,..}
  val str3 = Stream.filter(str2)(x => (x < 3 || x > 20)) // {1,2,21,22,..}
  val str4 = Stream.take(str3)(10) // {1,2,21,22,..,28}
  println(Stream.toList(str4)) // [1,2,21,22,..,28]

  lazy val corec: Stream[Int] = Stream.cons(1, corec) // {1,1,1,..}
  println(Stream.toList(Stream.take(corec)(10))) // [1,1,..,1]

  val fibonacci: Stream[Int] = Stream.map(Stream.iterate((0, 1))((e1, e2) => (e2, e1+e2)))((e1, e2) => e1)
  println(Stream.toList(Stream.take(fibonacci)(5))) // Cons (0 , Cons (1 , Cons (1 , Cons (2 , Cons (3 , Nil () ) ) ) ))

  /*val s1 = Stream.fromList(List(1, 3, 5))
  val s2 = Stream.fromList(List(2, 4, 6, 8, 10))
  Stream.toList(Stream.interleave(s1, s2))*/