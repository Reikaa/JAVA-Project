import java.util.ArrayList;
import java.util.List;

/**
 * Class Node for graph. has attributes:
 * - key - minimum weight from its parent
 * - ID - just an ID to refer to the index of this node the list
 * - children - list of nodes that consist of children of the node in the tree
 * - neighbors - list of nodes that connect to this node
 * - records - records of the crime records from the CSV
 * @author ardyadipta
 *
 */
public class Node {

	public double key;
	private Node parent;
	public int ID;
	public List<Node> children;
	public List<Node> neighbors;
	public String[] records;
	
	/**
	 * Constructor of the node
	 * @param ID - ID will be the same as the index number of the node in the list
	 */
	public Node(int ID)
	{
		key = Integer.MAX_VALUE;
		parent = null;
		this.ID = ID;
		children = new ArrayList<Node>();
		neighbors = new ArrayList<Node>();
	}
	
	/**
	 * method to set parent node
	 * @param p - node to set the parent node
	 */
	public void setParent(Node p)
	{
		this.parent = p;
	}
	
	
	/**
	 * method to tell the parent of this node
	 * @return parent
	 */
	public Node getParent()
	{
		return parent;
	}
	
	/**
	 * Method to set the key of this node
	 * @param Key - 
	 */
	public void setKey(double Key)
	{
		this.key = Key;
	}
	
	/**
	 * method to set the crime data of the node based on the input from the CSV file
	 * @param records
	 */
	public void setRecords(String[] records)
	{
		this.records = records;
	}
}
