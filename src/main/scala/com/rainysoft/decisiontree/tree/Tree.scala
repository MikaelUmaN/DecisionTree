package com.rainysoft.decisiontree.tree

import scala.collection.immutable.Map

/** A tree is either a leaf with a classification value
 * or it is sub tree with an attribute and one branch
 * per each attribute value. Each branch is then a
 * tree.
 */
abstract class Tree[A,B]

/**
 * A subtree. An attribute that splits the tree into nodes with
 * one node per possible attribute value.
 */
case class SubTree[A,B](attribute: String, branches: Map[A, Tree[A,B]]) extends Tree[A,B]

/** A leaf that contains the classification value. */
case class Leaf[A,B](value: B) extends Tree[A,B]
