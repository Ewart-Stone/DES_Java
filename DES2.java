
public class DES2 extends DES0
{
  public DES2(String text, String key)
  {
    super(text, key);
  }

  @Override
  //returns leftHalf and rightHalf
  public String[] round(String subKey, String leftHalf, String rightHalf)
  {
		String leftOut = rightHalf;
		String rightOut = "";
		
		rightOut = expansionPermutationE(rightHalf);
		rightOut = xorRoundKey(rightOut, subKey);
		rightOut = inverseExpansionPermutation(rightOut);
		rightOut = permutationP(rightOut);
		rightOut = xorHalves(leftHalf, rightHalf);
		
		String[] out = {leftOut, rightOut};
		return out;
		
  }
}
