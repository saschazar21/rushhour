package AStar;
/**
 * This class contains a static method for computing the branching
 * factor, as described in Russell and Norvig (page 106).
 */
public class BranchingFactor {

    /** The precision with which the answer is to be computed. */
    private static final double PRECISION = 0.00001;

    /**
     * This static method computes the branching factor for a search
     * in which <tt>num_expanded</tt> nodes were expanded, and a
     * solution at the given <tt>depth</tt> was discovered.  Note that
     * <tt>depth</tt> must be nonnegative, and <tt>num_expanded</tt>
     * must be at least <tt>depth</tt> plus one (otherwise an
     * <tt>IllegalArgumentException</tt> will be thrown).
     */
    public static double compute(int num_expanded,
				 int depth) {
	if (num_expanded < depth + 1)
	    throw new IllegalArgumentException("num_expanded must be at least depth + 1");

	if (depth < 0)
	    throw new IllegalArgumentException("depth must be nonnegative");

	double n = num_expanded;

	double b_lo = 1.;
	double f_lo = 1. + depth;
	double b_hi = b_lo;
	double f_hi = f_lo;

	while (f_hi < n) {
	    b_hi *= 2.;
	    f_hi = geo_sum(b_hi, depth);
	}

	while (b_hi - b_lo > PRECISION) {
	    double b_mid = (b_hi + b_lo) * 0.5;
	    double f_mid = geo_sum(b_mid, depth);

	    if (f_mid > n) {
		b_hi = b_mid;
		f_hi = f_mid;
	    } else {
		b_lo = b_mid;
		f_lo = f_mid;
	    }
	}

	return b_lo;
    }

    private static double geo_sum(double b, int d) {
	double s = 1.;

	for (int i = 0; i < d; i++) {
	    s = s * b + 1.;
	}

	return s;
    }

}
