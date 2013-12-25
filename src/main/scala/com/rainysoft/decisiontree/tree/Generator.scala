package com.rainysoft.decisiontree.tree

import scala.math._
import scala.collection.Traversable
import scala.collection.immutable.Map

/** Singleton containing methods to generate a decision tree.
 *
 */
object Generator {

  /** Splits samples based on the attribute.
   *
   * Returns a mapping from attribute value to list of samples.
   */
  def splitSamples[A,B](samples: Traversable[(Map[String, A], B)], attribute: String) = {
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
  def importance[A,B](attribute: String, samples: Traversable[(Map[String, A], B)]) = {
    val splits = splitSamples[A,B](samples, attribute)
    splits.map(s => {
        val attributeValue = s._1
        val sampleList = s._2
        val sampleCountByClassification = sampleList.groupBy(sa => sa._2).map(sagr => sagr._2.size)
        (sampleList.size / samples.size) * entropy(sampleCountByClassification)
    }).sum
  }

  /** Generates a tree using the given samples and the provided list of all possible attributes.
   *
   */
  def generateTree[A,B](attributes: Traversable[String], samples: Traversable[(Map[String, A], B)]) = {

    // For recursive functions, we have to define
    // the return type.
    def decTree[A,B](attrs: Traversable[String], ss: Traversable[(Map[String, A], B)], pv: B): Tree[A,B] = {

      // Plurality value of these samples.
      val cv = ss.groupBy(s => s._2).maxBy(gr => gr._2.size)._1

      if (ss.size == 0) {

        // No more samples, Use plurality value of parents.
        return Leaf[A,B](pv)
      } else if (ss.groupBy(s => s._2).size == 1) {
        
        // Same classification on all samples.
        return Leaf[A,B](ss.head._2)
      } else if (attrs.size == 0) {

        // No more samples to split on. Use plurality value of the samples.
        Leaf[A,B](cv)
      } else {

        // Find attribute to split on.
        val importances = attrs.map(a => (a, importance[A,B](a, ss)))
        val splitAttr = importances.minBy(imp => imp._2)._1
        val remainingAttrs = attrs.filterNot(a => a == splitAttr)

        // Split on the attribute.
        // For each resulting split, make a recursive call
        // and create a branch.
        val splits = splitSamples[A,B](ss, splitAttr)
        val subTree = SubTree[A,B](splitAttr, splits.map(s => s._1 -> decTree(remainingAttrs, s._2, cv)))
        return subTree
      }
    }

    // Get the plurality value of the samples.
    val pv = samples.groupBy(s => s._2).maxBy(sgr => sgr._2.size)._1
    decTree[A,B](attributes, samples, pv)
  }
}
