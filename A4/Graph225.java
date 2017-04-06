
/*
 * University of Victoria
 * CSC 225 - Fall 2016
 * Code template for assignment 4
 * Allan Liu
 * V00806981
 */
import java.io.IOException;
import java.io.*;
import java.util.*;

// DO NOT CHANGE THE CLASS NAME OR PACKAGE
public class Graph225 {

	/**
	 * Simple representation of an undirected graph, using a square, symmetric
	 * adjacency matrix.
	 * <p>
	 * An adjacency matrix M represents a graph G=(V,E) where V is a set of n
	 * vertices and E is a set of m edges. The size of the matrix is {@code n},
	 * where {@code n} is in the range {@code [4, 15]} only. Thus, the rows and
	 * columns of the matrix are in the range {@code [0, n-1]} representing
	 * vertices. The elements of the matrix are 1 if the edge exists in the
	 * graph and 0 otherwise. Since the graph is undirected, the matrix is
	 * symmetric and contains 2m 1’s.
	 */
	public static class Graph {

		/**
		 * An adjacency matrix representation of this graph
		 */
		private int[][] adjacencyMatrix;

		/*
		 * You are free to add constructors, but the empty constructor is the
		 * only one invoked during marking.
		 */
		public Graph() {
			adjacencyMatrix = new int [15][15];
			for (int col = 0; col < 15; col++)
			{
				for (int row = 0; row < 15; row++)
				{
					adjacencyMatrix[row][col] = -1;
				}
			}
		}

		public void populate_random(int a, int b)
		{
			int c = 0;
			int rCol = 0;
			int rRow = 0;
			Random random = new Random();
			while (c < b)
			{
				rRow = random.nextInt(a);
				rCol = random.nextInt(a);
				if (adjacencyMatrix[rRow][rCol] != 1)
				{
					adjacencyMatrix[rRow][rCol] = 1;
					c++;
				}
			}
			System.out.println("populate_random: Added" + " " + c + " "+ "total number of edges into matrix");
		}
		
		//print the matrix to the console
		public void printer()
		{
			for (int col = 0; col < adjacencyMatrix.length; col++)
			{
				for (int row = 0; row < adjacencyMatrix[col].length; row++)
				{
					System.out.print(adjacencyMatrix[row][col]);
					System.out.print(" ");
				}
				System.out.println();
			}
			System.out.println();
		}
		
		/**
		 * Generate a random graph as specified in the assignment statement.
		 * 
		 * @param n
		 *            The size of the graph
		 * @param density
		 *            The density of the graph
		 */
		public void generate(int n, int density) {
			//creates matrix and fills
			for (int col = 0; col < n; col++)
			{
				for (int row = 0; row < n; row++)
				{
					adjacencyMatrix[row][col] = 0;
				}
			}
			//populate graph size n
			int m;
			if (density == 1)
			{
				m = (7*n)/5;
				populate_random(n, m);
			}
			else if (density == 2)
			{
				m = (n*n)/4;
				populate_random(n, m);
			}
			else if (density == 3)
			{
				m = (2*(n*n))/5;
				populate_random(n, m);
			}
			else
			{
				System.out.println("density 1 to 3 only!");
			}
		}

		/**
		 * Reads an adjacency matrix from the specified file, and updates this
		 * graph's data. For the file structure please refer to the sample input
		 * file {@code testadjmat.txt}).
		 * 
		 * @param file
		 *            The input file
		 * @throws IOException
		 *             If something bad happens while reading the input file.
		 */
		public void read(String file) throws IOException {
			System.out.println("Reading a file in");
			try
			{
				int row = 0;
				int col = 0;
				Scanner in = new Scanner(new FileReader(file));
				while (in.hasNextLine())
				{
					String temp = in.nextLine();
					Scanner t = new Scanner(temp);
					while (t.hasNextInt())
					{
						int to = t.nextInt();
						adjacencyMatrix[row][col] = to;
						if(col < adjacencyMatrix.length-1)
						{
							col++;
						}
					}
					col = 0;
					row++;
				}
			}
			catch (IOException io)
			{
				System.out.println("an error has occured");
			}
		}

