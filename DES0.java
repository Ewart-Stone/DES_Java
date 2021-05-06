

public class DES0
{
    private String text;
    private String key;
    private String[] roundkeys;
    private String[] cipherTexts;

    public DES0(String text, String key)
    {
      this.text = text;
      this.key = key;
	  roundkeys = new String[16];
	  cipherTexts = new String[16];

      generateKeys();
    }

    public void encrypt()
    {
	  String cipherText = "";
      // initial permutation
	  cipherText = initialPermutation(text);
	  
	  String leftHalf = cipherText.substring(0,32);
	  String rightHalf = cipherText.substring(32);
	  
      // loop through 16 rounds
	  for(int i = 0; i < 16; i++)
	  {
		String[] temp = round(roundkeys[i], leftHalf, rightHalf);
		leftHalf = temp[0];
		rightHalf = temp[1];
		cipherTexts[i] = leftHalf + rightHalf;
	  }
	  
      //32 bit swap
	  cipherText = rightHalf + leftHalf;
	  
      //inverseInitialPermutation
	  cipherText = inverseInitialPermutation(cipherText);
      //updates this.text to encrypted text at end
	  this.text = cipherText;
    }

    public void decrypt()
    {
	  
	  String plainText = "";
      // initial permutation
	  plainText = initialPermutation(text);
	  
	  String leftHalf = plainText.substring(0,32);
	  String rightHalf = plainText.substring(32);
	  
      // loop through 16 rounds
	  for(int i = 0; i < 16; i++)
	  {
		String[] temp = round(roundkeys[15 - i], leftHalf, rightHalf);
		leftHalf = temp[0];
		rightHalf = temp[1];
	  }
	  
      //32 bit swap
	  plainText = rightHalf + leftHalf;
	  
      //inverseInitialPermutation
	  plainText = inverseInitialPermutation(plainText);
      //updates this.text to decrypted text at end
	  this.text = plainText;
    }

    //returns leftHalf and rightHalf
    public String[] round(String subKey, String leftHalf, String rightHalf)
    {
		String leftOut = rightHalf;
		String rightOut = "";
		
		rightOut = expansionPermutationE(rightHalf);
		rightOut = xorRoundKey(rightOut, subKey);
		rightOut = sBox(rightOut);
		rightOut = permutationP(rightOut);
		rightOut = xorHalves(leftHalf, rightHalf);
		
		String[] out = {leftOut, rightOut};
		return out;
		
    }

    public String expansionPermutationE(String in)
    {
        int[] E = {32,1,2,3,4,5,4,5,6,7,8,9,8,9,10,11,12,13,12,13,14,15,16,17,16,17,18,19,
				   20,21,20,21,22,23,24,25,24,25,26,27,28,29,28,29,30,31,32,1};
				   
	String expandedString = "";

	for(int i = 0; i < 48; i++)
	{
		expandedString = expandedString + String.valueOf(in.charAt(E[i - 1]));
	}

	return expandedString;
    }

    public String xorRoundKey(String in, String key)
    {
		String out = "";
			
		for(int i = 0; i < in.length(); i++)
		{
			int result = Integer.parseInt(String.valueOf(in.charAt(i))) ^ Integer.parseInt(String.valueOf(key.charAt(i)));
			out = out + result;
		}

		return out;
    }

