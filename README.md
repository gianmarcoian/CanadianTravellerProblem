# CanadianTravellerProblem

The goal is to get from vertex 0 to vertex 9 with the minimal number of hops.

We are using 100 to indicate that a road is open. If a number is less than 100, it indicates the probability that the road is open, eg: 80 indicates an 80% probability. When we want to use any particular road that might not be open (ie: weight < 100), we will generate a random number on the range of 0-99 to make that determination. For instance, if the road has an 80% probability of being open, and our generated number is 0-79, it is open. If we generate 80-99, the road is closed. We will count the number of "hops" an algorithm must make (the number of edges traversed) rather than the actual values of the weights, since those weights are being used for a different purpose.

The project will require two approaches to the solution of the problem:

Approach 1. Implement a BFS routine to figure out the least number of hops, assuming all roads are available. This is the deterministic algorithm against which we can benchmark for the competitiveness of our probabilistic algorithm. Note that you must recount the shortest path. The best way to do this is with a map data structure in which the key value is the child node and the value is the parent node. For example, in the above graph, the <key><value> mapping for node 9 is {9,6} since 6 is the parent of 9. we would also have the mapping {6,1} as 1 is the parent of 6 and {1,0} since 0 is the parent of 1. Therefore, the path with the lowest number of hops is {0,1,6,9}. Note that the policy in your algorithm will determine if you find  {0,1,6,9} or {0,3,8,9} as the shortest path.

Approach 2. Implement  a DFS approach that must deal with roads that might or might not be open. When your algorithm selects an edge that is not guaranteed to be open, generate a random number and decide if you can traverse the edge. If you can, go on to the next vertex, if not:
count the attempt as a hop (assume that we only discovered part way through that the road was closed, so we had to go back to the previous vertex).
choose another edge from the source vertex and try again
if you cannot find an edge to a new vertex, you can backtrack from the current vertex to a prior one and try again with DFS. Count a backtrack as a hop.
With at least one path containing all 100% edges, your program should always produce a solution. If there are no paths that guarantee traversal from source to target, you might not find a solution at all.

Notes
No need for an input file. You may hard-code the adjacency matrix right in the program.
Always display the results of a run on the console showing both approaches.
Use the same graph data for both approaches. With BFS ignore the probabilities. A non-zero number means the edge can be traversed for the deterministic version
Run your program multiple times. Over multiple runs, your program should eventually stumble into an optimal result, get a few that are reasonably good, and some that are quite poor.
Provide the output of a good, fair, and poor run in a file named results.txt.
A run showing an optimal result on the probabilistic algorithm:


BFS - deterministic solution
  An optimal solution:
    {0,1,6,9}
  Total hops = 3

DFS - probabilistic solution
  On vertex: 0
  Going on edge (0,3) edge prob = 100%
  On vertex: 3
  Going on edge (3,8) edge prob = 50% r = 42
  On vertex: 8
  Going on edge (8,9) edge prob = 10% r = 2
  On vertex: 9
Total DFS hops = 3
Total DFS misses = 0
Path = {0,3,8,9}

A run showing a fair result on the probabilistic algorithm:

BFS - deterministic solution
  An optimal solution:
    {0,1,6,9}
  Total hops = 3

DFS - probabilistic solution
  On vertex: 0
  Going on edge (0,3) edge prob = 100%
  On vertex: 3
  Cannot go on edge (3,8) edge prob = 50% r = 98
  Going on edge (3,5) edge prob = 70% r = 53
  On vertex: 5
  Going on edge (5,8) edge prob = 100%
  On vertex: 8
  Cannot go on edge (8,9) edge prob = 10% r = 65
  Going on edge (8,7) edge prob = 20% r = 13
  On vertex: 7
  Going on edge (7,9) edge prob = 80% r = 31
  On vertex: 9
Total DFS hops = 7
Total DFS misses = 2
Path = {0,3,5,8,7,9}

A run showing a poor result on the probabilistic algorithm:


BFS - deterministic solution
  An optimal solution:
    {0,1,6,9}
  Total hops = 3

DFS - probabilistic solution
  On vertex: 0
  Going on edge (0,3) edge prob = 100%
  On vertex: 3
  Cannot go on edge (3,8) edge prob = 50% r = 88
  Going on edge (3,5) edge prob = 70% r = 11
  On vertex: 5
  Going on edge (5,8) edge prob = 100%
  On vertex: 8
  Cannot go on edge (8,9) edge prob = 10% r = 76
  Cannot go on edge (8,7) edge prob = 20% r = 32
  Backtracking to vertex 5
  Cannot go on edge (5,7) edge prob = 10% r = 66
  Going on edge (5,2) edge prob = 100%
  On vertex: 2
  Going on edge (2,4) edge prob = 100%
  On vertex: 4
  Cannot go on edge (4,7) edge prob = 30% r = 70
  Cannot go on edge (4,1) edge prob = 70% r = 95
  Backtracking to vertex 2
  Backtracking to vertex 5
  Backtracking to vertex 3
  Backtracking to vertex 0
  Going on edge (0,1) edge prob = 100%
  On vertex: 1
  Going on edge (1,6) edge prob = 100%
  On vertex: 6
  Going on edge (6,9) edge prob = 100%
  On vertex: 9
Total DFS hops = 19
Total DFS misses = 11
Path = {0,3,5,8,5,2,4,2,5,3,0,1,6,9}

