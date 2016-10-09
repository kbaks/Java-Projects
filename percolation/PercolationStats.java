import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private static int n;								// keep command-line argument for grid size 
    private static int trials;							// keep command-line argument for number of trials
    private static double sampleMean;			// keep sampleMean to use it below
    private static double sampleStdDev;		// keep sampleStdDev to use it below
    private static double confFraction;			// keep confFraction to use it below
    private double[] thresholdSample;			// keep instance's results for every trial

   
    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {   
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException
                ("arguments must be non-zero positive"); }
        else {
            int n2 = n*n;
            int ri; int rj;
            thresholdSample = new double[trials];
            for(int j = 0; j < trials; j++) {
                int openSites = 0;					// keep number of open sites
                Percolation trial = new Percolation(n);
                // System.out.println("trial created");
                // System.out.println("trial percolates?" + trial.percolates());
                while(!trial.percolates()) {		// open sites until the system percolates
                    // System.out.println("entered!");
                    do {
                        ri = StdRandom.uniform(1, n+1);		// choose randomly element's row
                        rj = StdRandom.uniform(1, n+1);		// and column
                        // System.out.println("try (" + ri + "," + rj + ")");
                    } while (trial.isOpen(ri, rj));				// if site already open repeat
                    trial.open(ri, rj);					// open site
                    openSites++;						// increase counter
                    // System.out.println("There are " + openSites + "open sites");
                }
                thresholdSample[j] = (double) openSites/n2;		// compute threshold value and store it
                // System.out.println("The threshold estimation is " + thresholdSample[j] + " for trial " + j);
            }
        }       
    }
    public double mean()                          // sample mean of percolation threshold
    { return StdStats.mean(thresholdSample); }
    public double stddev()                        // sample standard deviation of percolation threshold
    { return StdStats.stddev(thresholdSample); }   
    
    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    { return sampleMean-confFraction; }
    public double confidenceHi()                  // high endpoint of 95% confidence interval
    { return sampleMean+confFraction; }
    private double confF()
    { return (1.96*sampleStdDev/Math.sqrt(trials)); }
   
    public static void main(String[] args)    // test client (described below)
    {
       Stopwatch watch = new Stopwatch();
       n = Integer.parseInt(args[0]);
       trials = Integer.parseInt(args[1]);
       PercolationStats stats = new PercolationStats(n, trials);
       // System.out.println("time to complete: watch = " + watch.elapsedTime());
       sampleMean = stats.mean();
       sampleStdDev = stats.stddev();
       confFraction = stats.confF();
       System.out.println("mean =  " + sampleMean);
       System.out.println("stddev =  " + sampleStdDev);
       System.out.println("95% confidence interval =  " + 
                          stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}