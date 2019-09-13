package lspace.types.geo

object Geometry {
//  implicit def toBBox(geometry: Geometry): BBox = geometry.bbox
}
trait Geometry extends Product with Serializable {
  def intersect(that: Geometry): Boolean
  def ^(that: Geometry): Boolean = intersect(that)
  def disjoint(that: Geometry): Boolean
  def !^(that: Geometry): Boolean = intersect(that)
  def contains(that: Geometry): Boolean
  def <>(that: Geometry): Boolean = contains(that)
  def within(that: Geometry): Boolean
  def ><(that: Geometry): Boolean = within(that)

  def bbox: BBox
}
