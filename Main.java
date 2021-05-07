/*
 * This is the main file of out assignment
 * reads and writes to files
 * generates plaintext and keys from input
 * controls DES instances and performs avalanche analysis on results
 * Author - Ewart Stone(c3350508), Ankur(c3347695) 
 * Date - 07/05/2021 
 * This file is used in conjuction with Assignment 2
 * 

*/

import java.util.*;
import java.io.*;



public class Main
{
	
  //main method
  public static void main(String[] args)
  {
	//initialise variables and objects
	
	//arrays store encryption plaintext and keys
    String[] plainText = new String[65];
    String[] keys = new String[57];
	
	//strings store ciphertext and key
    String cipherText = "";
    String cipherKey = "";

	//stores output as linkedList
    LinkedList<String> encryptionOutput = new LinkedList<String>();
    LinkedList<String> decryptionOutput = new LinkedList<String>();

	Scanner input = new Scanner(System.in);

    //encryption and avalanche analysis

	//get encryption file input from user
	//command line
	System.out.println("Enter the file path and name for the encryption file. i.e. test.txt ");
	String in = input.nextLine();

    //start timer for encryption
    long start = System.currentTimeMillis();

    //get inputs from file
    String[] lines = readFromFile(in);
    plainText[0] = lines[0];
    keys[0] = lines[1];


    //generate 64 other plaintexts
    //generate 56 other keys
	//1 bit flipped in each
    generateFlippedBits(plainText, 65);
    generateFlippedBits(keys, 57);

    //avalanche analysis function
	//and encryption
    encryptionOutput = encryptionOutput(plainText, keys, start);

    //write data to encryption file
    writeToFile("encrypted.txt", encryptionOutput);

    //decryption

	//get decryption file input from user
	//command line
	System.out.println("Enter the file path and name for the decryption file. i.e. text.txt ");
	in = input.nextLine();

    //get inputs from file
    lines = readFromFile(in);
    cipherText = lines[0];
    cipherKey = lines[1];



    //get decryption file output
    decryptionOutput = decryptionOutput(cipherText, cipherKey);

    //write data to decryption file
    writeToFile("decrypted.txt", decryptionOutput);

    //end main method
  }

  //returns input file lines
  //64 bit plaintext / ciphertext and 64 bit key
  //in binary
  public static String[] readFromFile(String fileName)
  {
    String[] lines = new String[2];

	//read through file otherwise print error in console
	try
	{
		//init file and fileReader
		File input = new File(fileName);
		Scanner inReader = new Scanner(input);
		
		int count = 0;
		
		//loop through file lines
		while(inReader.hasNextLine())
		{
			//insert line into array pos
			String data = inReader.nextLine();

			if(count < 2)
			{
				lines[count] = data;
			}
			count++;
		}
		inReader.close();
	}
	catch (FileNotFoundException e)
	{
		System.out.println("Error: file not found");
	}

    return lines;
  }

  //writes linkedList node data input into seperate lines at fileName
  public static void writeToFile(String fileName, LinkedList<String> output)
  {
    String[] lines = new String[2];
	
	//attempt to write to file otherwise catch error
	try
	{
		
		FileWriter writer = new FileWriter(fileName);
		
		//loop through linkedlist and append lines to file
		for(int i = 0; i < output.size(); i++)
		{
			writer.write(output.get(i) + "\n");
		}
		
		writer.close();
	}
	catch (IOException e)
	{
		System.out.println("An error has occured");
	}
  }

