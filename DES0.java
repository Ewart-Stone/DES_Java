

public class DES0
{
    private text;
    private key;
    private String[16] roundkeys;
    private String[16] cipherTexts;

    public DES0(String text, String key)
    {
      this.text = text;
      this.key = key;

      generateKeys();
    }

    public void encrypt()
    {
      // initial permutation
      // loop through 16 rounds
      //32 bit swap
      // inverseInitialPermutation
      //updates this.text to encrypted text at end
    }

    public void decrypt()
    {
      // initial permutation
      // loop through 16 rounds with keys in reverse order
      //32 bit swap
      // inverseInitialPermutation
      //updates this.text to encrypted text at end
    }

    //returns leftHalf and rightHalf
    public String[] round(String subKey, String leftHalf, String rightHalf)
    {

    }

    public String expansionPermutationE(String in)
    {

    }

    public String xorRoundKey(String in, String key)
    {

    }

    public String sBox(String in)
    {

    }

    public String permutationP(String in)
    {

    }

    public String inverseExpansionPermutation(String in)
    {

    }

    public String initialPermutation(String in)
    {

    }

    public String inverseInitialPermutation(String in)
    {

    }

    public String xorHalves(String leftHalf, String rightHalf)
    {
      //xor righthalf with lefthalf
      //return rightHalf
    }

    public void generateKeys()
    {
      String leftHalf;
      String rightHalf;

      String[] halves = permutedChoice1();

      leftHalf = halves[0];
      rightHalf = halves[1];

      for(int i = 0; i < 16; i++)
      {
         leftHalf = leftShift(leftHalf, i + 1);
         rightHalf = leftShift(rightHalf, i + 1);
         this.roundKeys[i] = permutedChoice2(leftHalf, rightHalf);
         //
         //
      }
    }

    public String[] permutedChoice1()
    {
      String[] halves = new String[2];

      int[] PC1 = {57,49,41,33,25,17,9,1,58,50,42,34,26,18,10,2,59,51,43,35,27,19,11,3,60,52,44,36,
                   63,55,47,39,31,23,15,7,62,54,46,38,30,22,14,6,61,53,45,37,29,21,13,5,28,20,12,4};

      //permuted this.key into two halves
      //leftHalf at index 0
      //rightHalf at index 1

      for(int i = 0; i < 56; i++)
      {
        if(i < 28)
        {
          halves[0] = String.valueOf(this.key.charAt(PC1[i]));
        }
        else
        {
          halves[1] = String.valueOf(this.key.charAt(PC1[i]));
        }
      }

      return halves;
    }

    public String leftShift(String half, int round)
    {
      String shiftedHalf = "";

      //shift by one bit
      if(round == 1 || round == 2 || round == 9 || round == 16)
      {
        for(int = 1; i < half.length(), i++)
        {
          shiftedHalf = shiftedHalf + String.valueOf(half.charAt(i));
        }

        shiftedHalf = shiftedHalf + String.valueOf(half.charAt(0);
      }
      //shift by two bits
      else
      {
        for(int = 2; i < half.length(), i++)
        {
          shiftedHalf = shiftedHalf + String.valueOf(half.charAt(i));
        }

        shiftedHalf = shiftedHalf + String.valueOf(half.charAt(0);
        shiftedHalf = shiftedHalf + String.valueOf(half.charAt(1);
      }

      return shiftedHalf;
    }

    public String permutedChoice2(String leftHalf, String rightHalf)
    {
      String joinedHalves = leftHalf + rightHalf;
      String subkey = "";

      int[] PC2 = {14,17,11,24,1,5,3,28,15,6,21,10,23,19,12,4,26,8,16,7,27,20,13,2,
                   41,52,31,37,47,55,30,40,51,45,33,48,44,49,39,56,34,53,46,42,50,36,29,32};

      for(int i = 0; i < 48; i++)
      {
        subkey = subKey + String.valueOf(joinedHalves.charAt(PC2[i]));
      }

      return subKey;
    }

    public String getCipherText(int index)
    {
      return this.cipherTexts[index];
    }

    public String getText()
    {
      return this.text;
    }

    public String getKey()
    {
      return this.key;
    }



}
