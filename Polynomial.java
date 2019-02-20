// Assignment 2:
// Sodelwyn Yit
// ID: 260639778

package assignments2018.a2template;

import java.math.BigInteger;


public class Polynomial {
    private SLinkedList<Term> polynomial;
    private BigInteger x;

    public int size() {
        return polynomial.size();
    }

    private Polynomial(SLinkedList<Term> p) {
        polynomial = p;
    }

    public Polynomial() {
        polynomial = new SLinkedList<Term>();
    }

    // Returns a deep copy of the object.
    public Polynomial deepClone() {
        return new Polynomial(polynomial.deepClone());
    }

    /*
     * TODO: Add new term to the polynomial. Also ensure the polynomial is
     * in decreasing order of exponent.
     */
    public void addTerm(Term t) {
        // Hint: Notice that the function SLinkedList.get(index) method is O(n),
        // so if this method were to call the get(index)
        // method n times then the method would be O(n^2).
        // Instead, use a Java enhanced for loop to iterate through
        // the terms of an SLinkedList.
    	
    	// Check if valid term
    	if (t.getExponent() < 0) return;
    	// addFirst element to polynomial
    	if (polynomial.isEmpty()) polynomial.addFirst(t);
        else {
        	int i = 0;
            for (Term currentTerm : polynomial) {
                // Sum coefficients of terms with same exponent.
                if (t.getExponent() == currentTerm.getExponent()) {
                    BigInteger sum = currentTerm.getCoefficient().add(t.getCoefficient());
                    // Remove terms whose coefficient = 0
                    if (sum.equals(new BigInteger("0"))) {
                        polynomial.remove(i);
                        return;
                    }

                    currentTerm.setCoefficient(sum);
                    return;
                } 
                else if (t.getExponent() > currentTerm.getExponent()){
                        polynomial.add(i, t);
                        return;
                }
                i++;
            }
            // The exponent is smaller than every other exponent
            polynomial.addLast(t);
        }
    }




    public Term getTerm(int index) {
        return polynomial.get(index);
    }
    
    // Helper function that adds every single term to array
    public static void addToArray(Polynomial p, BigInteger[] coefficients) {
    	for (Term currTerm: p.polynomial) {
    		if (currTerm.getExponent() >= 0) {
    		    if (coefficients[currTerm.getExponent()] != null)
                    coefficients[currTerm.getExponent()] = currTerm.getCoefficient().add(coefficients[currTerm.getExponent()]);
                else
                    coefficients[currTerm.getExponent()] = currTerm.getCoefficient();
    		}
    	}
    }
    
    public static Polynomial add(Polynomial p1, Polynomial p2) {
    	// Check if either one of them are empty
    	if (p1.size() == 0)
    		return p2.deepClone();
    	else if (p2.size() == 0)
    		return p1.deepClone();
    	
    	Polynomial sum = new Polynomial();

    	// Get biggest exponent to iterate
    	int biggestExponent = p1.getTerm(0).getExponent();
    	if (p2.getTerm(0).getExponent() > biggestExponent)
    		biggestExponent = p2.getTerm(0).getExponent();

    	BigInteger[] coefficients = new BigInteger[biggestExponent+1];

    	addToArray(p1, coefficients);
    	addToArray(p2, coefficients);
    	
    	for (int i=biggestExponent; i >= 0; i--) {
    	    if (coefficients[i] != null && !coefficients[i].equals(new BigInteger("0"))){
    	        sum.polynomial.addLast(new Term(i,coefficients[i]));
            }
    	}
    	
    	return sum;
    }
    
	//TODO: multiply this polynomial by a given term.
    private void multiplyTerm(Term t) {
    	// If coefficient is 0, then the polynomial is equal to 0
    	if (t.getCoefficient().equals(new BigInteger("0"))) polynomial.clear();
    	for (Term currentTerm: polynomial) {
    		currentTerm.setCoefficient(currentTerm.getCoefficient().multiply(t.getCoefficient()));
    		currentTerm.setExponent(currentTerm.getExponent() + t.getExponent());
    	}
    }

    public static Polynomial multiply(Polynomial p1, Polynomial p2) {
    	Polynomial multiplierPolynomial = p2.deepClone();
    	Polynomial product = new Polynomial();
    	for (Term multiplier: multiplierPolynomial.polynomial) {
    		Polynomial multipliedTerm = p1.deepClone();
    		multipliedTerm.multiplyTerm(multiplier);
    		product = Polynomial.add(product, multipliedTerm);
    	}
    	return product;
    }

    //TODO: evaluate this polynomial.
    // Hint:  The time complexity of eval() must be order O(m),
    // where m is the largest degree of the polynomial. Notice
    // that the function SLinkedList.get(index) method is O(m),
    // so if your eval() method were to call the get(index)
    // method m times then your eval method would be O(m^2).
    // Instead, use a Java enhanced for loop to iterate through
    // the terms of an SLinkedList.

    public BigInteger eval(BigInteger x) {
        this.x = x;
        if (this.polynomial.size() == 0) return new BigInteger("0") ;

        int bigExponent = this.getTerm(0).getExponent();
        BigInteger[] sum = new BigInteger[bigExponent+1];
        BigInteger result = sum[bigExponent];
        //Populating Terms in BigInteger array.
        for (Term currTerm : this.polynomial) {
            if (currTerm.getExponent() >= 0) sum[currTerm.getExponent()] = currTerm.getCoefficient();
        }
        //Iterating through the array, Multiplying by x each iteration. Check for nullcase.
        for (int i = bigExponent-1; i >= 0 ; i--){
            result = result.multiply(x);
            if (sum[i] != null) result = result.add(sum[i]);
        }
        return result;
    }

    // Checks if this polynomial is same as the polynomial in the argument.
    // Used for testing whether two polynomials have same content but occupy disjoint space in memory.
    // Do not change this code, doing so may result in incorrect grades.
    public boolean checkEqual(Polynomial p) {
        // Test for null pointer exceptions!!
        // Clearly two polynomials are not same if they have different number of terms
        if (polynomial == null || p.polynomial == null || size() != p.size())
            return false;

        int index = 0;
        // Simultaneously traverse both this polynomial and argument.
        for (Term term0 : polynomial) {
            // This is inefficient, ideally you'd use iterator for sequential access.
            Term term1 = p.getTerm(index);

            if (term0.getExponent() != term1.getExponent() || // Check if the exponents are not same
                    term0.getCoefficient().compareTo(term1.getCoefficient()) != 0 || // Check if the coefficients are not same
                    term1 == term0) // Check if the both term occupy same memory location.
                return false;

            index++;
        }
        return true;
    }

    // This method blindly adds a term to the end of LinkedList polynomial.
    // Avoid using this method in your implementation as it is only used for testing.
    // Do not change this code, doing so may result in incorrect grades.
    public void addTermLast(Term t) {
        polynomial.addLast(t);
    }

    // This is used for testing multiplyTerm.
    // Do not change this code, doing so may result in incorrect grades.
    public void multiplyTermTest(Term t) {
        multiplyTerm(t);
    }

    @Override
    public String toString() {
        if (polynomial.size() == 0) return "0";
        return polynomial.toString();
    }
}
