package it.unibo.pps.u03

import org.junit.Assert.assertEquals
import org.junit.Test
import u03.Sequences.Sequence
import u03.Sequences.Sequence.*
import u03.Streams.Stream.{cycle, fibonacci, interleave}

class lab03PersonTests:
  // Person tests
  import Person.*

  val sequence: Sequence[Person] = Cons(Student("Mario", 2000), Cons(Teacher("Mirko", "PPS"), Cons(Teacher("Aguzzi", "PPS"), Cons(Teacher("Ricci", "PCD"), Nil()))))
  val intSequence: Sequence[Int] = Cons(3, Cons(7, Cons(1, Cons(5, Nil()))))

  @Test def teacherCoursesShouldReturnCourses(): Unit =
    assertEquals(Cons("PPS", Cons("PPS", Cons("PCD", Nil()))), teachersCourses(sequence))

  @Test def foldLeftShouldAccumulateValues(): Unit =
    assertEquals(-16, foldLeft(intSequence)(0)(_ - _))

  @Test def foldLeftShouldConcatenateNumbers(): Unit =
    assertEquals("3715", foldLeft(intSequence)("")(_ + _))

  @Test def uniqueCoursesShouldCountUniqueCourses(): Unit =
    assertEquals(2, countDistinctCourses(sequence))

  @Test def uniqueCoursesShouldWorkOnEmptySequences(): Unit =
    assertEquals(0, countDistinctCourses(Nil()))

  //Stream tests

class lab03StreamTests:

  import u03.Streams.*
  val intStream: Stream[Int] = Stream.iterate(0)(_ + 1)
  
  @Test def takeWhileShouldReturnValuesThatSatisfyPredicate(): Unit =
    assertEquals(Cons(0, Cons(1, Cons(2, Nil()))), Stream.takeWhile(intStream)(_ < 3))

  @Test def fillShouldFillAStreamOfSameElements(): Unit =
    assertEquals(Cons("a", Cons("a", Cons("a", Nil()))), Stream.toList(Stream.fill(3)("a")))

  @Test def fibonacciTest(): Unit =
    assertEquals(Cons(0, Cons(1, Cons(1, Cons(2, Cons(3, Nil()))))), Stream.toList(Stream.take(fibonacci)(5)))

  @Test def interleaveShouldMergeTwoStreamsWithInterleavingElements(): Unit =
    val s1 = Stream.fromList(Cons(1, Cons(3, Cons(5, Nil()))))
    val s2 = Stream.fromList(Cons(2, Cons(4, Cons(6, Cons(8, Cons(10, Nil()))))))
    assertEquals(Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Cons(6, Cons(8, Cons(10, Nil())))))))), Stream.toList(interleave(s1, s2)))

  @Test def cycleShouldInfinitelyLoopList(): Unit =
    assertEquals(Cons("a", Cons("b", Cons("a", Cons("b", Cons("a", Nil()))))), Stream.toList(Stream.take(cycle(Cons("a", Cons("b", Nil()))))(5)))