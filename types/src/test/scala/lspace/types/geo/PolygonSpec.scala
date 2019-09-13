package lspace.types.geo

import org.scalatest.{Matchers, WordSpec}

class PolygonSpec extends WordSpec with Matchers {
  "A Polygon((2,2),(2,6),(6,6),(6,2))" should {
    "contain Point(4,4)" in {
      Polygon((2, 2), (2, 6), (6, 6), (6, 2)) contains Point(4, 4) shouldBe true
    }
    "not contain Point(1,2)" in {
      Polygon((2, 2), (2, 6), (6, 6), (6, 2)) contains Point(1, 2) shouldBe false
    }
  }
}