		/**
		 * Writes the adjacency matrix representing this graph in the specified
		 * file.
		 * 
		 * @param file
		 *            The path of the output file
		 * @throws IOException
		 *             If something bad happens while writing the file.
		 */
		public void write(String file) throws IOException {	
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for (int i = 0; adjacencyMatrix[i][0] != -1; i++)
			{
				for (int j = 0; adjacencyMatrix[i][j] != -1; j++)
				{
					out.write(String.valueOf(adjacencyMatrix[j][i]));
				}
				out.newLine();
			}
			out.append("PLEASE CHECK THE TERMINAL FOR THE FULL TEST.");
			out.close();
		}

		/**
		 * @return an adjacency matrix representation of this graph
		 */
		public int[][] getAdjacencyMatrix() {
			return this.adjacencyMatrix;
		}

		/**
		 * Updates this graph's adjacency matrix
		 * 
		 * @param m
		 *            The adjacency matrix representing the new graph
		 */
		public void setAdjacencyMatrix(int[][] m) {
			this.adjacencyMatrix = m;
		}

	private int[] visited;
	private int[] id;
	private int[] parent;
	private int count;
	private int CC;
	private boolean hasCycle;
	
		//finds the number of nodes in the graph
		public void nodes()
		{
			for (int i = 0; i < adjacencyMatrix[0].length; i++)
			{
				if (adjacencyMatrix[0][i] != -1)
				{
					count++;
				}
			}
		}
	
	/**
	 * Traverses the given graph starting at the specified vertex, using the
	 * depth first search graph traversal algorithm.
	 * <p>
	 * <b>NOTICE</b>: adjacent vertices must be visited in strictly increasing
	 * order (for automated marking)
	 * 
	 * @param graph
	 *            The graph to traverse
	 * @param vertex
	 *            The starting vertex (as per its position in the adjacency
	 *            matrix)
	 * @return a vector R of n elements where R[j] is 1 if vertex j can be
	 *         reached from {@code vertex} and 0 otherwise
	 */
	public int[] reach(Graph graph, int vertex) {
		Stack<Integer> stack = new Stack<Integer>();
		dfs(vertex);
		visited = new int [count];
		id = new int [count];
		parent = new int [count];
		return visited;
	}
	
	public void dfs (int vertex)
	{
		Stack <Integer> stack = new Stack <Integer>();
		int pointer = 0;
		int parentIndex = vertex;
		stack.push(vertex);			
		while (!stack.empty())
		{
			pointer = stack.pop();
			if (visited[pointer] == 0)
			{
				id[pointer] = CC;
				visited[pointer] = 1;
				for (int i=count-1 ; i >= 0 ; i--)
				{
					if (adjacencyMatrix[pointer][i] == 1 && visited[i] == 0)
					{
						stack.push(i);
						parent[i] = pointer;
					}
					if ((parent[pointer] != i) && (adjacencyMatrix[pointer][i] == 1 && visited[i] == 1))
					{
						hasCycle = true;
					}
				}
			}
		}
	}
	
	/**
	 * Computes the number connected components of a given graph.
	 * <p>
	 * <b>NOTICE</b>: adjacent vertices must be visited in strictly increasing
	 * order (for automated marking)
	 * 
	 * @param graph
	 *            The graph
	 * @return The number of connected component in {@code graph}
	 */
	public int connectedComponents(Graph graph) {
		visited = new int [count];
		id = new int [count];
		parent = new int [count];
		for (int i = count-1; i>=0 ; i--)
		{
			if (id[i] == 0)
			{
				CC ++;
				dfs(i);
			}
		}
		return CC;	
	}


	/**
	 * Determines whether a given graph contains at least one cycle.
	 * <p>
	 * <b>NOTICE</b>: adjacent vertices must be visited in strictly increasing
	 * order (for automated marking)
	 * 
	 * @param graph
	 *            The graph
	 * @return whether or not {@code graph} contains at least one cycle
	 */
	public boolean hasCycle(Graph graph) {
		hasCycle = false;
		visited = new int [count];
		id = new int [count];
		parent = new int [count];
		for (int i = 0 ; i <= count-1; i++)
		{
			if (id[i] == 0){
				CC ++;
				dfs(i);
			}
		}
		return hasCycle;
		
	}


	/**
	 * Computes the pre-order for each vertex in the given graph.
	 * Empty spots in the array, if any, are to be filled with -1.
	 * <p>
	 * <b>NOTICE</b>: adjacent vertices must be visited in strictly increasing
	 * order (for automated marking)
	 * 
	 * @param graph
	 *            The graph
	 * @return a vector R of n elements of each vertex, representing the pre-order of
	 *         {@code graph}
	 */
	public int[][] preOrder(Graph graph) {
		throw new UnsupportedOperationException("This method has not been implemented yet.");
	}

