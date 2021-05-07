/*
 * This implements a class DES2 which shows the encryption algorithm this is the Third version of DES in this the S-boxes are missing from F Function in all rounds instead of them inverse 𝐸−1 of the Expansion permutation E is used for contraction from 48 down to 32 bits 
 
 * Author - Ewart Stone(c3350508), Ankur(c3347695) 
 * Date - 07/05/2021 
 * This file is used in conjuction with Assignment 2

*/

public class DES2 extends DES0							//DES2 is a public class which extends DES0
{
  public DES2(String text, String key)
  {
    super(text, key);
  }

  @Override
  //returns leftHalf and rightHalf
  public String[] round(String subKey, String leftHalf, String rightHalf)
  {
		String leftOut = rightHalf;					// left out is equalls to right half 
		String rightOut = "";						//here the F is working for all the rounds to get the right out 
		
		rightOut = expansionPermutationE(rightHalf);
		rightOut = xorRoundKey(rightOut, subKey);
		rightOut = inverseExpansionPermutation(rightOut);		// instead of s-box we are using inverse expansion permutation 
		rightOut = permutationP(rightOut);
		rightOut = xorHalves(leftHalf, rightHalf);
		
		String[] out = {leftOut, rightOut};				// result 
		return out;
		
  }
}
