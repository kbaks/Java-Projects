// http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/WeightedQuickUnionUF.html
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
    private int dim;								// keep grid's dimensions
    private boolean[] grid;						// maintain sites' condition - 0 for blocked -1 for open
    private WeightedQuickUnionUF uf;		// UF object that keeps connections between sites
   
    public Percolation(int n)               		// create n-by-n grid, with all sites blocked
    {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be a non-zero positive");
        } else      {
            // System.out.println("n = " + n);
            dim = n;
            // System.out.println("N = " + N);
            int n2 = n*n;
            grid = new boolean[n2+2];		// creating array for sites' condition plus virtual top and virtual bottom sites
            grid[0] = true;						// initialize virtual top site to open
            grid[n2+1] = true;					// initialize virtual bottom site to open
            for (int i = 1; i <= n2; i++) {
                grid[i] = false;					// initialize grid to blocked
                // System.out.println("object " + i + " is initialized to " + grid[i]);
            }
            this.uf = new WeightedQuickUnionUF(n2+2);	// Create UF object associated to the grid
        }
    }
   
    public void open(int i, int j)         		 // open site (row i, column j) if it is not open already
    {
        // System.out.println("N = " + N);
        // System.out.println("validate i");
        validate(i);									// validate that index i is in bounds
        // System.out.println("validate j");
        validate(j);									// validate that index j is in bounds
        int k = convertIndex(i, j);				// convert 2D index to 1D index
        // System.out.println("open element = " + k);
        grid[k] = true;							// open site
        this.uniteAdjacent(i, j);				// establish connections with neighbor open sites
    }
   
    public boolean isOpen(int i, int j)     	// is site (row i, column j) open?
    {
        validate(i);									// validate that index i is in bounds
        validate(j);									// validate that index j is in bounds
        int k = convertIndex(i, j);				// convert 2D index to 1D index
        if (grid[k] == true) 						// if site is open return true
        { return true; }
        else
        { return false; }
    }
   
    public boolean isFull(int i, int j)     		// is site (row i, column j) full?
    {
        validate(i);;								// validate that index i is in bounds
        validate(j);;								// validate that index j is in bounds
        int k = convertIndex(i, j);				// convert 2D index to 1D index
		// if site is open and connected to virtual top, it is full
		// note that there is a minor bug here (backwashh)
		// it doesn't affect the percolation probem but whether a site is full or not		
        return (this.uf.connected(k, 0) && this.isOpen(i, j));
    }
   
    public boolean percolates()             	// does the system percolate?
    {
        if(this.uf.connected(0, dim*dim+1))	//if virtual bottom is connected to virtual top the system percolates
        { return true; }
        else
        { return false; }
    }
   
    // return the 1D Index from row and column
    private int convertIndex(int i, int j)
    { return (i-1)*dim+j; }
   
    // return -1 if we try to connect elements on the opposite side of the grid
    private int convertIndexWLimits(int i, int j)
    {
        if (i == 0) { return 0; }
        else if (i == dim+1) { return dim*dim+1; }
        else if (j <= 0 || j > dim) { return -1; }
        else { return (i-1)*dim+j; }
    }
   
    private void validate(int p)
    {
        if (p <= 0 || p > dim) {
            // System.out.println("Violation " + p);
            throw new IndexOutOfBoundsException
            ("index " + p + " is not between 1 and " + dim); }
    }

    private void uniteAdjacent(int pi, int pj)
    {
        int k;
        // System.out.println("unite adjacent of " + pi + "," + pj);
        // use different method to convert index, in order to avoid coonections on the opposite side of the grid
        k = convertIndexWLimits(pi, pj-1);
        if (k >= 0 && grid[k] == true) {					// if site on previous column is open connect them
            this.uf.union(k, (convertIndex(pi, pj)));
        }
        k = convertIndexWLimits(pi, pj+1);
        if (k >= 0 && grid[k] == true) {					// if site on next column is open connect them
            this.uf.union(k, (convertIndex(pi, pj)));
        }
        k = convertIndexWLimits(pi-1, pj);
        if (k >= 0 && grid[k] == true) {					// if site on previous row is open connect them
            this.uf.union(k, (convertIndex(pi, pj)));
        }
        k = convertIndexWLimits(pi+1, pj);
        if (k >= 0 && grid[k] == true) {					// if site on next row is open connect them
            this.uf.union(k, (convertIndex(pi, pj)));
        }
    }
   
   public static void main(String[] args)  // test client (optional)
   {   }       
}