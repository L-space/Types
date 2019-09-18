package lspace.types.geo

case class FeatureCollection[+T <: Geometry] private (features: List[Feature[T]], bbox: Option[BBox] = None)
