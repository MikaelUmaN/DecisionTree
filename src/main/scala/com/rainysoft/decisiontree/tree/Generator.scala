package com.rainysoft.decisiontree.tree

import scala.math._
import scala.collection.Traversable
import scala.collection.immutable.Map

/** Singleton object used because Scala does not
 * allow bare functions as it has to comply with
 * Java and JVM etc.
 *
 * Note that a scala object is different from a
 * scala class. There is only one object of type
 * Generator.
 */
object Generator {

  /** Splits samples based on the attribute.
   *
   * Returns a mapping from attribute value to list of samples.
   */
  def splitSamples(samples: Traversable[(Map[String, String], Int)], attribute: String) = {
    val samplesByAttrVal = samples.groupBy(s => s._1(attribute))
    samplesByAttrVal
  }

  /** Calculates the entropy of a random variable.
   *
   * The distribution is defined based on the sample frequencies.
   */
  def entropy(sampleCounts: Traversable[Int]) = {
    val totalCount = sampleCounts.sum
    val s = sampleCounts.map(c => {
        val pvk = c / totalCount
        pvk * log10(pvk) / log10(2)
    }).sum
    -s
  }

  /** Calculates importance of the attribute.
   *
   * This is the information gain by splitting on this attribute relative to
   * splitting on other attributes.
   */
  def importance(attribute: String, samples: Traversable[(Map[String, String], Int)]) = {
    val splits = splitSamples(samples, attribute)
    splits.map(s => {
        val attributeValue = s._1
        val sampleList = s._2
        val sampleCountByClassification = sampleList.groupBy(sa => sa._2).map(sagr => sagr._2.size)
        (sampleList.size / samples.size) * entropy(sampleCountByClassification)
    }).sum
  }

  /** Generates a tree using the given samples and the provided
   * list of all possible attributes.
   *
   * Currently attributes are hard coded to come as string,string
   * key value pairs. The particular attribute type, for example
   * "symbol" is assumed to be a string, and any particular
   * values, for example "AAPL" are assumed to be strings.
   */
  def generateTree(attributes: Traversable[String], samples: Traversable[(Map[String, String], Int)]) = {

    // For recursive functions, we have to define
    // the return type.
    def decTree(attrs: Traversable[String], ss: Traversable[(Map[String, String], Int)], pv: Int): Tree = {

      // Plurality value of these samples.
      val cv = ss.groupBy(s => s._2).maxBy(gr => gr._2.size)._1

      if (ss.size == 0) {

        // No more samples, Use plurality value of parents.
        return Leaf(pv)
      } else if (ss.groupBy(s => s._2).size == 1) {
        
        // Same classification on all samples.
        return Leaf(ss.head._2)
      } else if (attrs.size == 0) {

        // No more samples to split on. Use plurality value of the samples.
        Leaf(cv)
      } else {

        // Find attribute to split on.
        val importances = attrs.map(a => (a, importance(a, ss)))
        val splitAttr = importances.minBy(imp => imp._2)._1
        val remainingAttrs = attrs.filterNot(a => a == splitAttr)

        // Split on the attribute.
        // For each resulting split, make a recursive call
        // and create a branch.
        val splits = splitSamples(ss, splitAttr)
        val subTree = SubTree(splitAttr, splits.map(s => s._1 -> decTree(remainingAttrs, s._2, cv)))
        return subTree
      }
    }

    // Get the plurality value of the samples.
    val pv = samples.groupBy(s => s._2).maxBy(sgr => sgr._2.size)._2.size
    decTree(attributes, samples, pv)
  }
}
