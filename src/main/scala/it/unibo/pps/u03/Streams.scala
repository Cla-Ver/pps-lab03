package u03

import u03.Sequences.Sequence.*

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

    def fromList[A](s: Sequence[A]): Stream[A] = s match
      case Sequence.Cons(h, t) => cons(h, fromList(t))
      case _ => Empty()

    def interleave[A](stream1: Stream[A], stream2: Stream[A]): Stream[A] = (stream1, stream2) match
      case (Cons(h, t), s2) => cons(h(), interleave(s2, t()))
      case (Empty(), s2) => s2
      case _ => Empty()

    def cycle[A](lst: Sequence[A]): Stream[A] =
      def loop[A](fullList: Sequence[A], remainingList: Sequence[A]): Stream[A] = remainingList match
        case Sequence.Cons(h, t) => cons(h, loop(fullList, t))
        case _ => loop(fullList, fullList)

      loop(lst, lst)


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

  val s1 = Stream.fromList(Cons(1, Cons(3, Cons(5, Nil()))))
  val s2 = Stream.fromList(Cons(2, Cons(4, Cons(6, Cons(8, Cons(10, Nil()))))))
  val s3 = Stream.toList(Stream.interleave(s1, s2))
  val s4 = Stream.cycle(Cons(1, Cons(2, Nil())))
  //println(Stream.toList(Stream.take(s2)(4)))
  println(Stream.toList(Stream.take(s4)(5)))