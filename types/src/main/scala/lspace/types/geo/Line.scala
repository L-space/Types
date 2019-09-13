package lspace.types.geo

import lspace.types.geo.helper.Comparator

case class Line(vector: Vector[Point]) extends Geometry {
  def intersect(that: Geometry)(
      implicit helper: Comparator = Comparator.default): Boolean =
    helper.line.intersect(this, that)
  def disjoint(that: Geometry)(
      implicit helper: Comparator = Comparator.default): Boolean =
    helper.line.disjoint(this, that)
  def contains(that: Geometry)(
      implicit helper: Comparator = Comparator.default): Boolean =
    helper.line.contains(this, that)
  def within(that: Geometry)(
      implicit helper: Comparator = Comparator.default): Boolean =
    helper.line.within(this, that)

  lazy val bbox: BBox = BBox(vector.map(_.x).min,
                             vector.map(_.y).min,
                             vector.map(_.x).max,
                             vector.map(_.y).max)
}

object Line {
  def apply(points: Point*): Line = Line(points.toVector)
  implicit def toVector(points: Line): Vector[Vector[Double]] =
    points.vector.map(Point.toVector)
}
