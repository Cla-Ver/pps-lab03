package it.unibo.pps.u03

import org.junit.Assert.assertEquals
import org.junit.Test
import u03.Sequences.Sequence
import u03.Sequences.Sequence.*

class StreamTest:
  import u03.Streams.*
  //val intSequence: Sequence[Int] = Cons(0, Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil()))))))
  val intStream: Stream[Int] = Stream.iterate(0)(_ + 1)
  
  @Test def takeWhileShouldReturnValuesThatSatisfyPredicate(): Unit =
    assertEquals(Cons(0, Cons(1, Cons(2, Nil()))), Stream.takeWhile(intStream)(_ < 3))

  @Test def fillShouldFillAStreamOfSameElements(): Unit =
    assertEquals(Cons("a", Cons("a", Cons("a", Nil()))), Stream.toList(Stream.fill(3)("a")))