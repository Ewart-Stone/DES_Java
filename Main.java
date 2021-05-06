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
	
	long start = System.currentTimeMillis();

    LinkedList<String> encryptionOutput = new LinkedList<String>();
    LinkedList<String> decryptionOutput = new LinkedList<String>();

	Scanner input = new Scanner(System.in);

    //encryption and avalanche analysis
	
	//get file input from user
	System.out.println("Enter the file path and name for the encryption file. i.e. test.txt ");
	String in = input.nextLine();
	
    //get inputs from file
    String[] lines = readFromFile(in);
    plainText[0] = lines[0];
    keys[0] = lines[1];


    //generate 64 other plaintexts
    //generate 56 other keys
    generateFlippedBits(plainText, 65);
    generateFlippedBits(keys, 57);

    //avalanche analysis function
    encryptionOutput = encryptionOutput(plaintext, keys, start);

    //write data to encryption file
    writeToFile(fileName, encryptionOutput);

    //decryption
	
	//get file input from user
	System.out.println("Enter the file path and name for the decryption file. i.e. text.txt ");
	in = input.nextLine();
	
    //get inputs from file
    String[] lines = readFromFile(in);
    cipherText = lines[0];
    cipherKey = lines[1];



    //get decryption file output
    decryptionOutput = decryptionOutput(cipherText, cipherKey);

    //write data to decryption file
    writeToFile(fileName, decryptionOutput);

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
			String data = nextLine();
			
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
			writer.write(output.get(i));
		}
		writer.close();
	}
	catch (IOEception e)
	{
		System.out.println("An error has occured");
	}
  }

  public static LinkedList<String> encryptionOutput(String[] plaintext, String[] keys, long start)
  {
	LinkedList<String> output = new LinkedList<String>();
	
	//after encryption
	long end = System.currentTimeMillis();
	long elapsedTime = start - end;
	
	return output;
  }

  public static LinkedList<String> decryptionOutput(String cipherText, String key)
  {
	LinkedList<String> output = new LinkedList<String>();
	
	return output;
  }

  public static String[] generateFlippedBits(String[] texts, int length)
  {
    for(int i = 1; i < length - 1; i++)
    {
      String left = text[0].substring(0,i);

      if(i == length - 2)
      {
        String right = "";
      }
      else
      {
        String right = text[0].substring(i + 1);
      }

      char flippedBit = text[0].charAt(0);

      if(flippedBit == 0)
      {
        flippedBit = 1;
      }
      else
      {
        flippedBit = 0;
      }

      texts[i] = left + String.valueOf(flippedBit) + right;

    }

    return texts;
  }

  public static int compareBits(String in1, String in2)
  {
    int differentBits = 0;

    for(int i = 0; i < in1.size(); i++)
    {
      if(in1.charAt(i) != in2.charAt(i))
      {
          differentBits ++;
      }
    }

    return differentBits;
  }


}
