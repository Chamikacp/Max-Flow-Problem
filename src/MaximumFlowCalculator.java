import java.util.ArrayList;
import java.util.LinkedList;

/*
    S.M.C.C.Perera
    w1714889
    2018101
*/

public class MaximumFlowCalculator {

    private int numberOfVertices ; //Number of Nodes in a Flow Network

    private int stepNumber = 1;   //To get the step number to calculate maximum flow

    //if a path is available this will return true. parent array is used to store the the path
    private boolean breadthFirstSearch(int[][] rGraph, int startingNode, int endingNode, int[] parent){

        //Visited array initialization
        boolean[] visited = new boolean[numberOfVertices];

        //To mark every node is unvisited
        int i = 0;
        while (i < numberOfVertices) {
            visited[i] = false;
            ++i;
        }

        //Linked list to store the nodes
        LinkedList<Integer> listOfNodes = new LinkedList<Integer>();

        //To enqueue source node and to mark source node in visited array
        listOfNodes.add(startingNode);
        visited[startingNode] = true;
        parent[startingNode] = -1;

        if (listOfNodes.size() != 0) {
            do {

                //Retrieve and remove the head of the list
                int u = listOfNodes.poll();

                int v = 0;
                while (v < numberOfVertices) {

                    //To check if a node is not visited and have a capacity
                    if (!visited[v] && rGraph[u][v] > 0) {

                        //To add to listOfNodes
                        listOfNodes.add(v);
                        parent[v] = u;

                        //Mark node as visited
                        visited[v] = true;
                    }

                    v++;
                }
            } while (listOfNodes.size() != 0);
        }

        //To return that t node visited or not
        return (visited[endingNode]);
    }

    //To get the maximum flow from s to t
    public int calculateMaximumFlow(int[][] graph, int s, int t, int V, int[] namesOfNodes){

        //Creating temporary ArrayList to store the starting nodes, ending nodes, path flows and renewed capacity lists to graphically represent a flow starting from node 's' (node 0)
        ArrayList<Integer> temporaryU = new ArrayList<Integer>();
        ArrayList<Integer> temporaryV = new ArrayList<Integer>();
        ArrayList<Integer> finalU = new ArrayList<Integer>();
        ArrayList<Integer> finalV = new ArrayList<Integer>();

        //To assign length of one dimension of the 2D array
        this.numberOfVertices = V;

        //starting node is u and ending node is v
        int u, v;


        // Residual graph where rGraph[u][v] indicates residual capacity of edge from u to v

        //To copy the original capacity array to a new array
        int[][] rGraph = new int[V][V];
        u = 0;
        while (u < V) {
            for (v = 0; v < V; v++) {
                rGraph[u][v] = graph[u][v];
            }
            u++;
        }

        // parent array is filled by bread First Search  to store path
        int[] parent = new int[V];

        //To store calculated maximum flow
        int maximum_flow = 0;

        //To do the flow  until the last node is visited
        while (breadthFirstSearch(rGraph,s,t,parent)){

            //To assign the maximum fow value to the path flow in iteration
            int path_flow = Integer.MAX_VALUE;


            // To find the maximum flow value through the path that found.
            v = t;
            while (v !=s) {
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
                v = parent[v];
            }

            // update residual capacities of the edges and reverse edges along the path
            v = t;
            while (v != s) {

                u = parent[v];

                //If the stating node is 0 to clear the final arrays and add temporary u,v
                if(u != 0){
                    finalU.clear();
                    finalV.clear();
                    temporaryU.add(u);
                    temporaryV.add(v);
                }
                else {

                    //To show the connecting nodes,capacity and flow
                    System.out.println("");
                    System.out.println("STEP "+ stepNumber + " : " + "Node " + namesOfNodes[u] + " is connecting to " + "Node " + namesOfNodes[v] + "       Capacity = " + rGraph[u][v]);
                    System.out.println("\n * " + "Flow that sent from " + namesOfNodes[u] + " to " + namesOfNodes[v] + " = " + path_flow);

                    rGraph[u][v] -= path_flow;
                    rGraph[v][u] += path_flow;

                    //To show the available capacity of the nodes
                    System.out.println(" * " + "Capacity available " + namesOfNodes[u] + " to " + namesOfNodes[v] + "  = " + rGraph[u][v]);
                    System.out.println(" * " + "Capacity available " + namesOfNodes[v] + " to " + namesOfNodes[u] + "  = " + rGraph[v][u]);
                    System.out.println(" ");

                    finalU.add(u);
                    finalV.add(v);
                    stepNumber++;

                    //To
                    int i = temporaryU.size()-1;
                    if (i > -1) {
                        do {

                            //To show the connecting nodes,capacity and flow
                            System.out.println("");
                            System.out.println("STEP " + stepNumber + " : " + "Node " + namesOfNodes[temporaryU.get(i)] + " is connecting to " + "Node " + namesOfNodes[temporaryV.get(i)] + "       Capacity = " + rGraph[temporaryU.get(i)][temporaryV.get(i)]);
                            System.out.println("\n * " + "Flow that sent from " + namesOfNodes[temporaryU.get(i)] + " to " + namesOfNodes[temporaryV.get(i)] + " = " + path_flow);

                            rGraph[temporaryU.get(i)][temporaryV.get(i)] -= path_flow;
                            rGraph[temporaryV.get(i)][temporaryU.get(i)] += path_flow;

                            finalU.add(temporaryU.get(i));
                            finalV.add(temporaryV.get(i));

                            //To show the available capacity of the nodes
                            System.out.println(" * " + "Capacity available " + namesOfNodes[temporaryU.get(i)] + " to " + namesOfNodes[temporaryV.get(i)] + "  = " + rGraph[temporaryU.get(i)][temporaryV.get(i)]);
                            System.out.println(" * " + "Capacity available " + namesOfNodes[temporaryV.get(i)] + " to " + namesOfNodes[temporaryU.get(i)] + "  = " + rGraph[temporaryV.get(i)][temporaryU.get(i)]);
                            System.out.println(" ");

                            stepNumber++;

                            i--;
                        } while (i > -1);
                    }
                    // To clear the temporary arrays
                    temporaryU.clear();
                    temporaryV.clear();
                }
                v = parent[v];
            }

            //To add path flow to maximum flow
            maximum_flow += path_flow;
        }

        // Return the overall maximum flow
        return maximum_flow;
    }
}
