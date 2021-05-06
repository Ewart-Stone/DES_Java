/*
 * This implements a class DES3 which shows the encryption algorithm this is the forth version of DES in this permutation P is missing from F
 function in all rounds.
 
 * Author - Ewart Stone(c3350508), Ankur(c3347695) 
 * Date - 07/06/2021 
 * This file is used in conjuction with Assignment 2 for COMP3260

*/

public class DES3 extends DES0							// public class DES3 which  extends using DES0
{
  public DES3(String text, String key)						// we got two text and key 
  {
    super(text, key);
  }

  @Override
  public String[] round(String subKey, String leftHalf, String rightHalf)
  {
    String leftOut = rightHalf;							// the left out we get is equalls to the right half
		String rightOut = "";						//from here it is showing what are the F Functions are working in all rounds 
		
		rightOut = expansionPermutationE(rightHalf);
		rightOut = xorRoundKey(rightOut, subKey);
		rightOut = sBox(rightOut);
		rightOut = xorHalves(leftHalf, rightOut);
		
		String[] out = {leftOut, rightOut};				//getting the final result 
		return out;
  }
}
