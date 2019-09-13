package lspace.types.geo

import org.scalatest.{Matchers, WordSpec}

class BBoxSpec extends WordSpec with Matchers {

  "A BBox(5,5,10,10)" should {
    "be within" in {
      BBox(5, 5, 10, 10) within BBox(4, 4, 11, 11) shouldBe true
    }
    "not be within" in {
      BBox(5, 5, 10, 10) within BBox(6, 6, 11, 11) shouldBe false
      BBox(5, 5, 10, 10) within BBox(6, 6, 9, 9) shouldBe false
      BBox(5, 5, 10, 10) within BBox(11, 11, 12, 12) shouldBe false
    }
    "contain" in {
      BBox(5, 5, 10, 10) contains BBox(6, 6, 9, 9) shouldBe true
      BBox(5, 5, 10, 10) contains Point(6, 6) shouldBe true

    }
    "not contain " in {
      BBox(5, 5, 10, 10) contains BBox(4, 4, 11, 11) shouldBe false
      BBox(5, 5, 10, 10) contains BBox(4, 4, 9, 9) shouldBe false
      BBox(5, 5, 10, 10) contains Point(12, 6) shouldBe false
    }
  }
}
