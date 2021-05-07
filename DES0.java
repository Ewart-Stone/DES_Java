
/*
 * This implements a class DES0 which shows the encryption algorithm this is the original version of DES  which shows the normal working of the F Function in all rounds 
 * Author - Ewart Stone(c3350508), Ankur(c3347695) 
 * Date - 07/05/2021 
 * This file is used in conjuction with Assignment 2
 * Standard DES encryption w/ inverseExpansionPermutation added for DES2 

*/
public class DES0
{
	// we used 4 private strings text,key,roundkeys,cipherTexts
	// unaltered stores the original plaintext / ciphertext and is not altered at all
	//roundKeys stores subkeys for rounds 1-16
	//cipherTexts stores left and right half of texts after each round
	
    private String text;
    private String unaltered;
    private String key;
    private String[] roundkeys;
    private String[] cipherTexts;

	
    public DES0(String text, String key)
    {
      this.text = text;
      this.unaltered = text;
      this.key = key;
	  
	  //instantiate fixed length arrays
	  roundkeys = new String[16];
	  cipherTexts = new String[16];

	  //fill roundKeys with subkeys from key input
      generateKeys();
    }

    public void encrypt()
    {
		
  	  String cipherText = "";
	  
	  // initial permutation
  	  cipherText = initialPermutation(text);

	  //split permuted plaintext into 2 halves
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

	  //split permuted plaintext into 2 halves
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


	//Round performs DES round methods including swapping of the halves, xor of halves, and F function
    //Round returns leftHalf and rightHalf in string array
    public String[] round(String subKey, String leftHalf, String rightHalf)
    {
		//copy rightHalf to leftOutput
  		String leftOut = "" + rightHalf;
  		String rightOut = "";

		//perform F function on rightHalf and assign to right output
  		rightOut = expansionPermutationE(rightHalf);
  		rightOut = xorRoundKey(rightOut, subKey);
  		rightOut = sBox(rightOut);
  		rightOut = permutationP(rightOut);
		
		//xor leftHalf with rightOutput and assign to rightOutput
  		rightOut = xorHalves(leftHalf, rightOut);

  		String[] out = {leftOut, rightOut};
  		return out;

    }

	//Returns 48 bit output from 32 bit input string
	//Des F function E permutation
    public String expansionPermutationE(String in)
    {
      int[] E = {32,1,2,3,4,5,4,5,6,7,8,9,8,9,10,11,12,13,12,13,14,15,16,17,16,17,18,19,
			           20,21,20,21,22,23,24,25,24,25,26,27,28,29,28,29,30,31,32,1};

      String expandedString = "";

	  //loop through E array and append character at position i in String input (in) to output
	  for(int i = 0; i < 48; i++)
	  {
	  	expandedString = expandedString + String.valueOf(in.charAt(E[i] - 1));
	  }


	  return expandedString;
    }

	//returns xor of String input and String key
    public String xorRoundKey(String in, String key)
    {
  		String out = "";
	
		//xors each individual corresponding character to each other and appends to output
  		for(int i = 0; i < in.length(); i++)
  		{
  			int result = Integer.parseInt(String.valueOf(in.charAt(i))) ^ Integer.parseInt(String.valueOf(key.charAt(i)));
  			out = out + result;
	    }

	    return out;
    }

	//returns 32 bit String for 48 bit input
	//
    public String sBox(String in)
    {
		//define Sboxes as individual 2D arrays
		int[][] S1 = {{14,0,4,15},{4,15,1,12},{13,7,14,8},{1,4,8,2},{2,14,13,4},{15,2,6,9},
            {11,13,2,1},{8,1,11,7},{3,10,15,5},{10,6,12,11},{6,12,9,3},{12,11,7,14},
            {5,9,3,10},{9,5,10,0},{0,3,5,6},{7,8,0,13}};

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
				{3,14,10,1},{12,3,15,5},{9,5,6,0},{7,12,8,15},{5,2,0,14},{10,15,5,2},{6,8,9,3},{1,6,2,12}};

		int[][] S8 ={{13,1,7,2},{2,15,11,1},{8,13,4,14},{4,8,1,7},{6,10,9,4},{15,3,12,10},{11,7,14,8},{1,4,2,13},
				 {10,12,0,15},{9,5,6,12},{3,6,10,9},{14,11,13,0},{5,0,15,3},{0,14,3,5},{12,9,5,6},{7,2,8,11}};

		String[] inputSubstrings = new String[8];
		int pos = 0;

		// there are 8 substrings of len 6 from String in (param
		// start position (in input string) = 0 

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

		//intiialise variables
		String substitutedString = "";
		String sBoxAddon = "";
		String zeroes = "0000";

		//loop 8 times
		//for 8 sBoxes
		for(int i = 0; i < 8; i++)
		{
			//get row from substring by converting string to binary
			String rowString = String.valueOf(inputSubstrings[i].charAt(0)) + String.valueOf(inputSubstrings[i].charAt(5));
			int row = Integer.parseInt(rowString,2);

			//get column from substring by converting string to binary
			String colString = inputSubstrings[i].substring(1,5);
			int column = Integer.parseInt(colString,2);

			//switch cases determines which sBox to use
			//sBoxAddon is set to corresponding indexed value
			//converted to binary string
			switch(i)
			{
				case 0:
					sBoxAddon = Integer.toBinaryString(S1[column][row]);
					break;

				case 1:
					sBoxAddon = Integer.toBinaryString(S2[column][row]);
					break;

				case 2:
					sBoxAddon = Integer.toBinaryString(S3[column][row]);
					break;

				case 3:
					sBoxAddon = Integer.toBinaryString(S4[column][row]);
					break;

				case 4:
					sBoxAddon = Integer.toBinaryString(S5[column][row]);
					break;

				case 5:
					sBoxAddon = Integer.toBinaryString(S6[column][row]);
					break;

				case 6:
					sBoxAddon = Integer.toBinaryString(S7[column][row]);
					break;

				case 7:
					sBoxAddon = Integer.toBinaryString(S8[column][row]);
					break;
			}

			//adds zeroes onto sBoxAddon if less than length 4
			//by prepending substring of zeroes missing
			if(sBoxAddon.length() < 4)
			{
				sBoxAddon = zeroes.substring(0, 4 - sBoxAddon.length()) + sBoxAddon;
			}

			//append sBoxAddon to output string
			substitutedString = substitutedString + sBoxAddon;
		}

		return substitutedString;

	}
	
