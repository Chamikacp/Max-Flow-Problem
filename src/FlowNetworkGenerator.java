import java.util.Scanner;

import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.Stopwatch;

/*
    S.M.C.C.Perera
    w1714889
    2018101
*/

public class FlowNetworkGenerator {

    private static Scanner input = new Scanner(System.in);

    private static int numberOfNodes;
    private static int numberOfEdges;
    private static String ordinalIndicator;

    private static int[] edgeU;
    private static int[] edgeV;
    private static int[] nodeNumbers;
    private static int[][] edgeCapacity;

    //Print the Flow Network
    private static void showGraphDetails(){

        System.out.println("\nTotal Number of Nodes : " + numberOfNodes);
        System.out.println("Total Number of Edges  : " + numberOfEdges);
        System.out.println("\n*******************************************");
        System.out.println("             Network Flow                ");
        System.out.println("*******************************************\n");
        System.out.println("  Flow Connection || Connection Capacity ");

        int i = 0;
        while (i< edgeU.length) {
            System.out.printf("    %02d  ==> ", nodeNumbers[edgeU[i]]);     //%02d is java string format
            System.out.printf(" %02d      ", nodeNumbers[edgeV[i]]);
            System.out.printf("        %02d           \n", edgeCapacity[edgeU[i]][edgeV[i]]);
            i++;
        }
    }

    //To give numbers for nodes
    private static void numbersOfTheNodes(){
        int i = 0;
        while (i < numberOfNodes) {
            nodeNumbers[i] = i;
            i++;
        }
    }

    //to assign the ordinal indicator
    private static void ordinalIndicatorAssigner(int i) {
        if (i == 0) {
            ordinalIndicator = "st";
        } else if (i == 1) {
            ordinalIndicator = "nd";
        } else if (i == 2) {
            ordinalIndicator = "rd";
        } else {
            ordinalIndicator = "th";
        }
    }

    //To check weather user entered a non Integer
    public static void checkInteger(){
        if (!input.hasNextInt()) {
            do {
                System.out.print("Please enter an Integer : ");
                input.next();
            } while (!input.hasNextInt());
        }
    }

    //To calculate enters flow network
    public static void calculation(){

        //To get the elapsed time
        Stopwatch timer = new Stopwatch();

        MaximumFlowCalculator maximumFlowCalculator = new MaximumFlowCalculator();
        int maximumFlow = maximumFlowCalculator.calculateMaximumFlow(edgeCapacity, 0, numberOfNodes -1, numberOfNodes, nodeNumbers);

        System.out.println("Maximum Flow = " + maximumFlow);
        StdOut.println("Elapsed Time in Seconds = " + timer.elapsedTime());
    }

    //Main menu for calculation
    public static void mainMenu() {

        System.out.println("\n ******* FLOW NETWORK GENERATOR *******\n");
        System.out.println("Enter number 1 create a your own Graph");
        System.out.println("Enter number 2 to EXIT");
        System.out.print("\nINPUT : ");

        checkInteger();

        int userInput = input.nextInt();

        switch (userInput){
            case 1:
                System.out.println("\nStarting node is '0' and Ending node is 'numberOfNodes - 1'");
                System.out.print("Enter the Number of Nodes you need in the graph including Starting and Ending Node : ");
                checkInteger();

                numberOfNodes = input.nextInt();

                if (!(6 <= numberOfNodes)) {
                    do {
                        System.out.print("Enter a number 6 or above 6 : ");
                        checkInteger();
                        numberOfNodes = input.nextInt();
                    } while (!(6 <= numberOfNodes));
                }

                System.out.print("Enter the Number of Edges you need in the graph : ");
                checkInteger();

                numberOfEdges = input.nextInt();

                if (!(4 <= numberOfEdges) || !(numberOfEdges <= ((numberOfNodes * (numberOfNodes - 1)) - (2 * numberOfNodes) + 3))) {
                    do {
                        System.out.print("Enter a number between " + 4 + " and " + ((numberOfNodes * (numberOfNodes - 1)) - (2 * numberOfNodes) + 3) + " : ");
                        checkInteger();
                        numberOfEdges = input.nextInt();
                    } while (!(4 <= numberOfEdges) || !(numberOfEdges <= ((numberOfNodes * (numberOfNodes - 1)) - (2 * numberOfNodes) + 3)));
                }

                nodeNumbers = new int[numberOfNodes];
                edgeU = new int[numberOfEdges];
                edgeV = new int[numberOfEdges];
                edgeCapacity = new int[numberOfNodes][numberOfNodes];

                int i = 0;
                while (i < numberOfEdges) {
                    int start;
                    int end;
                    int capacity;

                    ordinalIndicatorAssigner(i);

                    System.out.print("Enter the Starting Node of the " + (i + 1) + ordinalIndicator + " edge : ");
                    checkInteger();

                    start = input.nextInt();
                    if ((start >= numberOfNodes - 1) || (start < 0)) {
                        do {
                            System.out.print("Enter a number between 0 and " + (numberOfNodes - 2) + " : ");
                            checkInteger();
                            start = input.nextInt();
                        } while ((start >= numberOfNodes - 1) || (start < 0));
                    }
                    edgeU[i] = start;

                    System.out.print("Enter the Ending Node of the " + (i + 1) + ordinalIndicator + " edge : ");
                    checkInteger();

                    end = input.nextInt();
                    if ((end > numberOfNodes - 1) || (end <= 0)) {
                        do {
                            System.out.print("Enter number between 1 and " + (numberOfNodes - 1) + " : ");
                            checkInteger();
                            end = input.nextInt();
                        } while ((end > numberOfNodes - 1) || (end <= 0));
                    }
                    edgeV[i] = end;

                    System.out.print("Enter the capacity of the " + (i + 1) + ordinalIndicator + " edge : ");
                    checkInteger();
                    capacity = input.nextInt();
                    if (capacity <= 0) {
                        do {
                            System.out.print("Please enter a number above 0 : ");
                            checkInteger();
                            capacity = input.nextInt();
                        } while (capacity <= 0);
                    }
                    edgeCapacity[start][end] = capacity;
                    i++;
                }

                numbersOfTheNodes();
                showGraphDetails();

                calculation();

                break;

            case 2:
                System.exit(0);

            default:
                System.out.println("");
                System.out.println("Invalid input");
                System.out.println("");
                mainMenu();

        }
    }

