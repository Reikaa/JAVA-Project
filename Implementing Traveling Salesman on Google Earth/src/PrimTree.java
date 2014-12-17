import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * PrimTree class for the minimum spanning tree
 * Has attributes:
 * - root - root node
 * - list A - list of nodes which is the output of minimum spanning tree algorithm in CLR book
 * - HamCycle - List of nodes which consist the nodes of Hamiltonian Cycle
 * - AdjMatrix - Adjacency Matrix of the tree to compute the distance among each node
 * - totalCost - totalCost of the this tree
 * 
 * @author ardyadipta
 *
 */
public class PrimTree {
	public Node root;
	public List<Node> A;
	List<Node> hamCycle;
	double[][] AdjMatrix;
	double totalCost;
	
	
	/**
	 * 
	 * @param root - define the root of this tree
	 * @param A - define the list A
	 * @param AdjMatrix - define the AdjMatrix from the previous calculated AdjMatrix in graph class
	 */
	public PrimTree(Node root, List<Node> A, double[][] AdjMatrix)
	{
		this.root = root;
		this.A = A;
		hamCycle = new ArrayList<Node>();
		this.AdjMatrix = AdjMatrix;
		totalCost = 0;
	}
	
	
	/**
	 * method to calculate the hamiltonian cycle
	 */
	
	public void hamiltonianCycle()
	{
		Stack<Node> stack = new Stack<Node>();
		stack.push(root);

		
		while(!stack.isEmpty())
		{
			Node current = stack.pop();
			hamCycle.add(current);
			List<Node> children = current.children;
			for(Node u: children)
			{
				stack.push(u);
			}
			
		}
		
		hamCycle.add(root);

	}
	
	public String hamiltonianString()
	{
		String preOrder = "";
		for(Node i: hamCycle )
		{
			preOrder = preOrder + i.ID;
			
		}
		
		return preOrder;
	}
	
	public double computeCost()
	{
		totalCost = 0;
		Node prev = hamCycle.get(0);
		for(Node u: hamCycle)
		{
//			System.out.println(prev.ID + " to " + u.ID +" = " + AdjMatrix[prev.ID][u.ID]);
			totalCost += this.AdjMatrix[prev.ID][u.ID] ;
			prev = u;
		}
		
		// convert to miles
		totalCost = totalCost /5279;
		return totalCost;
	}

}