	//Permutation P
	//returns permuted 48 bit string
    public String permutationP(String in)
    {
		//permutation P box as 1D array
  		int[] P = {16,7,20,21,29,12,28,17,1,15,23,26,5,18,31,10,2,8,24,14,32,27,3,
  				       9,19,13,30,6,22,11,4,25};

		//init output string
  		String permutedString = "";

		//loops through P array and appends corresponding char in String in (param) to
		//output string
  		for(int i = 0; i < 32; i++)
  		{
  			permutedString = permutedString + String.valueOf(in.charAt(P[i] - 1));
  		}

  		return permutedString;

    }

	//inverse Expansion permuation used for contraction from 48 down to 32 bits
    public String inverseExpansionPermutation(String in)
    {
  		int start = 1;
  		int end = 5;
  		String output = "";

		//loops through string in (param) and removes bits in positions
		//added by expansion permutation E
		//for use in DES2
  		for(int i = 0; i < 8; i++)
  		{
  			output = output + in.substring(start, end);
  			start += 6;
  			end += 6;
  		}

  		return output;
    }


	//returns String initial permutation of in
	//
    public String initialPermutation(String in)
    {
      int[] IP = {58,50,42,34,26,18,10,2,60,52,44,36,28,20,12,4,62,54,46,38,30,22,14,6,
                  64,56,48,40,32,24,16,8,57,49,41,33,25,17,9,1,59,51,43,35,27,19,11,3,
                  61,53,45,37,29,21,13,5,63,55,47,39,31,23,15,7};

      String permutedString = "";
	  
	  // loop through IP array and append char in String in to output 
      for(int i = 0; i < 64; i++)
      {
        permutedString = permutedString + String.valueOf(in.charAt(IP[i] - 1));
      }

      return permutedString;
    }

