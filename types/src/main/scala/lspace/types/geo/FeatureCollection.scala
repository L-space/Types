package lspace.types.geo

case class FeatureCollection[+T <: Geometry](features: List[Feature[T]], bbox: BBox)
