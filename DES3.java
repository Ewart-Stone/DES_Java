/*
 * This implements a child class of DES0
 * Overloads round() method to implement DES3 requirements in spec
 * Polymorphism handles altered encryption and decryption
 * Author - Ewart Stone(c3350508), Ankur(c3347695) 
 * Date - 07/05/2021 
 * This file is used in conjuction with Assignment 2
 * 

*/
public class DES3 extends DES0
{
  public DES3(String text, String key)
  {
	//calls super constructor
    super(text, key);
  }

  //overloads round() method
  @Override
  //returns leftHalf and rightHalf
  public String[] round(String subKey, String leftHalf, String rightHalf)
  {
    String leftOut = rightHalf;
		String rightOut = "";

		//DES F function without permutation P
		rightOut = expansionPermutationE(rightHalf);
		rightOut = xorRoundKey(rightOut, subKey);
		rightOut = sBox(rightOut);
		rightOut = xorHalves(leftHalf, rightOut);

		String[] out = {leftOut, rightOut};
		return out;
  }
}
