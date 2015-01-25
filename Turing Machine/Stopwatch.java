import java.util.HashMap;

/**
 * Stopwatch class, taken from: http://introcs.cs.princeton.edu/java/32class/Stopwatch.java.html
 * 
 *
 */
public class Stopwatch { 

	    private final long start;

	    public Stopwatch() {
	        start = System.currentTimeMillis();
	    } 

	    // return time (in seconds) since this object was created
	    public double elapsedTime() {
	        long now = System.currentTimeMillis();
	        return (now - start) / 1000.0;
	    }
	    
	    public static void main(String[] Args)
	    {
	    	HashMap<Integer, String> tes = new HashMap<Integer, String>();
	    	tes.put(0, "halo0");
	    	tes.put(1, "halo1");
	    	tes.put(2, "halo2");
	    	tes.put(3, "halo3");
	    	tes.put(2, "halo4");
	    	
	    	for(int i = 0; i< 5; i++)
	    	{
	    		System.out.println(i + " value: " + tes.get(i));
	    	}
	    	
	    }
	}
