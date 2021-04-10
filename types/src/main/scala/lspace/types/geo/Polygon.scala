package lspace.types.geo

import lspace.types.geo.ops.Comparator

case class Polygon(vector: Vector[Vector[Point]]) extends Geometry {
  def apply[T](points: T*)(implicit ev: T =:= Point): Polygon = Polygon.apply(points: _*)
  def apply[T: Numeric](points: (T, T)*): Polygon             = Polygon.apply(points: _*)

  def intersect(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean =
    helper.polygon.intersect(this, that)
  def disjoint(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean =
    helper.polygon.disjoint(this, that)
  def contains(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean =
    helper.polygon.contains(this, that)
  def within(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean =
    helper.polygon.within(this, that)

  lazy val bbox: BBox = BBox(vector.flatMap(_.map(_.x)).min,
                             vector.flatMap(_.map(_.y)).min,
                             vector.flatMap(_.map(_.x)).max,
                             vector.flatMap(_.map(_.y)).max)
}

object Polygon {
  def apply[T](points: T*)(implicit ev: T =:= Point): Polygon =
    Polygon(points.asInstanceOf[Seq[Point]].toVector)
  def apply[T](points: (T, T)*)(implicit n: Numeric[T]): Polygon =
    Polygon(
      points
        .map(t => n.toDouble(t._1) -> n.toDouble(t._2))
        .map(Point.toPoint)
        .toVector)

  def apply[T](vector: Vector[T])(implicit ev: T =:= Point): Polygon =
    Polygon(Vector(vector).asInstanceOf[Vector[Vector[Point]]])
  implicit def toVector(points: Line): Vector[Vector[Double]] =
    points.vector.map(Point.toVector)
}
