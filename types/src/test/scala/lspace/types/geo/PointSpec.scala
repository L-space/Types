package lspace.types.geo

import org.scalatest.{Matchers, WordSpec}

class PointSpec extends WordSpec with Matchers {
  "A Point(4,4)" should {
    "be within Polygon((2,2),(2,6),(6,6),(6,2))" in {
      Point(4, 4) within Polygon((2, 2), (2, 6), (6, 6), (6, 2)) shouldBe true
    }

    "not be within Polygon((5,5),(5,6),(6,6),(6,5))" in {
      Point(4, 4) within Polygon((5, 5), (5, 6), (6, 6), (6, 5)) shouldBe false
    }
  }
}
