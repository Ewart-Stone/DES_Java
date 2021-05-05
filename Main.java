import java.util.*;

public class Main
{
  public static void main(String[] args)
  {
    double runTime = 0;
    String[] plainText = new String[65];
    String[] keys = new String[57];
    String cipherText = "";
    String cipherKey = "";

    LinkedList<String> encryptionOutput = new LinkedList<String>;
    LinkedList<String> decryptionOutput = new LinkedList<String>;

    //encryption and avalanche analysis
    //get inputs from file
    String[] lines = readFromFile();
    plainText[0] = lines[0];
    keys[0] = lines[1];


    //generate 64 other plaintexts
    //generate 56 other keys
    generateFlippedBits(plainText, 65);
    generateFlippedBits(keys, 57);

    //avalanche analysis function
    encryptionOutput = encryptionOutput(plaintext, keys);

    //write data to encryption file
    writeToFile(fileName, encryptionOutput);

    //decryption
    String[] lines = readFromFile();
    cipherText = lines[0];
    cipherKey = lines[1];

    //get decryption file output
    decryptionOutput = decryptionOutput(cipherText, cipherKey);

    //write data to decryption file
    writeToFile(fileName, decryptionOutput);

    //end
  }

  public String[] readFromFile(String fileName)
  {
    String[] lines = new String[2];

    return lines;
  }

  public String[] writeToFile(String fileName, LinkedList<String> output)
  {
    String[] lines = new String[2];

    return lines;
  }

  public LinkedList<String> encryptionOutput(String[] plaintext, String[] keys)
  {

  }

  public LinkedList<String> decryptionOutput(String cipherText, String key)
  {

  }

  public String[] generateFlippedBits(String[] texts, int length)
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

  public int compareBits(String in1, String in2)
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
