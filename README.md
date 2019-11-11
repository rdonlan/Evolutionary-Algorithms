# Evolutionary-Algorithms
A Comparison of Genetic Algorithm and Population Based Incremental Learning

The file labeled "Nature Inspired Computation PROJECT 1-2" is a pdf that describes the code and what it was used for.

To run the code it must be downloaded and run through user input into the IDE's console. To run a version of the genetic algorithm code the user must write a single line that specifies specific parameters:

[the name of the file containing the problem] [# of individuals in the populaiton] [the type of selection method: rs | ts | gs] [the type of crossover method: 1c | uc] [the crossover probability] [the mutation probability] [the number of generations] [g for GA].

So an example of a user input to run the genetic algorithm code that tests the problem in the file s2v120c1200-3.cnf for 100 individuals with a rs type selection and 1c crossover method with a mutation probability of 0.5 and a crossover probability of 0.2 for 1000 generations would be:

s2v120c1200-3.cnf 100 rs 1c 0.2 0.5 1000 g


To run a version of the PBIL code the user must write a single line that specifies specific parameters:

[the name of the file containing the problem] [# of individuals in the populaiton] [the positive learning rate] [the negative learning rate] [the mutation probability] [the mutation amount] [the number of generations] [p for PBIL].

So an example of a user input to run the PBIL that tests the problem in the file s2v120c1200-3.cnf for 100 individuals that has a positive learning rate of 0.05 and a negative learning rate pf 0.02 with a mutation probability of 0.5 and a mutation amount of 0.01 for 1000 generations would be:

s2v120c1200-3.cnf 100 0.05 0.02 0.5 0.01 1000 p
