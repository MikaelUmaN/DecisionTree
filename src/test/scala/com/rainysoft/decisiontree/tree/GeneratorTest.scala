package com.rainysoft.decisiontree.tree

import org.scalatest._
import scala.collection.Traversable
import scala.collection.immutable.HashSet

class GeneratorTest extends FunSuite {

  test("Remove this test") {
    assert(true)
  }

  test("Split samples on attribute") {
    val sample1 = HashSet.apply(("attribute1", "value1"), ("attribute2", "value2"), ("attribute3", "value1"))
    val sample2 = HashSet.apply(("attribute1", "value2"), ("attribute2", "value2"), ("attribute3", "value1"))
    val sample3 = HashSet.apply(("attribute1", "value1"), ("attribute2", "value1"), ("attribute3", "value2"))

  }
}
