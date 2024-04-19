/***************************************************************
Student Name: Gianmarco Iannario
File Name: CanadianTraveller.java
Assignment number: Project2

File that contains the java class in which there are all the functionality requested.

***************************************************************/

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class CanadianTraveller {
    
    private final int V; //vertices
    private final int[][] adjMatrix; //adjacency matrix
    private final Random random = new Random(); //Random_number generator
    //flags i need
    private boolean optimalWritten = false; 
    private boolean fairWritten = false; 
    private boolean poorWritten = false;
    
    // Constructor to initialize the graph with the given adjacency matrix
    public CanadianTraveller(int[][] matrix) {
        this.V = matrix.length;
        this.adjMatrix = matrix;
    }

    public static int[][] InitProblem() {
        //hard-coded  matrix from the given graph
        int[][] graphMatrix = {
            {0, 100, 100, 100, 0, 0, 0, 0, 0, 0},   //node 0
            {100, 0, 0, 0, 70, 0, 100, 0, 0, 0},    //node 1
            {100, 0, 0, 0, 100, 100, 0, 0, 0, 0},   //node 2
            {100, 0, 0, 0, 0, 70, 0, 0, 50, 0},     //node 3
            {0, 70, 100, 0, 0, 0, 0, 30, 0, 0},     //node 4
            {0, 0, 100, 70, 0, 0, 0, 10, 0, 0},     //node 5
            {0, 100, 0, 0, 0, 0, 0, 0, 0, 100},     //node 6
            {0, 0, 0, 0, 30, 10, 0, 0, 0, 80},      //node 7
            {0, 0, 0, 50, 0, 0, 0, 0, 0, 10},       //node 8
            {0, 0, 0, 0, 0, 0, 100,80,10,0}         //node 9
        };
        return graphMatrix;
    }
    
    
    //BFS assuming all roads are available (probability of 100%)
    public void bfs(int start, int goal) {
        //creating a queue(useful for bfs) and a list of parents that i will use after
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[V];
        int[] parent = new int[V];
        Arrays.fill(parent, -1);
        List<Integer> path=new ArrayList<>();
        queue.offer(start);
        visited[start] = true;
        boolean found=false;
        //loop until queue is empty
        while (!queue.isEmpty()) {
            int current = queue.poll();
            //reaching the goal condition
            if (current == goal) {
                found=true;
                for (int at = goal; at != -1; at = parent[at]) {path.add(at);}
                Collections.reverse(path);
                System.out.println("BFS - deterministic solution");
                System.out.println("An optimal solution:");
                System.out.println(path);
                System.out.println("Total hops = " + (path.size() - 1));
            }
            //moving to the other node(the next in queue)
            for (int i = 0; i < V; i++) {
                if (!visited[i] && adjMatrix[current][i] > 0) {
                    visited[i] = true;
                    parent[i] = current;
                    queue.offer(i);
                }
            }
        }
        //if I can't reach node 9
        if(found!=true){
            System.out.println("no path found!");
            return;
        }
    }
    
    
 //DFS_Probabilistc
 public void dfsProbabilistic(int start, int goal) {
    System.out.println("\nDFS - probabilistic solution");

    boolean[] visited = new boolean[V]; //for visited vertices
    boolean[] deadEnds = new boolean[V]; //deadEnds for vertices leading to no success
    int[] parent = new int[V];
    Arrays.fill(parent, -1);   //parents array
    List<Integer> path = new ArrayList<>();
    Random random = new Random();   //I'm using this to select a random child (to get the exotic solution 0,3,8,9)
    boolean adv=false; // i will need this if I can't go to the next edge (p<r)
    int totalHops = 0; //counter for the hops
    int totalMisses = 0; //counter for the misses
    visited[start] = true; //array that gives me the already explored nodes
    int r=0; //for random
    int probability=0;
    int current = start;  //starting from 0
    List<Integer> children = new ArrayList<>(); //list for the children


    // until i do not have more chances to move
    while (current!=goal) {
        System.out.println("On vertex: " + current);
        path.add(current);
        children.clear();
        //reaching the goal

        // i need this to randomize the choiche of the child node
        for(int i=0;i<V;i++){
            if(adjMatrix[current][i]>0 && visited[i]!=true && current!=i && deadEnds[i]==false){
                children.add(i);
                //System.out.println("\nchild: " + i);
                }
        }
        //i use the children list to see where can i go through
        while (!children.isEmpty()) {
            //randomy choosing a child from children list
            int selectedIndex = random.nextInt(children.size());
            int selectedChild = children.get(selectedIndex);
            probability = adjMatrix[current][selectedChild];
            r=random.nextInt(100); 
            parent[selectedChild] = current;

            //p=100 on the edge-> I can pass
            if(probability==100){
                //parent[selectedChild] = current;
                visited[selectedChild] = true;
                current=selectedChild;
                totalHops++;
                System.out.println("Going on edge (" + parent[selectedChild] + "," + selectedChild + ") edge prob = " + probability);
                adv=true;
                break;
            }
            //p<r-> i can't pass
            else if(probability<r){
                System.out.println("Cannot go on edge (" + current + "," + selectedChild + ") edge prob = " + probability + "% r = " + r);
                totalMisses++;
                children.remove(selectedIndex);
                adv=false;
            }
            //100>p>r, i pass and i show both p and r in the printing
            else{
                System.out.println("Going on edge (" + current + "," + selectedChild + ") edge prob = " + probability + "% r = " + r);
                totalHops++;
                current=selectedChild;
                //parent[selectedChild] = current;
                visited[selectedChild] = true;
                adv=true;
                break;
                }

          }
        //if i can't pass by any of the children-> backtracking
        if(adv==false){
            deadEnds[current] = true;
            totalMisses++;
            System.out.println("Backtracking to vertex: " + parent[current]);
            current=parent[current];

            totalHops++;
        }
     }
                
    path.add(current);
    
    System.out.println("Total DFS hops = " + totalHops);
    System.out.println("Total DFS misses = " + totalMisses);
    System.out.println("Path = " + path);
    return;
        

 }





    //Main method to run the BFS and DFS approaches and display results, this time i will make him the most empty possible
    public static void main(String[] args) {

        //Initializing the problem..
        int [][] graphMatrix=null;
        graphMatrix=InitProblem();
        CanadianTraveller traveller = new CanadianTraveller(graphMatrix);

        //First approach: BFS 
        traveller.bfs(0, 9);


        //Second approach: Probabilistic DFS  
        traveller.dfsProbabilistic(0, 9);

    }
}
