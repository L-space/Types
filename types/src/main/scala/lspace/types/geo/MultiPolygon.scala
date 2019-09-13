package lspace.types.geo

import lspace.types.geo.helper.Comparator

case class MultiPolygon(vector: Vector[Polygon]) extends Geometry {
  def intersect(that: Geometry)(
      implicit helper: Comparator = Comparator.default): Boolean =
    helper.multipolygon.intersect(this, that)
  def disjoint(that: Geometry)(
      implicit helper: Comparator = Comparator.default): Boolean =
    helper.multipolygon.disjoint(this, that)
  def contains(that: Geometry)(
      implicit helper: Comparator = Comparator.default): Boolean =
    helper.multipolygon.contains(this, that)
  def within(that: Geometry)(
      implicit helper: Comparator = Comparator.default): Boolean =
    helper.multipolygon.within(this, that)

  lazy val bbox: BBox = BBox(
    vector.flatMap(_.vector.flatMap(_.map(_.x))).min,
    vector.flatMap(_.vector.flatMap(_.map(_.y))).min,
    vector.flatMap(_.vector.flatMap(_.map(_.x))).max,
    vector.flatMap(_.vector.flatMap(_.map(_.y))).max
  )
}

object MultiPolygon {
  def apply(points: Polygon*): MultiPolygon = MultiPolygon(points.toVector)
  implicit def toVector(points: MultiLine): Vector[Vector[Vector[Double]]] =
    points.vector.map(Polygon.toVector)
}