	/**
	 * Computes the post-order for each vertex in the given graph.
	 * Empty spots in the array, if any, are to be filled with -1.
	 * <p>
	 * <b>NOTICE</b>: adjacent vertices must be visited in strictly increasing
	 * order (for automated marking)
	 * 
	 * @param graph
	 *            The graph
	 * @return a vector R of n elements of each vertex, representing the post-order of
	 *         {@code graph}
	 */
	public int[][] postOrder(Graph graph) {
		throw new UnsupportedOperationException("This method has not been implemented yet.");
	}

	/**
	 * test and exercise the algorithms and data structures developed for the
	 * first five parts of this assignment extensively. The output generated by
	 * this method must convince the marker that the algorithms and data
	 * structures are implemented as specified. For example:
	 * <ul>
	 * <li>Generate graphs of different sizes and densities
	 * <li>Test the algorithms for different graphs
	 * <li>Test your algorithms using the sample input file testadjmat.txt
	 * 
	 * @throws Exception
	 *             if something bad happens!
	 */
	public void test() throws Exception {
		System.out.println("TESTING HAS BEGUN");
		System.out.println();
		Graph g225 = new Graph ();
		
		//Testing CYCLES
		try {
			g225.read("testadjmat.txt");
		} catch (IOException IO) {
			System.out.println("Error reading file");
		}
		int connect = g225.connectedComponents(g225);
		System.out.println("Number of connected components:" + connect);
		boolean cycle = g225.hasCycle(g225);
		System.out.println("Graph containes cycles:" + cycle);
		try {
			g225.write("cycles.txt");
		} catch (IOException io) {
			System.out.println("An error has ocurred while writting in the file");
		}
		g225.printer();
		
		//Testing CONNECTED COMPONENTS
		try {
			g225.read("testadjmat.txt");
		} catch (IOException IO) {
			System.out.println("Error reading file");
		}
		connect = g225.connectedComponents(g225);
		System.out.println("Number of connected components:" + connect);
		try {
			g225.write("connectedComponents.txt");
		} catch (IOException io) {
			System.out.println("An error has ocurred while writting in the file");
		}
		g225.printer();
		
		//Testing READ
		try {
			g225.read("testadjmat.txt");
		} catch (IOException io){
			System.out.println("Error reading file");
		}
		try {
			g225.write("testadjmat_read.txt");
		} catch (IOException io) {
			System.out.println("An error has ocurred while writting in the file");
		}
		cycle = g225.hasCycle(g225);
		System.out.println("Graph containes cycles:" + cycle);
		g225.printer();
		
		//Testing GENERATE/WRITE, size 10, density 1. EXPECTED: 14 edges
		System.out.println("expected: 14 edges");
		g225.generate(10,1);
		cycle = g225.hasCycle(g225);
		System.out.println("Graph containes cycles:" + cycle);
		g225.printer();
		try{
			g225.write("density1.txt");
		} catch (IOException io){
			System.out.println("An error has ocurred while writting in the file");
		}
		
		
		//Testing GENERATE/WRITE, size 10, density 2. EXPECTED: 25 edges
		System.out.println("expected: 25 edges");
		g225.generate(10,2);
		cycle = g225.hasCycle(g225);
		System.out.println("Graph containes cycles:" + cycle);
		g225.printer();
		try{
			g225.write("density2.txt");
		} catch (IOException io){
			System.out.println("An error has ocurred while writting in the file");
		}
		
		//Testing GENERATE/WRITE, size 10, density 3. EXPECTED: 40 edges
		System.out.println("expected: 40 edges");
		g225.generate(10,3);
		cycle = g225.hasCycle(g225);
		System.out.println("Graph containes cycles:" + cycle);
		g225.printer();
		try{
			g225.write("density3.txt");
		} catch (IOException io){
			System.out.println("An error has ocurred while writting in the file");
		}
	}
	
	}
	
	public static void main(String[] args) throws Exception
	{
		Graph g = new Graph ();
		try
		{
			g.test();
		}
		catch (Exception e)
		{
			System.out.println("something bad happened");
		}
	}

}
