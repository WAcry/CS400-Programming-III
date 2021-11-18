/**
 * Main.java created by Ziyuan Zhang on Lenovo Yoga 720 in p0 JavaIOPractice
 *
 * Author:    Ziyuan Zhang (zzhang949@wisc.edu)
 * Date:      1/26/2020
 *
 * Course:    CS400
 * Semester:  Spring 2020
 * Lecture:   001
 *
 * IDE:       Eclipse IDE for Java Developers
 * Version:   2019-12 (4.14.0)
 * Build id:  20191212-1212
 *
 * Device:    Ziyuan-Yoga720
 * OS:        Windows 10 Home
 * Version:   1903
 * OS build:  18362.535
 *
 * Description:
 * This program is a cipher machine which could encrypted or decode sentences,
 * and also decode an encrypted txt file.
 */

import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;

/**
 * Main - This class run a cipher machine which could encrypt or decode sentences, and also decode
 * encrypted txt files.
 *
 * @author Ziyuan Zhang (2020)
 */
public class Main {

  private static final Scanner STDIN = new Scanner(System.in);
  private static final String title = "Name: Ziyuan Zhang, Email: zzhang949@wisc.edu, LEC: 001";
  private static boolean flag = true; // false only when the program stops
  private static final int min = 1; // min key value
  private static final int max = 1000; // max key value
  private static final int seed = 1000; // seed number for encryption

  /**
   * main Main method of the program, running as a cipher machine.
   *
   * @param args input arguments
   */
  public static void main(String[] args) {
    // print basic information
    System.out.println(title);
    System.out.println("Welcome to the cipher machine!");

    // keep running the program until the user quit.
    while (flag) {
      menu(); // print menu of command
      driver(); // run the driver
    }
  }

  /**
   * menu Print the command menu the user can enter.
   */
  public static void menu() {
    System.out.println("What you can do:\n"
        + "Type 1: Encrypt a sentence\n"
        + "Type 2: Decode a sentence\n"
        + "Type 3: Decode a txt file\n"
        + "Type 4: Quit the cipher machine");
  }

  /**
   * encrypt Encrypt a given sentence with a given key using basic cipher algorithm.
   *
   * @param text The text need to be encrypted
   * @param key  a key is a integer between pre-set range, used to encrypt and decode
   * @return a cipher which generated from the text and the key
   */
  public static String encrypt(String text, int key) {
    char[] chs = text.toCharArray(); // convert the text to char array

    // do the encryption
    for (int i = 0; i < chs.length; i++) {
      chs[i] = (char) (chs[i] + key + seed);
    }

    return String.valueOf(chs);
  }

  /**
   * decode Decode a given sentence with a given key using basic cipher algorithm.
   *
   * @param cipher The text need to be decoded
   * @param key    a key is a integer between pre-set range, used to encrypt and decode
   * @return an un-encrypted text which decoded from the cipher and the key
   */
  public static String decode(String cipher, int key) {
    char[] chs = cipher.toCharArray(); // convert the text to char array

    for (int i = 0; i < chs.length; i++) {
      chs[i] = (char) (chs[i] - key - seed);
    }

    return String.valueOf(chs);
  }

