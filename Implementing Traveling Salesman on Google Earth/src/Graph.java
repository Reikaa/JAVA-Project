
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;



public class Graph {
	List<Node> V;
	int numOfVertices;
	private double[][] adjMatrix;
	private String[] crimeRecords;
	
	/**
	 * Constructor for graph
	 * @param a start index from the CSV file
	 * @param b end index from the CSV file
	 * @throws IOException
	 */
	public Graph(int a , int b) throws IOException
	{
		V = new ArrayList<Node>();
//		listHamiltonian = new ArrayList<ArrayList<Node>>();
		readCSV(a,b);
		
	}
	
	/**
	 * Method to read the CSV files and extract it into our data
	 * @param start : beginning index of the record
	 * @param end	: end index of the record
	 * @throws IOException
	 */
	public void readCSV(int start, int end) throws IOException
	{
		numOfVertices = end-start + 1;
		this.adjMatrix = new double[numOfVertices][numOfVertices];
		this.crimeRecords = new String[numOfVertices];
		Path path = Paths.get(System.getProperty("user.dir") + "/CrimeLatLonXY1990.csv");
		BufferedReader reader = null;
		int n = 0; // counter for index of the data
		int counter = 0;
		String line = "";
		reader = new BufferedReader(new FileReader(path.toString()));
		line = reader.readLine();
		LinkedList<double[]> list = new LinkedList<double[]>();
		
		System.out.println("Crime Records Processed: \n");
		
		try{
			while((line = reader.readLine()) != null && (n <= end))
			{
				
				//use comma as separator
				if(n>= start)
				{
					String[] crimeData = line.split(",");
					Node tempNode = new Node(counter);
					tempNode.setRecords(crimeData);
					V.add(tempNode);
					this.crimeRecords[n-start] = line;
					double[] data = new double[2];
					data[0] = Double.parseDouble(crimeData[0]); //x
					data[1] = Double.parseDouble(crimeData[1]); //y
					System.out.println(line);
					list.add(data);
					counter++;
				}
				n++;
			}
		} catch(FileNotFoundException e)
		{
			e.printStackTrace();
		} catch(IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if( reader !=null)
			{
				try
				{
					reader.close();
				} catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		for(int i=0 ; i<numOfVertices ; i++)
		{
			for(int j=0 ; j<numOfVertices ; j++)
			{
				double[] node1 = list.get(i);
				double[] node2 = list.get(j);
				this.adjMatrix[i][j] = Math.sqrt(Math.pow((node2[0]-node1[0]),2) + Math.pow((node2[1]-node1[1]),2));
//				System.out.println("weight between node " + i + " & " + j + " = " + adjMatrix[i][j]);				
			}
			
			List<Node> temp = new ArrayList<Node>(V);
			temp.remove(i);
			V.get(i).neighbors = temp;
			
		}
			
		
	}
	
	/**
	 * Function to generate a Prim Tree, 
	 * @param root - number of index of nodes that will be the root of the tree
	 * @return Object of PrimTree 
	 */
	public PrimTree performPrim(int root)
	{
		Node r = V.get(root);
		List<Node> Q = new ArrayList<Node>();
		List<Node> A = new ArrayList<Node>();
		for (Node u: this.V)
		{
			u.setKey(Integer.MAX_VALUE);
			u.setParent(null);
			Q.add(u);
		}
		
		// comparator for sorting Q
		Comparator<Node> newComparator = new Comparator<Node>(){
			@Override
			public int compare(Node o1, Node o2)
			{
				if(o1.key < o2.key){return -1;}
				if(o1.key == o2.key){return 0;}
				return 1;
			}
		};
		
		while(!Q.isEmpty())
		{
			// return the minimum value
			Collections.sort(Q, newComparator);
			Node u = Q.get(0);
			Q.remove(0);
			
//			System.out.println("u: " + u.ID);
			for(Node v: u.neighbors)
			{
				double w = this.adjMatrix[u.ID][v.ID];
				if(Q.contains(v) && w <= v.key)
				{
					v.setParent(u);
					v.setKey(w);
					
					if(!A.contains(v))
					{
						A.add(v);
//						System.out.println(v.ID);
					}
					
				}
			}
		
		}
		
		// add children 
		Collections.sort(A, newComparator);
		for(Node z: A)
		{
			z.getParent().children.add(z);
		}
		PrimTree tree = new PrimTree(r, A, this.adjMatrix);
		
		return tree;		
		
	}
	
	
	
	/**
	 * Method to convert from list of vertices into string, separated by comma
	 * @param V
	 * @return
	 */
	public String ListOfVToString(List<Node> V)
	{
		String res = "";
		for(Node u: V)
		{
			res += u.ID;// + ",";
		}
		
		return res;
	}
	
	/**
	 * Method to convert string to total cost of the adjacency matrix of the graph
	 * @param V
	 * @return
	 */
	public double pathCost(String V)
	{
		//String[] splitted = V.split(",");
		double Cost = 0.0;
		
		for(int i = 1; i< V.length(); i++)
		{
			int node1 = Character.getNumericValue(V.charAt(i-1));
			int node2 = Character.getNumericValue(V.charAt(i));
			Cost += adjMatrix[node1][node2];
		}
		
		Cost += adjMatrix[Character.getNumericValue(V.charAt(V.length()-1))][Character.getNumericValue(V.charAt(0))];
		return Cost/5280.10982628;
	}
	
	
	
	public static ArrayList<String> permutation(String s) {
	    // The result
	    ArrayList<String> res = new ArrayList<String>();
	    // If input string's length is 1, return {s}
	    if (s.length() == 1) {
	        res.add(s);
	    } else if (s.length() > 1) {
	        int lastIndex = s.length() - 1;
	        // Find out the last character
	        String last = s.substring(lastIndex);
	        // Rest of the string
	        String rest = s.substring(0, lastIndex);
	        // Perform permutation on the rest string and
	        // merge with the last character
	        res = merge(permutation(rest), last);
	    }
	    return res;
	}

	/**
	 * @param list a result of permutation, e.g. {"ab", "ba"}
	 * @param c    the last character
	 * @return     a merged new list, e.g. {"cab", "acb" ... }
	 */
	public static ArrayList<String> merge(ArrayList<String> list, String c) {
	    ArrayList<String> res = new ArrayList<String>();
	    // Loop through all the string in the list
	    for (String s : list) {
	        // For each string, insert the last character to all possible postions
	        // and add them to the new list
	        for (int i = 0; i <= s.length(); ++i) {
	            String ps = new StringBuffer(s).insert(i, c).toString();
	            res.add(ps);
	        }
	    }
	    return res;
	}
	
	/**
	 * Method to get a minimum path from list of string of Vertices.
	 * @param V
	 * @return
	 */
	
	public String getMinimumPath(ArrayList<String> V)
	{
		double[] pathList = new double[V.size()];
		double max = Double.MAX_VALUE;
		int indexOfMinimumPath = Integer.MAX_VALUE;
		double eps = 0.000000000000001;
		for(int i =0; i< V.size(); i++)
		{
			pathList[i] = pathCost(V.get(i));
			if((pathList[i] - max)<eps)
			{
				max = pathList[i];
				indexOfMinimumPath = i;
			}
		}
		
		return V.get(indexOfMinimumPath);
		
	}
	
	public String Prim(int rootID)
	{
		PrimTree tree = this.performPrim(rootID);
		tree.hamiltonianCycle();
		String cycle = tree.hamiltonianString();
		String print = "";
		
		for(int i =0; i<cycle.length()-1; i++)
		{
			print = print + cycle.charAt(i) + ", ";
		}
		print = print + cycle.charAt(cycle.length()-1);
				
				
		System.out.println("\nHamiltonian Cycle (not necessarily optimum): " + print );
		DecimalFormat df = new DecimalFormat("#.##");
		System.out.println("Total Cost: " + df.format(tree.computeCost()) + " miles");
		return cycle;
	}
	
	public String bruteForce()
	{
		String VString = this.ListOfVToString(this.V);
		ArrayList<String> listOfPath = permutation(VString);

		String minPath = getMinimumPath(listOfPath);
		String HamiltonCycle = "";
		
		for(int i=0 ; i<minPath.length(); i++)
		{
			HamiltonCycle = HamiltonCycle + minPath.charAt(i) + ",";
		}
		
		HamiltonCycle = HamiltonCycle + minPath.charAt(0);
		
		// Somehow I cannot get the same path as Mike did, because more than 1 path that has the same length
		System.out.println("\nHamiltonan Cycle (minimal): " + HamiltonCycle);
		System.out.println("Length of Cycle : " + pathCost(minPath) + " miles");
		
		minPath = minPath + minPath.charAt(0);
		return minPath;
		
	}
	
	public ArrayList<String> coordinates(String cycle)
	{
		ArrayList<String> res = new ArrayList<String>();
		System.out.println("Cycle is: " + cycle);
		for(int u = 0; u<cycle.length(); u++)
		{
			int index = Character.getNumericValue(cycle.charAt(u));
			String[] crimeData = this.V.get(index).records;
			int length = crimeData.length;
			String coord = crimeData[length-1] + "," + crimeData[length-2] + ",0.000000";
			res.add(coord);
		}
		
		return res;
	}

	
}