  //returns output for encryptionFile
  //contains avalanche analysis and encryption
  public static LinkedList<String> encryptionOutput(String[] plaintext, String[] keys, long start)
  {
	LinkedList<String> output = new LinkedList<String>();

	//DES with plaintexts
	//Init all DES objects for P and Pi under K analysis
	LinkedList<DES0> des0Plaintext = new LinkedList<DES0>();
	LinkedList<DES0> des1Plaintext = new LinkedList<DES0>();
	LinkedList<DES0> des2Plaintext = new LinkedList<DES0>();
	LinkedList<DES0> des3Plaintext = new LinkedList<DES0>();
	
	//4 loops for 4 DES object types
	for(int i = 0; i < 65; i++)
	{
		des0Plaintext.add(new DES0(plaintext[i], keys[0]));
	}
	
	for(int i = 0; i < 65; i++)
	{
		des1Plaintext.add(new DES1(plaintext[i], keys[0]));
	}
	
	for(int i = 0; i < 65; i++)
	{
		des2Plaintext.add(new DES2(plaintext[i], keys[0]));
	}
	
	for(int i = 0; i < 65; i++)
	{
		des3Plaintext.add(new DES3(plaintext[i], keys[0]));
	}

	//encrypt all DES objects
	for(int i = 0; i < 4; i++)
	{
		for(int y = 0; y < 65; y++)
		{
		  switch(i)
		  {
			case 0:
			  des0Plaintext.get(y).encrypt();
			  break;

			case 1:
			  des1Plaintext.get(y).encrypt();
			  break;

			case 2:
			  des2Plaintext.get(y).encrypt();
			  break;

			case 3:
			  des3Plaintext.get(y).encrypt();
			  break;

		  }
		}
	}

	//DES with varying keys
	//Init all DES objects for P under K and Ki analysis
	LinkedList<DES0> des0Keys = new LinkedList<DES0>();
	LinkedList<DES0> des1Keys = new LinkedList<DES0>();
	LinkedList<DES0> des2Keys = new LinkedList<DES0>();
	LinkedList<DES0> des3Keys = new LinkedList<DES0>();
	
	//4 loops for all des object types
	for(int i = 0; i < 57; i++)
	{
		des0Keys.add(new DES0(plaintext[0], keys[i]));
	}
	
	for(int i = 0; i < 57; i++)
	{
		des1Keys.add(new DES1(plaintext[0], keys[i]));
	}
	
	for(int i = 0; i < 57; i++)
	{
		des2Keys.add(new DES2(plaintext[0], keys[i]));
	}
	
	for(int i = 0; i < 57; i++)
	{
		des3Keys.add(new DES3(plaintext[0], keys[i]));
	}

	//encrypt all data in DES with varying Keys
	for(int i = 0; i < 4; i++)
	{
		for(int y = 0; y < 57; y++)
		{
		  switch(i)
		  {
			case 0:
			  des0Keys.get(y).encrypt();
			  break;

			case 1:
			  des1Keys.get(y).encrypt();
			  break;

			case 2:
			  des2Keys.get(y).encrypt();
			  break;

			case 3:
			  des3Keys.get(y).encrypt();
			  break;

		  }
		}
	}
	
	//after encryption
	//records runtime taken for all encryptions to take place
	long end = System.currentTimeMillis();
	long elapsedTime = end - start;


	//add output strings to output linkedList
	output.add("ENCRYPTION");
	output.add("Plaintext P: " + plaintext[0]);
	output.add("Key K: " + keys[0]);
	output.add("Ciphertext C: " + des0Plaintext.get(0).getText());
	output.add("Running time: " + String.valueOf(elapsedTime) + "ms");
	output.add("");
	output.add("");
	output.add("Avalanche:");
	output.add("");
	output.add("P and Pi under K");
	output.add("Round        DES0    DES1    DES2    DES3");

	//Loop 17 times for round 0 - 16
	for(int i = 0; i < 17; i++)
	{
		//format round num position
		String roundSpaces = "          ";
		String roundNum = Integer.toString(i);
		String out = "   " + i + roundSpaces.substring(0, (10 - roundNum.length() - 1));

		//loop through all 4 des objects per round
		for(int y = 0; y < 4; y++)
		{
		  //avg bit difference between plaintexts in same DES object type array
		  int avg = 0;

		  //loop through all 65 DES instances of each array
		  for(int z = 1; z < 65; z++)
		  {

			//compare P at round i to Pi at round i
			//switch case controls DES type
			switch(y)
			{
			  case 0:

				if(i == 0)
				{
				  avg += compareBits(des0Plaintext.get(0).getUnaltered(), des0Plaintext.get(z).getUnaltered());
				}
				else
				{
				  avg += compareBits(des0Plaintext.get(0).getCipherText(i - 1), des0Plaintext.get(z).getCipherText(i - 1));
				}
				break;

			  case 1:
				if(i == 0)
				{
				  avg += compareBits(des1Plaintext.get(0).getUnaltered(), des1Plaintext.get(z).getUnaltered());
				}
				else
				{
				  avg += compareBits(des1Plaintext.get(0).getCipherText(i - 1), des1Plaintext.get(z).getCipherText(i - 1));
				}
				break;

			  case 2:
				if(i == 0)
				{
				  avg += compareBits(des2Plaintext.get(0).getUnaltered(), des2Plaintext.get(z).getUnaltered());
				}
				else
				{
				  avg += compareBits(des2Plaintext.get(0).getCipherText(i - 1), des2Plaintext.get(z).getCipherText(i - 1));
				}
				break;

			  case 3:
				if(i == 0)
				{
				  avg += compareBits(des3Plaintext.get(0).getUnaltered(), des3Plaintext.get(z).getUnaltered());
				}
				else
				{
				  avg += compareBits(des3Plaintext.get(0).getCipherText(i - 1), des3Plaintext.get(z).getCipherText(i - 1));
				}
				break;
			}

		  }
		  
		  //compute avg
		  avg = avg / 64;
		 
		  //format output for line for next DES avg
		  String spaces = "          ";
		  out = out + avg + spaces.substring(0, 9 - Integer.toString(avg).length() - 1);

		}
		
		//add roundLine output to output LinkedList
		output.add(out);
	}

	//format for P under K and Ki analysis
	output.add("");
	output.add("");
	output.add("P under K and Ki");
	output.add("Round        DES0    DES1    DES2    DES3");

	//loop through rounds 0-16
	for(int i = 0; i < 17; i++)
	{
		String roundSpaces = "          ";
		String roundNum = Integer.toString(i);
		String out = "   " + i + roundSpaces.substring(0, (10 - roundNum.length() - 1));

		//loop through each Des object array
		//calculate avg bit difference
		for(int y = 0; y < 4; y++)
		{
			
		  int avg = 0;

		  //loop 57 times for 57 varying keys
		  for(int z = 1; z < 57; z++)
		  {
				
			//switch controls Des array compared
			switch(y)
			{
			  case 0:

				if(i == 0)
				{
				  avg += compareBits(des0Keys.get(0).getUnaltered(), des0Keys.get(z).getUnaltered());
				}
				else
				{
				  avg += compareBits(des0Keys.get(0).getCipherText(i - 1), des0Keys.get(z).getCipherText(i - 1));
				}
				break;

			  case 1:
				if(i == 0)
				{
				  avg += compareBits(des1Keys.get(0).getUnaltered(), des1Keys.get(z).getUnaltered());
				}
				else
				{
				  avg += compareBits(des1Keys.get(0).getCipherText(i - 1), des1Keys.get(z).getCipherText(i - 1));
				}
				break;

			  case 2:
				if(i == 0)
				{
				  avg += compareBits(des2Keys.get(0).getUnaltered(), des2Keys.get(z).getUnaltered());
				}
				else
				{
				  avg += compareBits(des2Keys.get(0).getCipherText(i - 1), des2Keys.get(z).getCipherText(i - 1));
				}
				break;

			  case 3:
				if(i == 0)
				{
				  avg += compareBits(des3Keys.get(0).getUnaltered(), des3Keys.get(z).getUnaltered());
				}
				else
				{
				  avg += compareBits(des3Keys.get(0).getCipherText(i - 1), des3Keys.get(z).getCipherText(i - 1));
				}
				break;
			}

		  }
		  
		  //compute avg and add to string out
		  avg = avg / 56;
		  String spaces = "          ";
		  
		  //format for next avg
		  out = out + avg + spaces.substring(0, 9 - Integer.toString(avg).length() - 1);

		}
		
		//add to linkedList output
		output.add(out);
	
	}


	return output;
  }

