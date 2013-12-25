package com.rainysoft.decisiontree.tree

import scala.collection.immutable.Map

/** Singleton to classify a sample.
 *
 */
object Classifier {

  /** Classifies a sample given a decision tree.
   *
   */
  def classify[A,B](sample: Map[String, A], tree: Tree[A,B]): B = {
    tree match {
      case Leaf(value) =>
        return value
      case SubTree(attribute, branches) =>
        val sampleAttributeValue = sample(attribute)
        val subTree = branches(sampleAttributeValue)
        return classify[A,B](sample, subTree)
    }
  }
}
