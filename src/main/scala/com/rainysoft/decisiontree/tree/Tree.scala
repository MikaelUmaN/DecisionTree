abstract class Tree
case class SubTree(tree: Tree, value: Int) extends Tree
case class Leaf(value: Int) extends Tree
