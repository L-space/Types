package lspace.types.geo.ops

import lspace.types.geo._

object Comparator {
  object default extends Comparator {
    object point extends Operators[Point] {
      def intersect(self: Point, that: Geometry): Boolean = that match {
        case that: Point         => that == self
        case that: MultiPoint    => that.vector.contains(self)
        case that: Line          => that.contains(self)
        case that: MultiLine     => that.vector.exists(_.contains(self))
        case that: Polygon       => that.contains(self)
        case that: MultiPolygon  => that.vector.exists(_.contains(self))
        case that: MultiGeometry => that.intersect(self)
      }
      def disjoint(self: Point, that: Geometry): Boolean = !that.contains(self)
      def contains(self: Point, that: Geometry): Boolean = self == that
      def within(self: Point, that: Geometry): Boolean = that match {
        case that: Point         => that == self
        case that: MultiPoint    => that.vector.contains(self)
        case that: Line          => that.contains(self)
        case that: MultiLine     => that.vector.exists(_.contains(self))
        case that: Polygon       => that.contains(self)
        case that: MultiPolygon  => that.vector.exists(_.contains(self))
        case that: MultiGeometry => that.contains(self)
      }
    }
    object multipoint extends Operators[MultiPoint] {
      def intersect(self: MultiPoint, that: Geometry): Boolean = that match {
        case that: Point => self.vector.contains(that)
        case that: MultiPoint =>
          self.vector.exists(p => that.vector.contains(p))
        case that: Line          => that.contains(self)
        case that: MultiLine     => that.vector.exists(_.contains(self))
        case that: Polygon       => that.contains(self)
        case that: MultiPolygon  => that.vector.exists(_.contains(self))
        case that: MultiGeometry => that.intersect(self)
      }
      def disjoint(self: MultiPoint, that: Geometry): Boolean =
        !self.vector.exists(that.contains)
      def contains(self: MultiPoint, that: Geometry): Boolean =
        self.vector.contains(that)
      def within(self: MultiPoint, that: Geometry): Boolean = that match {
        case that: Point => self.vector.forall(_ == that)
        case that: MultiPoint =>
          self.vector.forall(p => that.vector.contains(p))
        case that: Line => self.vector.forall(p => that.vector.contains(p))
        case that: MultiLine =>
          self.vector.forall(p => that.vector.exists(_.vector.contains(p)))
        case that: Polygon => self.vector.forall(p => that.contains(p))
        case that: MultiPolygon =>
          self.vector.forall(p => that.vector.exists(_.contains(p)))
        case that: MultiGeometry => that.contains(self)
      }
    }
    object line extends Operators[Line] {
      def intersect(self: Line, that: Geometry): Boolean = that match {
        case that: Point => self.vector.contains(that)
        case that: MultiPoint =>
          that.vector.exists(p => self.vector.contains(p))
        case that: Line          => self.bbox.intersect(that.bbox)
        case that: MultiLine     => that.vector.exists(_.bbox.intersect(self.bbox))
        case that: Polygon       => that.contains(self)
        case that: MultiPolygon  => that.vector.exists(_.contains(self))
        case that: MultiGeometry => that.intersect(self)
      }
      def disjoint(self: Line, that: Geometry): Boolean =
        !that.contains(self)
      def contains(self: Line, that: Geometry): Boolean =
        that match {
          case that: Point      => self.vector.contains(that)
          case that: MultiPoint => that.vector.forall(self.vector.contains)
          case that: Line       => self.vector.containsSlice(that.vector)
          case that: MultiLine =>
            that.vector.map(_.vector).forall(self.vector.containsSlice)
          case _ => false
        }
      def within(self: Line, that: Geometry): Boolean = that match {
        case _: Point            => false
        case that: MultiPoint    => that.vector.contains(self)
        case that: Line          => that.vector.contains(self)
        case that: MultiLine     => that.vector.exists(_.vector.contains(self))
        case that: Polygon       => that.contains(self)
        case that: MultiPolygon  => that.vector.exists(_.contains(self))
        case that: MultiGeometry => that.contains(self)
      }
    }
    object multiline extends Operators[MultiLine] {
      def intersect(self: MultiLine, that: Geometry): Boolean = that match {
        case that: Point => self.vector.exists(_.vector.contains(that))
        case that: MultiPoint =>
          self.vector.exists(p => that.vector.contains(p))
        case that: Line => self.vector.exists(_.bbox.intersect(that.bbox))
        case that: MultiLine =>
          that.vector.exists(line => self.vector.exists(_.bbox.intersect(line.bbox)))
        case that: Polygon =>
          self.vector.exists(line => that.bbox.intersect(line.bbox))
        case that: MultiPolygon =>
          self.vector.exists(line => that.vector.exists(_.bbox.intersect(line.bbox)))
        case that: MultiGeometry => that.intersect(self)
      }
      def disjoint(self: MultiLine, that: Geometry): Boolean =
        !self.vector.exists(that.contains)
      def contains(self: MultiLine, that: Geometry): Boolean =
        that match {
          case that: Point => self.vector.exists(_.bbox.contains(that.bbox))
          case that: MultiPoint =>
            that.vector.forall(point => self.vector.exists(_.bbox.contains(point.bbox)))
          case that: Line =>
            self.vector.exists(_.vector.containsSlice(that.vector))
          case that: MultiLine =>
            that.vector.forall(line => self.vector.exists(_.bbox.intersect(line.bbox)))
          case _ => false
        }
      def within(self: MultiLine, that: Geometry): Boolean = that match {
        case _: Point      => false
        case _: MultiPoint => false
        case that: Line    => self.vector.forall(p => that.containsSlice(p.vector))
        case that: MultiLine =>
          self.vector.forall(p => that.vector.exists(_.vector.containsSlice(p.vector)))
        case that: Polygon => self.vector.forall(p => that.contains(p))
        case that: MultiPolygon =>
          self.vector.forall(p => that.vector.exists(_.contains(p)))
        case that: MultiGeometry => that.contains(self)
      }
    }
    object polygon extends Operators[Polygon] {
      def intersect(self: Polygon, that: Geometry): Boolean = that match {
        case that: Point      => self.bbox.intersect(that.bbox)
        case that: MultiPoint => self.bbox.intersect(that.bbox)
        case that: Line       => self.bbox.intersect(that.bbox)
        case that: MultiLine  => self.bbox.intersect(that.bbox)
        case that: Polygon    => self.bbox.intersect(that.bbox)
        case that: MultiPolygon =>
          that.vector.exists(_.bbox.intersect(that.bbox))
        case that: MultiGeometry => that.intersect(self)
      }
      def disjoint(self: Polygon, that: Geometry): Boolean =
        !self.bbox.intersect(that.bbox)
      def contains(self: Polygon, that: Geometry): Boolean =
        self.bbox.contains(that.bbox)
      def within(self: Polygon, that: Geometry): Boolean = that match {
        case _: Point            => false
        case _: MultiPoint       => false
        case _: Line             => false
        case _: MultiLine        => false
        case that: Polygon       => that.contains(self)
        case that: MultiPolygon  => that.vector.exists(_.contains(self))
        case that: MultiGeometry => that.contains(self)
      }
    }
    object multipolygon extends Operators[MultiPolygon] {
      def intersect(self: MultiPolygon, that: Geometry): Boolean =
        self.vector.exists(p => p.intersect(that))
      def disjoint(self: MultiPolygon, that: Geometry): Boolean =
        self.vector.forall(p => p.disjoint(that))
      def contains(self: MultiPolygon, that: Geometry): Boolean =
        self.vector.forall(p => p.contains(that))
      def within(self: MultiPolygon, that: Geometry): Boolean =
        self.vector.forall(p => p.within(that))
    }
    object multigeometry extends Operators[MultiGeometry] {
      def intersect(self: MultiGeometry, that: Geometry): Boolean =
        self.vector.exists(geo => geo.intersect(that))
      def disjoint(self: MultiGeometry, that: Geometry): Boolean =
        self.vector.forall(geo => geo.disjoint(that))
      def contains(self: MultiGeometry, that: Geometry): Boolean =
        self.vector.forall(geo => geo.contains(that))
      def within(self: MultiGeometry, that: Geometry): Boolean =
        self.vector.forall(geo => geo.within(that))
    }
    object bbox extends Operators[BBox] {
      def intersect(self: BBox, that: Geometry): Boolean =
        that match {
          case bbox: BBox =>
            (Math.abs(bbox.center.x - self.center.x) * 2 < (bbox.width + self.width)) &&
              (Math.abs(bbox.center.y - self.center.y) * 2 < (bbox.height + self.height))
          case _ =>
            that.bbox.intersect(self) //TODO: FIX.. this is by far not precise
        }
      def disjoint(self: BBox, that: Geometry): Boolean =
        !intersect(self, that)
      def contains(self: BBox, that: Geometry): Boolean =
        self.left <= that.bbox.left && self.bottom <= that.bbox.bottom && self.right >= that.bbox.right && self.top >= that.bbox.top
      def within(self: BBox, that: Geometry): Boolean =
        that match {
          case bbox: BBox =>
            self.left >= bbox.left && self.bottom >= bbox.bottom && self.right <= bbox.right && self.top <= bbox.top
          case _ => false //TODO
        }
    }
  }
}

trait Comparator extends lspace.types.ops.Comparator {
  def point: Operators[Point]
  def multipoint: Operators[MultiPoint]
  def line: Operators[Line]
  def multiline: Operators[MultiLine]
  def polygon: Operators[Polygon]
  def multipolygon: Operators[MultiPolygon]
  def multigeometry: Operators[MultiGeometry]
  def bbox: Operators[BBox]
}