    public String sBox(String in)
    {
		int[][] S1 = {{14,0,4,15},{4,15,1,12},{13,7,14,8},{1,4,8,2},{2,14,13,4},{11,13,2,1},
					  {8,1,11,7},{3,10,15,5},{10,6,12,11},{6,12,9,3},{12,11,7,14},{5,9,3,10},
					  {9,5,10,0},{0,3,5,6},{7,8,0,13}};
						  
		int[][] S2 = {{15,3,0,13},{1,13,14,8},{8,4,7,10},{14,7,11,1},{6,15,10,3},{11,2,4,15},
					  {3,8,13,4},{4,14,1,2},{9,12,5,11},{7,0,8,6},{2,1,12,7},{13,10,6,12},
					  {12,6,9,0},{0,9,3,5},{5,11,2,14},{10,5,15,9}};

		int[][] S3 = {{10,13,13,1},{0,7,6,10},{9,0,4,13},{14,9,9,0},{6,3,8,6},{3,4,15,9},
					  {15,6,3,8},{5,10,0,7},{1,2,11,4},{13,8,1,15},{12,5,2,14},{7,14,12,3},
					  {11,12,5,11},{4,11,10,5},{2,15,14,2},{8,1,7,12}};

		int[][] S4 = {{7,13,10,3},{13,8,6,15},{14,11,9,0},{3,5,0,6},{0,6,12,10},{6,5,11,1},
					  {9,0,7,13},{10,3,13,8},{1,4,15,9},{2,7,1,4},{8,2,3,5},{5,12,14,11},
					  {11,1,5,12},{12,10,2,7},{4,14,8,2},{15,9,4,14}};
			
		int[][] S5 ={{2,14,4,11},{12,11,2,8},{4,2,1,12},{1,12,11,7},{7,4,10,1},{10,7,13,14},
				 {11,13,7,2},{6,1,8,13},{8,5,15,6},{5,0,9,15},{3,15,12,0},{15,10,5,9},
				 {13,3,6,10},{0,9,3,4},{14,8,0,5},{9,6,14,3}};
		
		int[][] S6 ={{12,10,9,4},{1,15,14,3},{10,4,15,2},{15,2,5,12},{9,7,2,9},{2,12,8,5},{6,9,12,15},{8,5,3,10},{0,6,7,11},
				 {13,1,0,14},{3,13,4,1},{4,14,10,7},{14,0,1,6},{7,11,13,0},{5,3,11,8},{11,8,6,13}};
		
		int[][]S7 ={{4,13,1,6},{11,0,4,11},{2,11,11,13},{14,7,13,8},{15,4,12,1},{0,9,3,4},{8,1,7,10},{13,10,14,7},
				{3,14,4,1},{12,3,15,5},{9,5,6,0},{7,12,8,15},{5,2,0,14},{10,15,5,2},{6,8,9,3},{1,6,2,12}};
		
		int[][] S8 ={{13,1,7,2},{2,15,11,1},{8,13,4,14},{4,8,1,7},{6,10,9,4},{15,3,12,10},{11,7,14,8},{1,4,2,13},
				 {10,12,0,15},{9,5,6,12},{3,6,10,9},{14,11,13,0},{5,0,15,3},{0,14,3,5},{12,9,5,6},{7,2,8,11}};
				 
		String[] inputSubstrings = new String[8];
		int pos = 0;
		
		for(int i = 0; i < 8; i++)
		{
			if(i == 7)
			{
				inputSubstrings[i] = in.substring(pos);
			}
			else
			{
				inputSubstrings[i] = in.substring(pos, pos + 6);
				pos = pos + 6;
			}
		}
		
		String substitutedString = "";
		
		for(int i = 0; i < 8; i++)
		{
			String rowString = String.valueOf(inputSubstrings[i].charAt(0)) + String.valueOf(inputSubstrings[i].charAt(5));
			int row = Integer.parseInt(rowString,2);
			
			String colString = inputSubstrings[i].substring(1,5);
			int column = Integer.parseInt(colString,2);
			
			switch(i)
			{
				case 0:
					substitutedString = substitutedString + Integer.toBinaryString(S1[column][row]);
					break;
					
				case 1:
					substitutedString = substitutedString + Integer.toBinaryString(S2[column][row]);
					break;
					
				case 2:
					substitutedString = substitutedString + Integer.toBinaryString(S3[column][row]);
					break;
					
				case 3:
					substitutedString = substitutedString + Integer.toBinaryString(S4[column][row]);
					break;
					
				case 4:
					substitutedString = substitutedString + Integer.toBinaryString(S5[column][row]);
					break;
					
				case 5:
					substitutedString = substitutedString + Integer.toBinaryString(S6[column][row]);
					break;
					
				case 6:
					substitutedString = substitutedString + Integer.toBinaryString(S7[column][row]);
					break;
					
				case 7:
					substitutedString = substitutedString + Integer.toBinaryString(S8[column][row]);
					break;
			}
		}
		
		return substitutedString;
		
	}
    public String permutationP(String in)
    {
		int[] P = {16,7,20,21,29,12,28,17,1,15,23,26,5,18,31,10,2,8,24,14,32,27,3,
				   9,19,13,30,6,22,11,4,25};
		
		String permutedString = "";
		
		for(int i = 0; i < 32; i++)
		{
			permutedString = permutedString + String.valueOf(in.charAt(P[i - 1]));
		}
		
		return permutedString;
		
    }

