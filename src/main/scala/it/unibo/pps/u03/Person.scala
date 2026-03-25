package it.unibo.pps.u03

import u03.Sequences.Sequence
import u03.Sequences.Sequence.*

import scala.annotation.tailrec

enum Person:
  case Student(name: String, year: Int)
  case Teacher(name: String, course: String)

object Person:
  def name(p: Person): String = p match
    case Student(n, _) => n
    case Teacher(n, _) => n

  def isStudent(p: Person): Boolean = p match
    case Student(_, _) => true
    case _ => false

  def course(p: Person): String = p match
    case Teacher(_, course) => course

  def teachersCourses(persons: Sequence[Person]): Sequence[String] = map(filter(persons)(person => !isStudent(person)))(course)

  @tailrec
  def foldLeft[A, B](sequence: Sequence[A])(initialValue: B)(operator: (B, A) => B): B = sequence match
    case Cons(h, t) => foldLeft(t)(operator(initialValue, h))(operator)
    case _ => initialValue

  def countDistinctCourses(persons: Sequence[Person]): Int = foldLeft(distinct(teachersCourses(persons)))(0)((e, _) => e + 1)

