
public class DES3 extends DES0
{
  public DES3(String text, String key)
  {
    super(text, key);
  }

  @Override
  public String[] round(String subKey, String leftHalf, String rightHalf)
  {
    String leftOut = rightHalf;
		String rightOut = "";

		rightOut = expansionPermutationE(rightHalf);
		rightOut = xorRoundKey(rightOut, subKey);
		rightOut = sBox(rightOut);
		rightOut = xorHalves(leftHalf, rightOut);

		String[] out = {leftOut, rightOut};
		return out;
  }
}
