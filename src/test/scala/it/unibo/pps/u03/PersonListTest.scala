package it.unibo.pps.u03

import org.junit.Assert.assertEquals
import org.junit.Test
import u03.Sequences.Sequence
import u03.Sequences.Sequence.*

class PersonListTest:
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
