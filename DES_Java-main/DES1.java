
public class DES1 extends DES0
{
  public DES1(String text, String key)
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
    rightOut = sBox(rightOut);
    rightOut = permutationP(rightOut);
    rightOut = xorHalves(leftHalf, rightOut);

    String[] out = {leftOut, rightOut};
    return out;

  }
}