	//inverse initial permutation returns String of permutedString
	//inverse to initial permutation and used after rounds completed
    public String inverseInitialPermutation(String in)
    {
      int[] IIP = {40,8,48,16,56,24,64,32,39,7,47,15,55,23,63,31,38,6,46,14,54,22,62,30,
                   37,5,45,13,53,21,61,29,36,4,44,12,52,20,60,28,35,3,43,11,51,19,59,27,
                   34,2,42,10,50,18,58,26,33,1,41,9,49,17,57,25};

      String permutedString = "";
	  
	  //loop through 64 indexes of IIP array
	  //append corresponding char in String in (param) to output
      for(int i = 0; i < 64; i++)
      {
        permutedString = permutedString + String.valueOf(in.charAt(IIP[i] - 1));
      }

      return permutedString;
    }

	//xorHalves returns string output of righthalf xor with lefthalf
    public String xorHalves(String leftHalf, String rightHalf)
    {

		String out = "";

		//xor righthalf with lefthalf
		//by comparing each corresponding character
		for(int i = 0; i < leftHalf.length(); i++)
		{
			int result = Integer.parseInt(String.valueOf(leftHalf.charAt(i))) ^ Integer.parseInt(String.valueOf(rightHalf.charAt(i)));
			out = out + result;
		}

		return out;

    }

	//generate all subkeys from private member key
    public void generateKeys()
    {
      String leftHalf;
      String rightHalf;

	  //get left and right halves from permutedChoice 1
      String[] halves = permutedChoice1();

      leftHalf = halves[0];
      rightHalf = halves[1];

	  //for 16 keys perform leftShift and PC2 of halves to generate subkeys of 48 bits
      for(int i = 0; i < 16; i++)
      {
         leftHalf = leftShift(leftHalf, i + 1);
         rightHalf = leftShift(rightHalf, i + 1);
         this.roundkeys[i] = permutedChoice2(leftHalf, rightHalf);
         //
         //
      }
    }

	//Permuted choice one returns left and righthalf in array len 2
	//where halves are characters selected from pvt key
    public String[] permutedChoice1()
    {
      String[] halves = new String[2];
      halves[0] = "";
      halves[1] = "";

      int[] PC1 = {57,49,41,33,25,17,9,1,58,50,42,34,26,18,10,2,59,51,43,35,27,19,11,3,60,52,44,36,
                   63,55,47,39,31,23,15,7,62,54,46,38,30,22,14,6,61,53,45,37,29,21,13,5,28,20,12,4};

      //permuted this.key into two halves
      //leftHalf at index 0
      //rightHalf at index 1

      for(int i = 0; i < 56; i++)
      {
        if(i < 28)
        {
          halves[0] = halves[0] + String.valueOf(this.key.charAt(PC1[i] - 1));
        }
        else
        {
          halves[1] = halves[1] + String.valueOf(this.key.charAt(PC1[i] - 1));
        }
      }

      return halves;
    }

	//shifts characters in String half to the left according to int round
	//used in key generation
    public String leftShift(String half, int round)
    {
		
      String shiftedHalf = "";
		
	  //shift according to DES shift table
      //shift by one bit to left
      if(round == 1 || round == 2 || round == 9 || round == 16)
      {
        for(int i = 1; i < half.length(); i++)
        {
          shiftedHalf = shiftedHalf + String.valueOf(half.charAt(i));
        }

        shiftedHalf = shiftedHalf + String.valueOf(half.charAt(0));
      }
      //shift by two bits to left
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

	//PC2 in key generation
	//returns permuted string from halves
	//as 48 bit output
    public String permutedChoice2(String leftHalf, String rightHalf)
    {
      String joinedHalves = leftHalf + rightHalf;
      String subkey = "";

      int[] PC2 = {14,17,11,24,1,5,3,28,15,6,21,10,23,19,12,4,26,8,16,7,27,20,13,2,
                   41,52,31,37,47,55,30,40,51,45,33,48,44,49,39,56,34,53,46,42,50,36,29,32};


	  //loops through array and appends corresponding characters in joinedHalves
      for(int i = 0; i < 48; i++)
      {
        subkey = subkey + String.valueOf(joinedHalves.charAt(PC2[i] - 1));
      }

      return subkey;
    }

	//getters
	
	//returns round cipherText in pvt array at given index
    public String getCipherText(int index)
    {
      return this.cipherTexts[index];
    }

	//returns text
    public String getText()
    {
      return this.text;
    }

	//returns key
    public String getKey()
    {
      return this.key;
    }

	//returns initial text input
    public String getUnaltered()
    {
      return this.unaltered;
    }


}
