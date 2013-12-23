package com.rainysoft.decisiontree.tree

import org.scalatest._
import scala.collection.Traversable
import scala.collection.immutable.HashMap

class GeneratorTest extends FunSuite {

  test("Split samples on attribute") {
    val sample1 = (HashMap("attribute1" -> "value1", "attribute2" -> "value2", "attribute3" -> "value1"), 1)
    val sample2 = (HashMap("attribute1" -> "value2", "attribute2" -> "value2", "attribute3" -> "value1"), 1)
    val sample3 = (HashMap("attribute1" -> "value1", "attribute2" -> "value1", "attribute3" -> "value2"), 2)
    val ss = Traversable(sample1, sample2, sample3)
    
    // Map from attribute value to members.
    val splitsAttr1 = Generator.splitSamples(ss, "attribute1")
    assert(splitsAttr1("value1").size == 2)

    val splitsAttr2 = Generator.splitSamples(ss, "attribute3")
    assert(splitsAttr2("value2").size == 1)
  }

  test("Importance of attributes") {
    val sample1 = (HashMap("saturday" -> "yes", "sick" -> "yes", "money" -> "no"), 0)
    val sample2 = (HashMap("saturday" -> "yes", "sick" -> "yes", "money" -> "yes"), 0)
    val sample3 = (HashMap("saturday" -> "yes", "sick" -> "no", "money" -> "yes"), 1)
    val sample4 = (HashMap("saturday" -> "yes", "sick" -> "no", "money" -> "no"), 1)
    val sample5 = (HashMap("saturday" -> "no", "sick" -> "no", "money" -> "yes"), 1)
    val sample6 = (HashMap("saturday" -> "no", "sick" -> "no", "money" -> "no"), 0)
    val sample7 = (HashMap("saturday" -> "no", "sick" -> "yes", "money" -> "yes"), 1)
    val sample8 = (HashMap("saturday" -> "no", "sick" -> "yes", "money" -> "no"), 0)
    
    val samples = Traversable(sample1, sample2, sample3, sample4, sample5, sample6, sample7, sample8)
    val attributes = Traversable("saturday", "sick", "money")

    val importances = attributes.map(a => (a, Generator.importance(a, samples)))
    val splitAttr = importances.minBy(imp => imp._2)._1
    assert(splitAttr == "money")
  }
}
