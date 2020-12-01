# Evolutionary-Algorithms
A Comparison of Genetic Algorithm and Population Based Incremental Learning

Evolutionary Algorithms aim to create a population that has specific characteristics or is best fitting to solve a problem. There exist multiple different approaches to Evolutionary Algorithms, two of which are Genetic Algorithms and Population Based Incremental Learning (PBIL). These approaches can be used in solving various types of optimization problems, one of which is MAXSAT problems. Here the effectiveness of Genetic Algorithms and PBIL on solving MAXSAT problems will be evaluated through implementation of the two algorithms and experiments on the implementations. 

The code must be run through user input from the IDE's console from the Execute class. Once the Execute class has been run the user will be prompted to enter the parameters for a test.  Those paramters are explained below.  

To run a version of the genetic algorithm code the user must write a single line specifying certain parameters described below:

[the name of the file containing the problem] [# of individuals in the populaiton] [the type of selection method: rs | ts | gs] [the type of crossover method: 1c | uc] [the crossover probability] [the mutation probability] [the number of generations] [g for GA].

So an example of a user input to run the genetic algorithm code that tests the problem in the file s2v120c1200-3.cnf for 100 individuals with a rs type selection and 1c crossover method with a mutation probability of 0.5 and a crossover probability of 0.2 for 1000 generations would be:

s2v120c1200-3.cnf 100 rs 1c 0.2 0.5 1000 g


To run a version of the PBIL code the user must write a single line specifying certain parameters described below:

[the name of the file containing the problem] [# of individuals in the populaiton] [the positive learning rate] [the negative learning rate] [the mutation probability] [the mutation amount] [the number of generations] [p for PBIL].

So an example of a user input to run the PBIL that tests the problem in the file s2v120c1200-3.cnf for 100 individuals that has a positive learning rate of 0.05 and a negative learning rate pf 0.02 with a mutation probability of 0.5 and a mutation amount of 0.01 for 1000 generations would be:

s2v120c1200-3.cnf 100 0.05 0.02 0.5 0.01 1000 p
