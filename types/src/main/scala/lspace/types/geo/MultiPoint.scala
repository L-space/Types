package lspace.types.geo

import lspace.types.geo.ops.Comparator

case class MultiPoint(vector: Vector[Point]) extends Geometry {
  def intersect(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean =
    helper.multipoint.intersect(this, that)
  def disjoint(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean =
    helper.multipoint.disjoint(this, that)
  def contains(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean =
    helper.multipoint.contains(this, that)
  def within(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean =
    helper.multipoint.within(this, that)

  lazy val bbox: BBox = BBox(vector.map(_.x).min, vector.map(_.y).min, vector.map(_.x).max, vector.map(_.y).max)
}

object MultiPoint {
  def apply[T](points: T*)(implicit ev: T =:= Point): MultiPoint =
    MultiPoint(points.asInstanceOf[Seq[Point]].toVector)
  def apply(points: Point*): MultiPoint = MultiPoint(points.toVector)
  implicit def toVector(point: MultiPoint): Vector[Vector[Double]] =
    point.vector.map(Point.toVector)
}
