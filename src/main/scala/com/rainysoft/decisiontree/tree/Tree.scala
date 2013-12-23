package com.rainysoft.decisiontree.tree

import scala.collection.Traversable
import scala.collection.immutable.Map

/** A tree is either a leaf with a classification value
 * or it is sub tree with an attribute and one branch
 * per each attribute value. Each branch is then a
 * tree.
 */
abstract class Tree

/**
 * A subtree. An attribute that splits the tree into nodes with
 * one node per possible attribute value.
 */
case class SubTree(attribute: String, branches: Map[String, Tree]) extends Tree

/** A leaf that contains the classification value. */
case class Leaf(value: Int) extends Tree