  //decryption output and computation
  //returns decryption.txt output
  public static LinkedList<String> decryptionOutput(String cipherText, String key)
  {
	LinkedList<String> output = new LinkedList<String>();

	//intit DES0 obj with cipherText and key
	//decrypt
	DES0 des = new DES0(cipherText, key);
	des.decrypt();

	//append data to linkedList for file output
	output.add("DECRYPTION");
	output.add("Ciphertext C: " + cipherText);
	output.add("Key K: " + key);
	output.add("Plaintext P: " + des.getText());

	return output;
  }


  //returns an array of length (param) with strings with 1 bit difference
  //compared to texts[0] (param)
  public static String[] generateFlippedBits(String[] texts, int length)
  {
    String left = "";
    String right = "";
    int pos = 0;

	//loop for full length of array
    for(int i = 0; i < length - 1; i++)
    {
		
	  //in case of key variance
	  //change different bits for mod 8 = 0 as
	  //bits i are not chosen for subkeys in PC1
      if(length == 57 && i != 0 && (i + 1) % 8 == 0)
      {
        switch((i + 1) / 8)
        {
          case 1:
            pos = 56;
            break;

          case 2:
            pos = 57;
            break;

          case 3:
            pos = 58;
            break;

          case 4:
            pos = 59;
            break;

          case 5:
            pos = 60;
            break;

          case 6:
            pos = 61;
            break;

          case 7:
            pos = 62;
            break;
        }
      }
      else
      {
        pos = i;
      }

	  //split text[0] into two halves and flip bit pos
	  
      if(i == 0)
      {
          left = "";
          right = texts[0].substring(pos + 1);
      }
      else if(i == length - 1)
      {
        left = texts[0].substring(0, pos - 1);
        right = "";
      }
      else
      {
        left = texts[0].substring(0, pos);
        right = texts[0].substring(pos + 1);
      }

	  //get bit to flip
      char flippedBit = texts[0].charAt(pos);
      String val = "";

	  //flip bit
      if(flippedBit == '0')
      {
        val = "1";
      }
      else
      {
        val = "0";
      }

	  //add String with flipped bit to array output
      texts[i + 1] = left + val + right;

    }

    return texts;
  }

  //compares two strings and returns the number of different characters at index i
  public static int compareBits(String in1, String in2)
  {
    int differentBits = 0;

	//loop through strings
    for(int i = 0; i < in1.length(); i++)
    {
		
	  //compare characters
      if(in1.charAt(i) != in2.charAt(i))
      {
          differentBits ++;
      }
    }

    return differentBits;
  }


}
