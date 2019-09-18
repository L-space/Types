package lspace.types.geo

case class Feature[+T <: Geometry] private (geometry: T, properties: Map[String, Any], bbox: Option[BBox] = None)
