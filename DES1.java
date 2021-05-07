/*
 * This implements a class DES1 which shows the encryption algorithm this is the Second version of DES in this XOR with a round key is missing from F function in all rounds
 
 * Author - Ewart Stone(c3350508), Ankur(c3347695) 
 * Date - 07/05/2021 
 * This file is used in conjuction with Assignment 2

*/

public class DES1 extends DES0                                          //DES1 is the public class  which is  extends DES0
{
  public DES1(String text, String key)
  {
    super(text, key);
  }

  @Override
  //returns leftHalf and rightHalf
  public String[] round(String subKey, String leftHalf, String rightHalf)   // general string's in a round string 
  {
    String leftOut = rightHalf;                                             // leftout is equals to right half in all the cases of DES
    String rightOut = "";                                                   // here the working is dhown to get the right out 

    rightOut = expansionPermutationE(rightHalf);                            // XOR with a round key is missing  from F function in all rounds 
    rightOut = sBox(rightOut);
    rightOut = permutationP(rightOut);
    rightOut = xorHalves(leftHalf, rightHalf);

    String[] out = {leftOut, rightOut};                                    // result
    return out;

  }
}
