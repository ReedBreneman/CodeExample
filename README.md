# CodeExample

Dependency Evaluator code example

This project is intended to solve the following question...

In many languages, static dependencies can be determined by source code analysis. Tools such as JDepend that look for explicit dependencies in the source and list them out.

One of the insidious things about dependencies is that they are transitive—if A depends on B and B depends on C, then A also depends on C. 

 Given the following input, we know that A directly depends on B and C, B depends on C and E, and so on.

A   B   C
B   C   E
C   G
D   A   F
E   F
F   H

The program should use this some data like this to calculate the full set of dependencies. In this example, looking at B, we see it directly depends on C and E. C in turn relies on G, E relies on F, and F relies on H. This means that B ultimately relies on C, E, F, G, and H. 

The result below shows a solution from the set of data above.

A   B C E F G H
B   C E F G H
C   G
D   A B C E F G H
E   F H
F   H

