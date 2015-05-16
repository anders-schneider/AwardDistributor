# AwardDistributor

Independent Project (inspired by work as a teacher)

This program takes in ranked lists of award nominations and determines the optimal distribution of awards amongs the nominees under the constraint that no nominee may be selected for more than one award. Underneath the covers, the program accomplishes this by solving the bipartite-graph matching problem using the Hungarian Algorithm. This entails using a variation of the Ford-Fulkerson max-flow algorithm as well as Konig's theorem to find a minimum vertex cover (reference links that I used are included at the bottom of this document). This algorithm gives a time complexity of O(n^4), a dramatic improvement over the factorial time complexity I first encountered with this project. Furthermore, the algorithm is guaranteed to find an optimal solution, if one exists. If multiple optimal solutions exist, the program selects one of them arbitrarily.

This project was inspired by my work as an 11th grade Physics teacher at YES Prep Southwest. Our grade level team gave out two awards for each class: for example, one 11th grader received the Physics Overall Achievement Award and another 11th grader received the Physics Growth Award. To increase the number of students receiving awards, we decided that no student should be allowed to receive more than one award and so teachers had to submit ranked lists of nominations for their class's awards. This is one example of a use case for this program, although others certainly exist.

Reference links:

Hungarian Algorithm:
http://webcache.googleusercontent.com/search?q=cache:EYqpNAzoHukJ:https://community.topcoder.com/tc%3Fmodule%3DStatic%26d1%3Dtutorials%26d2%3DhungarianAlgorithm+&cd=1&hl=en&ct=clnk&gl=us

Ford-Fulkerson Algorithm:
https://www.youtube.com/watch?v=hmIrJCGPPG4

Konig's Theorem:
http://en.wikipedia.org/wiki/König's_theorem_(graph_theory)#Algorithm