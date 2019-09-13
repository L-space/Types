package lspace.types.geo

import org.scalatest.{Matchers, WordSpec}

class BBoxSpec extends WordSpec with Matchers {

  "A BBox(5,5,10,10)" should {
    "be within BBox(4,4,11,11)" in {
      BBox(5, 5, 10, 10) within BBox(4, 4, 11, 11) shouldBe true
    }
    "not be within BBox(6, 6, 11, 11) or BBox(6, 6, 9, 9)" in {
      BBox(5, 5, 10, 10) within BBox(6, 6, 11, 11) shouldBe false
      BBox(5, 5, 10, 10) within BBox(6, 6, 9, 9) shouldBe false
    }
    "contain BBox(6,6,9,9)" in {
      BBox(5, 5, 10, 10) contains BBox(6, 6, 9, 9) shouldBe true
    }
    "not contain BBox(4,4,11,11) or BBox(4,4,9,9)" in {
      BBox(5, 5, 10, 10) contains BBox(4, 4, 11, 11) shouldBe false
      BBox(5, 5, 10, 10) contains BBox(4, 4, 9, 9) shouldBe false
    }
  }
}
