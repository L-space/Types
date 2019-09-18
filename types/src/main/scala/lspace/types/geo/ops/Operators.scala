package lspace.types.geo.ops

import lspace.types.geo.Geometry

trait Operators[T <: Geometry] {
  def intersect(self: T, that: Geometry): Boolean
  def disjoint(self: T, that: Geometry): Boolean
  def contains(self: T, that: Geometry): Boolean
  def within(self: T, that: Geometry): Boolean
}
