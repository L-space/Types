package lspace.types.geo

import lspace.types.geo.helper.Comparator

case class BBox(left: Double, bottom: Double, right: Double, top: Double)
    extends Geometry {
  lazy val center: Point = Point(
    if (left < right) left + width / 2 else right + width / 2,
    if (bottom < top) bottom + height / 2 else top + height / 2)
  lazy val width: Double = Math.abs(left - right)
  lazy val height: Double = Math.abs(bottom - top)

  def +(bbox: BBox): BBox =
    BBox(Vector(left, bbox.left).min,
         Vector(bottom, bbox.bottom).min,
         Vector(right, bbox.right).max,
         Vector(top, bbox.top).max)

  def intersect(that: Geometry)(
      implicit helper: Comparator = Comparator.default): Boolean =
    helper.bbox.intersect(this, that)
  def disjoint(that: Geometry)(
      implicit helper: Comparator = Comparator.default): Boolean =
    helper.bbox.disjoint(this, that)
  def contains(that: Geometry)(
      implicit helper: Comparator = Comparator.default): Boolean =
    helper.bbox.contains(this, that)
  def within(that: Geometry)(
      implicit helper: Comparator = Comparator.default): Boolean =
    helper.bbox.within(this, that)

  def bbox: BBox = this
}
