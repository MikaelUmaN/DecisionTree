package com.rainysoft.decisiontree.tree

import org.scalatest._
import scala.collection.Traversable
import scala.collection.immutable.HashMap

class ClassifierTest extends FunSuite {

  test("Classify sample based on tree") {
    val sample1 = (HashMap("sick" -> true, "saturday" -> true, "money" -> true), false)
    val sample2 = (HashMap("sick" -> true, "saturday" -> true, "money" -> false), false)
    val sample3 = (HashMap("sick" -> true, "saturday" -> false, "money" -> false), false)
    val sample4 = (HashMap("sick" -> true, "saturday" -> false, "money" -> true), false)
    val sample5 = (HashMap("sick" -> false, "saturday" -> true, "money" -> true), true)
    val sample6 = (HashMap("sick" -> false, "saturday" -> true, "money" -> false), true)
    val sample7 = (HashMap("sick" -> false, "saturday" -> false, "money" -> false), false)
    val sample8 = (HashMap("sick" -> false, "saturday" -> false, "money" -> true), true)
  
    val samples = Traversable(sample1, sample2, sample3, sample4, sample5, sample6, sample7, sample8)
    val attributes = Traversable("sick", "saturday", "money")
    val decisionTree = Generator.generateTree(attributes, samples)
    
    val testSample1 = HashMap("sick" -> true, "saturday" -> true, "money" -> true)
    val testSample2 = HashMap("sick" -> false, "saturday" -> true, "money" -> true)

    val classification1 = Classifier.classify(testSample1, decisionTree)
    val classification2 = Classifier.classify(testSample2, decisionTree)

    assert(classification1 == false)
    assert(classification2 == true)
  }
}
