# COMP250-As2
COMP250-As2;

Methods implemented:

1.SLinkedList.deepClone

Returns a deep copy of the linked list object. It should step through the linked list and make a copy of each term. The runtime of this method should be 𝑂(𝑛).

2.Polynomial.addTerm 

Add a new term to the polynomial. Use the methods provided by the SLinkedList class. runtime of this method should be 𝑂(𝑛).

3.Polynomial.add 

This is a static method that adds two polynomials and returns a new polynomial as result. The runtime of this method should be 𝑂(𝑛 1 + 𝑛 2 ) where 𝑛 1 , 𝑛 2 are the number of terms in the two polynomialsbeing added.

4.Polynomial.eval 

The polynomial object evaluates itself for a given value of 𝑥 using Horner’s method. The variable 𝑥 is of BigInteger data type, as mentioned earlier. Horner’s method greatly speeds up the evaluation for exponents with many terms. It does so by not having to re-compute the 𝑥 𝑖 fresh for each term. Note that you should not use Term.eval() method to evaluate a particular term. That method is provided for you only to help with testing, namely to ensure that your implementation of Horner’s method is correct.
