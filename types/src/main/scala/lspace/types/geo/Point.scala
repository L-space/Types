package lspace.types.geo

import lspace.types.geo.ops.Comparator

case class Point(x: Double, y: Double) extends Geometry {
  def intersect(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean =
    helper.point.intersect(this, that)
  def disjoint(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean  =
    helper.point.disjoint(this, that)
  def contains(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean  =
    helper.point.contains(this, that)
  def within(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean    =
    helper.point.within(this, that)

  lazy val bbox: BBox = BBox(x, y, x, y)
}

object Point {
  implicit def toPoint(xy: (Double, Double)): Point   = Point(xy._1, xy._2)
  implicit def toVector(point: Point): Vector[Double] = Vector(point.x, point.y)
}