  /**
   * driver Driver of the cipher machine.
   */
  public static void driver() {
    try {
      boolean flag1 = true; // false when correct command entered
      while (flag1) {  // stop when correct command entered
        if (STDIN.hasNextInt()) { // check if the command is a integer number
          flag1 = false;
          int command = STDIN.nextInt();
          STDIN.nextLine(); // skip \n

          // check if the command is 1 or 2 or 3 or 4, and do corresponding operation.
          switch (command) {
            case 1: // encrypt a sentence
              System.out.println("Enter a sentence need to be encrypted:");
              String text = STDIN.nextLine();

              boolean flag2 = true; // false when the key is correctly entered.
              int key1 = 0; // the key to encrypt

              // stop when the key is correctly entered.
              while (flag2) {
                System.out.println("Enter an integer key between " + min + "~" + max + " :");
                if (STDIN.hasNextInt()) { // check if the key is an integer
                  flag2 = false;
                  key1 = STDIN.nextInt();
                  STDIN.nextLine(); // skip \n
                  if (min > key1 || key1 > max) { // check if the key is between pre-set range
                    System.out.println(
                        "The integer key must between " + min + " ~ " + max + ". Try again!");
                    flag2 = true;
                  }
                } else {
                  STDIN.nextLine(); // skip \n
                  System.out.println("The key must be an integer. Try again!");
                }
              }

              System.out.println("The sentence has be encrypted:");
              System.out.println(encrypt(text, key1)); // encrypt the text with key
              break;
            case 2: // decode a cipher
              System.out.println("Enter a cipher need to be decoded:");
              String cipher = STDIN.nextLine();
              boolean flag3 = true;  // false when the key is correctly entered.
              int key2 = 0;  // the key to decode

              // stop when the key is correctly entered.
              while (flag3) {
                System.out.println("Enter the integer key between " + min + "~" + max + " :");
                if (STDIN.hasNextInt()) { // check if the key is an integer
                  flag3 = false;
                  key2 = STDIN.nextInt();
                  STDIN.nextLine(); // skip \n
                  if (min > key2 || key2 > max) { // check if the key is between pre-set range
                    flag3 = true;
                    System.out.println(
                        "The integer key must between " + min + " ~ " + max + ". Try again!");
                  }
                } else {
                  STDIN.nextLine(); // skip \n
                  System.out.println("The key must be an integer. Try again!");
                }
              }

              System.out.println("The sentence has be decoded:");
              System.out.println(decode(cipher, key2)); // decode the text with key
              break;
            case 3: // Decode an encrypted txt file
              int key3 = 0; // the key to decode
              boolean flag4 = true; // false when the key is correctly entered

              // stop when the key is correctly entered
              while (flag4) {
                System.out.println("Enter the integer key between " + min + "~" + max + " :");
                System.out.println("Notice: Default key of input.txt is 222");
                if (STDIN.hasNextInt()) { // check if the key is an integer
                  flag4 = false;
                  key3 = STDIN.nextInt();
                  STDIN.nextLine(); // skip \n
                  if (min > key3 || key3 > max) { // check if the key is between pre-set range
                    flag4 = true;
                    System.out.println(
                        "The key must between " + min + " ~ " + max + ". Try again!");
                  }
                } else {
                  System.out.println("The key must be an integer. Try again!");
                  STDIN.nextLine(); // skip \n
                }
              }

              boolean flag5 = true; // false when the entered filename is existed

              // stop when the entered filename is existed
              while (flag5) {
                System.out.println("Enter an existed encrypted filename need to be decoded: ");
                String fileName = STDIN.nextLine();
                File file = new File(fileName);

                if (file.exists()) { // check if the entered filename is existed
                  flag5 = false;
                  Scanner inFile = new Scanner(file);
                  PrintWriter pw = new PrintWriter("output.txt");
                  System.out.println("The un-encrypted text is:");

                  // read the file need to be decoded by lines
                  while (inFile.hasNextLine()) {
                    String line = inFile.nextLine();
                    System.out.println(decode(line, key3)); // print the decode result
                    pw.println(decode(line, key3)); // save the decode result in output.txt
                    pw.flush();
                  }

                  // close the Scanner and PrintWriter
                  inFile.close();
                  pw.close();

                  System.out.println("Decode successful, the result is saved in output.txt");
                } else {
                  System.out.println("The file must exist. Try again!");
                }
              }
              break;
            case 4: // quit the machine
              flag = false;
              System.out.println("The machine stops.");
              break;
            default: // the command is incorrectly formatted, re-ask the user to enter
              System.out.println("Wrong command. Try again！");
              flag1 = true;
          }
        } else {
          System.out.println("Wrong command. Try again！");
          STDIN.nextLine(); // skip \n
        }

        if (flag1) { // keep run if the command is incorrectly formatted.
          System.out.println("Enter 1 or 2 or 3 or 4 only.\n");
          menu();
        }
      }
    } catch (Exception e) { // Some IO exception may be caught.
      System.out.println(e.getMessage());
      System.out.println("Something unexpected happened. Please restart the machine.");
    }
  }
}
