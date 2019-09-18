package lspace.types.geo

case class Feature[T <: Geometry](geometry: T, properties: Map[String, Any], bbox: BBox)
