package com.rainysoft.decisiontree.tree

import scala.collection.Traversable
import scala.collection.immutable.HashSet

/** Singleton object used because Scala does not
 * allow bare functions as it has to comply with
 * Java and JVM etc.
 *
 * Note that a scala object is different from a
 * scala class. There is only one object of type
 * Generator.
 */
object Generator {

  /** Generates a tree using the given samples and the provided
   * list of all possible attributes.
   *
   * Currently attributes are hard coded to come as string,string
   * key value pairs. The particular attribute type, for example
   * "symbol" is assumed to be a string, and any particular
   * values, for example "AAPL" are assumed to be strings.
   */
  def generateTree(attributes: Traversable[String], samples: Traversable[HashSet[(String, String)]]) = {

    // For recursive functions, we have to define
    // the return type. Builds a subtree from the given
    // samples and attributes left to split on. The plurality
    // value of the parents are used to determine the classification
    // in case of missing information in remaining samples.
    def decTree(attrs: Traversable[String], ss: Traversable[HashSet[(String, String)]], pv: Int) = {

      // Splits the samples into mapping from 
      // attribute value to all matching samples.
      def splitSamples(attr: String) = {

        // Group by the value of samples for this particular attribute.
        // val cannot be reassigned to.
        val ssByAttrVal = ss.groupBy(s => s.find(x => x._1 == attr).get._2)
        ssByAttrVal
      }

      splitSamples("MyAttribute")
      //return Leaf(pv)
    }

    // If we add return statement, then
    // we have to also declare the return type.
    // If we just have this line, then type inference
    // works.
    decTree(attributes, samples, 2)
  }
}
