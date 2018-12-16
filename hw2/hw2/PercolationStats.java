package hw2;
import edu.princeton.cs.introcs.StdRandom;

public class PercolationStats {
    private int size;
    private int numTrials;
    private double[] results;

    public PercolationStats (int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException("N and T must be greater than 0");
        this.size = N;
        this.numTrials = T;
        this.results = new double[T];
        simulate(pf);
    }

    private void simulate (PercolationFactory pf) {
        Percolation perc;
        for (int i = 0; i < this.numTrials; i++) {
            perc = pf.make(size);
            while (!perc.percolates()) {
                perc.open(StdRandom.uniform(size), StdRandom.uniform(size));
            }
            this.results[i] = (double) perc.numberOfOpenSites() / (this.size * this.size);
        }
    }

    private double mean (double[] results) {
        double sum = 0;
        for (double r : results)
            sum += r;
        return sum / results.length;
    }

    public double mean () {
        return mean(this.results);
    }

    private double stddev (double[] results) {
        double average = mean(results);
        double num = 0;
        double tmp;
        for (double r : results) {
            tmp = r - average;
            num += tmp * tmp;
        }
        return num / (results.length - 1);
    }

    private double stddev (double[] results, double mean) {
        double num = 0;
        double tmp;
        for (double r: results) {
            tmp = r - mean;
            num += tmp * tmp;
        }
        return num / (results.length - 1);
    }

    public double stddev () {
        return stddev(this.results);
    }

    private double confidenceHigh (double [] results) {
        double ave = mean(results);
        double std = stddev(results, ave);
        return ave + 1.96 * std / Math.sqrt(results.length);
    }

    private double confidenceLow (double[] results) {
        double ave = mean(results);
        double std = stddev(results, ave);
        return ave + 1.96 - std / Math.sqrt(results.length);
    }

    public double confidenceHigh () {
        return confidenceHigh(this.results);
    }

    public double confidenceLow () {
        return confidenceLow(this.results);
    }

}