    public String inverseExpansionPermutation(String in)
    {
		int start = 1;
		int end = 5;
		String output = "";
		
		for(int i = 0; i < 8; i++)
		{
			output = output + in.substring(start, end);
			start += 6;
			end += 6;
		}
		
		return output;
    }

    public String initialPermutation(String in)
    {
      int[] IP = {58,50,42,34,26,18,10,2,60,52,44,36,28,20,12,4,62,54,46,38,30,22,14,6,
                  64,56,48,40,32,24,16,8,57,49,41,33,25,17,9,1,59,51,43,35,27,19,11,3,
                  61,53,45,37,29,21,13,5,63,55,47,39,31,23,15,7};

      String permutedString = "";
      for(int i = 0; i < 64; i++)
      {
        permutedString = permutedString + String.valueOf(in.charAt(IP[i - 1]));
      }

      return permutedString;
    }

    public String inverseInitialPermutation(String in)
    {
      int[] IIP = {40,8,48,16,56,24,64,32,39,7,47,15,55,23,63,31,38,6,46,14,54,22,62,30,
                   37,5,45,13,53,21,61,29,36,4,44,12,52,20,60,28,35,3,43,11,51,19,59,27,
                   34,2,42,10,50,18,58,26,33,1,41,9,49,17,57,25};

      String permutedString = "";
      for(int i = 0; i < 64; i++)
      {
        permutedString = permutedString + String.valueOf(in.charAt(IIP[i - 1]));
      }

      return permutedString;
    }

    public String xorHalves(String leftHalf, String rightHalf)
    {
      //xor righthalf with lefthalf
	  
		String out = "";
			
		for(int i = 0; i < leftHalf.length(); i++)
		{
			int result = Integer.parseInt(String.valueOf(leftHalf.charAt(i))) ^ Integer.parseInt(String.valueOf(rightHalf.charAt(i)));
			out = out + result;
		}

		return out;

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
         this.roundkeys[i] = permutedChoice2(leftHalf, rightHalf);
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
          halves[0] = String.valueOf(this.key.charAt(PC1[i - 1]));
        }
        else
        {
          halves[1] = String.valueOf(this.key.charAt(PC1[i - 1]));
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
        for(int i = 1; i < half.length(); i++)
        {
          shiftedHalf = shiftedHalf + String.valueOf(half.charAt(i));
        }

        shiftedHalf = shiftedHalf + String.valueOf(half.charAt(0));
      }
      //shift by two bits
      else
      {
        for(int i = 2; i < half.length(); i++)
        {
          shiftedHalf = shiftedHalf + String.valueOf(half.charAt(i));
        }

        shiftedHalf = shiftedHalf + String.valueOf(half.charAt(0));
        shiftedHalf = shiftedHalf + String.valueOf(half.charAt(1));
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
        subkey = subkey + String.valueOf(joinedHalves.charAt(PC2[i - 1]));
      }

      return subkey;
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
