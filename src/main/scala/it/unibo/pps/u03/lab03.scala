/*package it.unibo.pps.u03
import scala.annotation.tailrec
import u03.Optionals.Optional
import u03.Streams.Stream.*
import u03.Streams.Stream
import u03.Sequences.Sequence
import u03.Sequences.Sequence.*
import Person.*

object lab03:
      // Task 1

      @tailrec
      def skip[A](s: Sequence[A])(n: Int): Sequence[A] = s match
        case s if n == 0 => s
        case Cons(h, t) => skip(t)(n - 1)
        case _ => Nil()

      def zip[A, B](first: Sequence[A], second: Sequence[B]): Sequence[(A, B)] = (first, second) match
        case (Cons(h1, t1), Cons(h2, t2)) => Cons((h1, h2), zip(t1, t2))
        case _ => Nil()

      def concat[A](s1: Sequence[A], s2: Sequence[A]): Sequence[A] = s1 match
        case Cons(h, t) => Cons(h, concat(t, s2))
        case _ => s2

      def reverse[A](s: Sequence[A]): Sequence[A] = s match
        case Cons(h, t) => concat(reverse(t), Cons(h, Nil()))
        case _ => Nil()

      def flatMap[A, B](s: Sequence[A])(mapper: A => Sequence[B]): Sequence[B] = s match
        case Cons(h, t) => concat(mapper(h), flatMap(t)(mapper))
        case _ => Nil()

      def min(s: Sequence[Int]): Optional[Int] =
        @tailrec
        def minimum(s: Sequence[Int], minimumSoFar: Optional[Int]): Optional[Int] = s match
          case Cons(h, t) if h < Optional.orElse(minimumSoFar, h + 1) => minimum(t, Optional.Just(h))
          case Cons(_, t) => minimum(t, minimumSoFar)
          case _ => minimumSoFar

        minimum(s, Optional.Empty())

      def evenIndices[A](s: Sequence[A]): Sequence[A] = s match
        case Cons(h, _) => Cons(h, skip(s)(2))
        case _ => Nil()

      @tailrec
      def contains[A](s: Sequence[A])(elem: A): Boolean = s match
        case Cons(h, t) if h == elem => true
        case Cons(_, t) => contains(t)(elem)
        case _ => false

      def distinct[A](s: Sequence[A]): Sequence[A] =
        @tailrec
        def dist[A](remainingSequence: Sequence[A])(distinctSequence: Sequence[A]): Sequence[A] = remainingSequence match
          case Cons(h, t) if contains(distinctSequence)(h) => dist(t)(distinctSequence)
          case Cons(h, t) => dist(t)(concat(distinctSequence, Cons(h, Nil())))
          case _ => distinctSequence

        dist(s)(Nil())

      def group[A](s: Sequence[A]): Sequence[Sequence[A]] =
        @tailrec
        def grouping[A](s: Sequence[A], sequences: Sequence[Sequence[A]], currentGroup: Sequence[A]): Sequence[Sequence[A]] = s match
          case Cons(h, t) if contains(currentGroup)(h) || currentGroup == Sequence.Nil() => grouping(t, sequences, concat(currentGroup, Sequence.Cons(h, Nil())))
          case Cons(h, t) => grouping(t, concat(sequences, Sequence.Cons(currentGroup, Nil())), Sequence.Cons(h, Nil()))
          case Nil() if currentGroup == Sequence.Nil() => sequences
          case _ => concat(sequences, Sequence.Cons(currentGroup, Nil()))

        grouping(s, Nil(), Nil())

      def partition[A](s: Sequence[A])(pred: A => Boolean): (Sequence[A], Sequence[A]) =
        @tailrec
        def part[A](s: Sequence[A], seq1: Sequence[A], seq2: Sequence[A], pred: A => Boolean): (Sequence[A], Sequence[A]) = s match
          case Cons(h, t) if pred(h) => part(t, concat(seq1, Cons(h, Nil())), seq2, pred)
          case Cons(h, t) => part(t, seq1, concat(seq2, Cons(h, Nil())), pred)
          case _ => (seq1, seq2)

        part(s, Nil(), Nil(), pred)

      // Task 2

      def teachersCourses(persons: Sequence[Person]): Sequence[String] = Sequence.map(Sequence.filter(persons)(person => !isStudent(person)))(course)

      @tailrec
      def foldLeft[A, B](sequence: Sequence[A])(initialValue: B)(operator: (B, A) => B): B = sequence match
        case Cons(h, t) => foldLeft(t)(operator(initialValue, h))(operator)
        case _ => initialValue

      def countDistinctCourses(persons: Sequence[Person]): Int = foldLeft(distinct(teachersCourses(persons)))(0)((e, _) => e + 1)

      // Task 3

      def takeWhile[A](stream: Stream[A])(pred: A => Boolean): Sequence[A] = stream match
        case Cons(h, t) if pred(h()) => Sequence.Cons(h(), takeWhile(t())(pred))
        case _ => Sequence.Nil()

      def fill[A](n: Int)(element: A): Stream[A] = n match
        case 0 => Empty()
        case n => cons(element, fill(n - 1)(element))

      def interleave[A](stream1: Stream[A], stream2: Stream[A]): Stream[A] = stream1 match
        case Cons(h, t) => cons(h(), interleave(stream2, t()))
        case _ => stream2

      def cycle[A](lst: Sequence[A]): Stream[A] =
        def loop[A](fullList: Sequence[A], remainingList: Sequence[A]): Stream[A] = remainingList match
          case Sequence.Cons(h, t) => cons(h, loop(fullList, t))
          case _ => loop(fullList, fullList)

        loop(lst, lst)
*/