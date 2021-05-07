/* This is the main file of our assignment 
Author - Ewart stone, Ankur
Date - 07/05/2021
*/


import java.util.*;
import java.io.*;

public class Main
{
  public static void main(String[] args)
  {
    double runTime = 0;
    String[] plainText = new String[65];
    String[] keys = new String[57];
    String cipherText = "";
    String cipherKey = "";

    LinkedList<String> encryptionOutput = new LinkedList<String>();
    LinkedList<String> decryptionOutput = new LinkedList<String>();

	Scanner input = new Scanner(System.in);

    //encryption and avalanche analysis

	//get file input from user
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
    generateFlippedBits(plainText, 65);
    generateFlippedBits(keys, 57);

    //avalanche analysis function
    encryptionOutput = encryptionOutput(plainText, keys, start);

    //write data to encryption file
    writeToFile("encrypted.txt", encryptionOutput);

    //decryption

	//get file input from user
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

    //end
  }

  public static String[] readFromFile(String fileName)
  {
    String[] lines = new String[2];

	try
	{
		File input = new File(fileName);
		Scanner inReader = new Scanner(input);
		int count = 0;
		while(inReader.hasNextLine())
		{
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

  public static void writeToFile(String fileName, LinkedList<String> output)
  {
    String[] lines = new String[2];
	try
	{
		FileWriter writer = new FileWriter(fileName);
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

  public static LinkedList<String> encryptionOutput(String[] plaintext, String[] keys, long start)
  {
	LinkedList<String> output = new LinkedList<String>();

	//after encryption
	long end = System.currentTimeMillis();
	long elapsedTime = end - start;

  //DES with plaintexts
  //
  LinkedList<DES0> des0Plaintext = new LinkedList<DES0>();
  LinkedList<DES0> des1Plaintext = new LinkedList<DES0>();
  LinkedList<DES0> des2Plaintext = new LinkedList<DES0>();
  LinkedList<DES0> des3Plaintext = new LinkedList<DES0>();
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
  //
  LinkedList<DES0> des0Keys = new LinkedList<DES0>();
  LinkedList<DES0> des1Keys = new LinkedList<DES0>();
  LinkedList<DES0> des2Keys = new LinkedList<DES0>();
  LinkedList<DES0> des3Keys = new LinkedList<DES0>();
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

  for(int i = 0; i < 17; i++)
  {
	String roundSpaces = "          ";
	String roundNum = Integer.toString(i);
    String out = "   " + i + roundSpaces.substring(0, (10 - roundNum.length() - 1));

    for(int y = 0; y < 4; y++)
    {
      int avg = 0;

      for(int z = 1; z < 65; z++)
      {

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
      avg = avg / 64;
      String spaces = "          ";
      out = out + avg + spaces.substring(0, 9 - Integer.toString(avg).length() - 1);

    }
    output.add(out);
  }

  output.add("");
  output.add("");
  output.add("P under K and Ki");
  output.add("Round        DES0    DES1    DES2    DES3");

  for(int i = 0; i < 17; i++)
  {
    String roundSpaces = "          ";
	String roundNum = Integer.toString(i);
    String out = "   " + i + roundSpaces.substring(0, (10 - roundNum.length() - 1));

    for(int y = 0; y < 4; y++)
    {
      int avg = 0;

      for(int z = 1; z < 57; z++)
      {

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
      avg = avg / 56;
      String spaces = "          ";
      out = out + avg + spaces.substring(0, 9 - Integer.toString(avg).length() - 1);

    }
    output.add(out);
  }


	return output;
  }

  public static LinkedList<String> decryptionOutput(String cipherText, String key)
  {
	LinkedList<String> output = new LinkedList<String>();

  DES0 des = new DES0(cipherText, key);
  des.decrypt();

  output.add("DECRYPTION");
  output.add("Ciphertext C: " + cipherText);
  output.add("Key K: " + key);
  output.add("Plaintext P: " + des.getText());

	return output;
  }

  public static String[] generateFlippedBits(String[] texts, int length)
  {
    String left = "";
    String right = "";
    int pos = 0;

    for(int i = 0; i < length - 1; i++)
    {
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

      char flippedBit = texts[0].charAt(pos);
      String val = "";

      if(flippedBit == '0')
      {
        val = "1";
      }
      else
      {
        val = "0";
      }

      texts[i + 1] = left + val + right;

    }

    return texts;
  }

  public static int compareBits(String in1, String in2)
  {
    int differentBits = 0;

    for(int i = 0; i < in1.length(); i++)
    {
      if(in1.charAt(i) != in2.charAt(i))
      {
          differentBits ++;
      }
    }

    return differentBits;
  }


}
