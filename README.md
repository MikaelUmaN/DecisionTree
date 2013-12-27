# DecisionTree
This is an minimal implementation of a decision learning tree ([Wikipedia entry](http://en.wikipedia.org/wiki/Decision_tree)).

## Features
A decision tree has one root. Every node represents an attribute and there is one branch to a subtree for every attribute value. Attribute keys are strings but values can be of any type. Classifications can be of any type and are found at the leaf level.

Samples used to construct a key consist of attribute-value mappings as well as a classification for that sample. The `Generator#generateTree` function is used to construct a decision tree from the provided samples. Given a tree, a new, unclassified, sample can be classified by calling the `Classifier#classify` function. 

As of version 1.0, an attribute is assumed to have a discrete domain and range that is always completely enumerated in provided samples. This may for example cause problems if an attribute has an integer range.

# Dependencies
Java JVM, Scala (developed using version 2.10.3) and Simple Build Tool (sbt).

# Build & Run
Build.
```
sbt clean compile test:compile
```

Run test cases.
```
sbt test
```

Run main (if something useful is there...).
```
sbt run
```