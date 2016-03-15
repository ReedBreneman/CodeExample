# CodeExample

Dependency Evaluator code example

This project is intended to solve the following question...

In many languages, static dependencies can be determined by source code analysis. 
Tools such as JDepend that look for explicit dependencies in the source and list them out.

One of the insidious things about dependencies is that they are transitive—if A depends on B and B depends on C, 
then A also depends on C. 

 Given the following input, we know that A directly depends on B and C, B depends on C and E, and so on.

```
1 | A   B   C  
2 | B   C   E  
3 | C   G  
4 | D   A   F  
5 | E   F  
6 | F   H 
```

The program should use this some data like this to calculate the full set of dependencies. In this example, 
looking at B, we see it directly depends on C and E. C in turn relies on G, E relies on F, and F relies on H. 
This means that B ultimately relies on C, E, F, G, and H. 

The result below shows a solution from the set of data above.

```
1 | A   B C E F G H    
2 | B   C E F G H  
3 | C   G  
4 | D   A B C E F G H  
5 | E   F H  
6 | F   H  
```

The project is setup and built using Eclipse 'Version: Mars.1 Release (4.5.1)'.
It was build against and tested against java Version 1.8.0_71.
The build.xml was tested with ant Version 1.9.6

The one piece that expects the eclipse environment is the junit 4.12.0 libraries sourced from the eclipse plugin directories.
You can modify the build.xml & eclipse project classpath to use another instance of junit as needed.

To execute the code please issue the following command:
'java -jar DependencyEvaluator.jar [-f <input file name>]'
The default input file is './data/input.dat'.

There is a compiled DependencyEvaluator.jar file is available in the 'deploy' folder.
The main ant tasks are:  
    cleanall  This will clean up the output folders in preparation for a clean build.  
    jar       This will compile the source & place DependencyEvaluator.jar in the ./deploy directory.  
    runTests  This will execute the junit tests & generate a report in the ./testclasses/report/index.html  
    
