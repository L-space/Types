package lspace.types.geo

import lspace.types.geo.ops.Comparator

object Geometry {
  //  implicit def toBBox(geometry: Geometry): BBox = geometry.bbox
}
trait Geometry extends Product with Serializable {
  def intersect(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean
  def ^(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean =
    intersect(that)(helper)
  def disjoint(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean
  def !^(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean =
    intersect(that)(helper)
  def contains(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean
  def <>(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean =
    contains(that)(helper)
  def within(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean
  def ><(that: Geometry)(implicit helper: Comparator = Comparator.default): Boolean =
    within(that)(helper)

  def bbox: BBox
}