    //To edit an exciting Flow Network
    public static void editMenu(){
        System.out.println("");
        System.out.println("Enter number 1 to Add Edges");
        System.out.println("Enter number 2 to Remove Edges");
        System.out.println("Enter number 3 to goto Main menu");
        System.out.print("\nINPUT : ");

        checkInteger();

        int userInput = input.nextInt();

        switch (userInput){
            case 1:
                System.out.print("\nEnter the number of new edges to add : ");
                checkInteger();

                int newEdgesNumber = input.nextInt();

                int newEdgeStart;
                int newEdgeEnd;
                int newCapacity;

            {
                int i = 0;
                while (i < newEdgesNumber) {

                    ordinalIndicatorAssigner(i);
                    System.out.print("Enter the New Starting Node of the " + (i + 1) + ordinalIndicator + " edge : ");
                    checkInteger();
                    newEdgeStart = input.nextInt();

                    if ((newEdgeStart >= numberOfNodes - 1) || (newEdgeStart < 0)) {
                        do {
                            System.out.print("Enter a number between 0 and " + (numberOfNodes - 2) + " : ");
                            checkInteger();
                            newEdgeStart = input.nextInt();
                        } while ((newEdgeStart >= numberOfNodes - 1) || (newEdgeStart < 0));
                    }

                    System.out.print("Enter the New Ending Node of the " + (i + 1) + ordinalIndicator + " edge : ");
                    checkInteger();
                    newEdgeEnd = input.nextInt();

                    if ((newEdgeEnd > numberOfNodes - 1) || (newEdgeEnd <= 0)) {
                        do {
                            System.out.print("Enter a number between 1 and " + (numberOfNodes - 1) + " : ");
                            checkInteger();
                            newEdgeEnd = input.nextInt();
                        } while ((newEdgeEnd > numberOfNodes - 1) || (newEdgeEnd <= 0));
                    }

                    System.out.print("Enter the New Capacity of your " + (i + 1) + ordinalIndicator + " edge : ");
                    checkInteger();
                    newCapacity = input.nextInt();

                    if ((newCapacity <= 0)) {
                        do {
                            System.out.print("Enter a number above 0 add  a new capacity : ");
                            checkInteger();
                            newCapacity = input.nextInt();
                        } while ((newCapacity <= 0));
                    }
                    edgeCapacity[newEdgeStart][newEdgeEnd] = newCapacity;
                    i++;
                }
            }
                numbersOfTheNodes();
                showGraphDetails();

                calculation();
                editMenu();
                break;
            case 2:

                System.out.print("\nEnter the number of edges you want to remove :");
                checkInteger();

                int removeEdgeNumber = input.nextInt();

                int i = 0;
                while (i < removeEdgeNumber) {
                    int start;
                    int end;

                    ordinalIndicatorAssigner(i);

                    System.out.print("Enter the Starting Node of the " + (i + 1) + ordinalIndicator + " edge to remove : ");
                    checkInteger();

                    start = input.nextInt();

                    if ((start >= numberOfNodes - 1) || (start < 0)) {
                        do {
                            System.out.print("Enter a number between 0 and " + (numberOfNodes - 2) + " : ");
                            checkInteger();
                            start = input.nextInt();
                        } while ((start >= numberOfNodes - 1) || (start < 0));
                    }

                    System.out.print("Enter the Ending Node of your " + (i + 1) + ordinalIndicator + " edge to remove : ");
                    checkInteger();
                    end = input.nextInt();
                    if ((end > numberOfNodes - 1) || (end <= 0)) {
                        do {
                            System.out.println("Enter a number between 1 and " + (numberOfNodes - 1) + " : ");
                            checkInteger();
                            end = input.nextInt();
                        } while ((end > numberOfNodes - 1) || (end <= 0));
                    }
                    edgeCapacity[start][end] = 0;
                    i++;
                }

                numbersOfTheNodes();
                showGraphDetails();

                calculation();
                editMenu();
                break;

            case 3:
               mainMenu();
            default:
                System.out.println("");
                System.out.println("Invalid input");
                System.out.println("");
                editMenu();
        }
    }

    public static void main(String[] args) {
        mainMenu();
        editMenu();
    }
}
