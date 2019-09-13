package lspace.types.geo

import lspace.types.geo.helper.Comparator

case class MultiGeometry(vector: Vector[Geometry]) extends Geometry {
  def intersect(that: Geometry)(
      implicit helper: Comparator = Comparator.default): Boolean =
    helper.multigeometry.intersect(this, that)
  def disjoint(that: Geometry)(
      implicit helper: Comparator = Comparator.default): Boolean =
    helper.multigeometry.disjoint(this, that)
  def contains(that: Geometry)(
      implicit helper: Comparator = Comparator.default): Boolean =
    helper.multigeometry.contains(this, that)
  def within(that: Geometry)(
      implicit helper: Comparator = Comparator.default): Boolean =
    helper.multigeometry.within(this, that)

  lazy val bbox: BBox = vector.map(_.bbox).reduce(_ + _)
}

object MultiGeometry {
  def apply(points: Geometry*): MultiGeometry = MultiGeometry(points.toVector)
}
