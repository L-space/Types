package lspace.types.geo

import lspace.types.geo.ops.Comparator

case class MultiLine(vector: Vector[Line]) extends Geometry {
  def intersect(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean =
    helper.multiline.intersect(this, that)
  def disjoint(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean =
    helper.multiline.disjoint(this, that)
  def contains(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean =
    helper.multiline.contains(this, that)
  def within(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean =
    helper.multiline.within(this, that)

  lazy val bbox: BBox = BBox(
    vector.flatMap(_.vector.map(_.x)).min,
    vector.flatMap(_.vector.map(_.y)).min,
    vector.flatMap(_.vector.map(_.x)).max,
    vector.flatMap(_.vector.map(_.y)).max
  )
}

object MultiLine {
  def apply(points: Line*): MultiLine = MultiLine(points.toVector)
  implicit def toVector(points: MultiLine): Vector[Vector[Vector[Double]]] =
    points.vector.map(Line.toVector)
}
