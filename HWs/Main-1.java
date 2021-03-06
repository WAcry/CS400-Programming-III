//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: p0 JavaIOPractice
// Files: Main.java
// Course: COMP SCI 400 LEC 001, Spring Semester, 2020
//
// Author: Ziyuan Zhang
// Email: zzhang949@wisc.edu
// Lecturer's Name: Debra Deppeler
//
// Description:
// This program is a cipher machine which could encrypted or decode sentences,
// and also decode an encrypted txt file.
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;

/**
 * This class run a cipher machine which could encrypt or decode sentences, and also decode
 * encrypted txt files.
 *
 * @author Ziyuan Zhang
 */
public class Main {

  private static final Scanner STDIN = new Scanner(System.in);
  private static final String title = "Name: Ziyuan Zhang, Email: zzhang949@wisc.edu, LEC: 001";
  private static boolean flag = true; // false only when the program stops
  private static final int min = 1;
  private static final int max = 1000;

  /**
   * Main method of the program, running as a cipher machine.
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
      driver(); // run driver()
    }
  }

  /**
   * Print the command menu the user can enter.
   */
  public static void menu() {
    System.out.println("\nWhat you can do:\n"
        + "Type 1: Encrypt a sentence\n"
        + "Type 2: Decode a sentence\n"
        + "Type 3: Decode a txt file\n"
        + "Type 4: Quit the cipher machine");
  }

  /**
   * Encrypt a given sentence with a given key using basic cipher algorithm.
   *
   * @param text The text need to be encrypted
   * @param key  a key is a integer between 1~1000, used to encrypt and decode
   * @return a cipher which generated from the text and the key
   */
  public static String Encrypt(String text, int key) {
    String cipher = "";
    char[] chs = text.toCharArray(); // convert the text to char array

    // do the encryption
    for (int i = 0; i < chs.length; i++) {
      cipher += String.valueOf((char) (chs[i] + key));
    }

    return cipher;
  }

  /**
   * Decode a given sentence with a given key using basic cipher algorithm.
   *
   * @param cipher The text need to be decoded
   * @param key    a key is a integer between 1~1000, used to encrypt and decode
   * @return an un-encrypted text which decoded from the cipher and the key
   */
  public static String Decode(String cipher, int key) {
    String text = "";
    char[] chs = cipher.toCharArray(); // convert the text to char array

    // do the decoding operation
    for (int i = 0; i < chs.length; i++) {
      text += String.valueOf((char) (chs[i] - key));
    }

    return text;
  }

  /**
   * Driver of the cipher machine.
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
                System.out.println("Enter an integer key between 1~1000 :");
                if (STDIN.hasNextInt()) { // check if the key is an integer
                  flag2 = false;
                  key1 = STDIN.nextInt();
                  if (min > key1 || key1 > max) { // check if the key is between 1~1000
                    flag2 = true;
                  }
                } else {
                  STDIN.nextLine(); // skip \n
                }
              }

              System.out.println("The sentence has be encrypted:");
              System.out.println(Encrypt(text, key1)); // encrypt the text with key
              break;
            case 2: // decode a cipher
              System.out.println("Enter a cipher need to be decoded:");
              String cipher = STDIN.nextLine();
              boolean flag3 = true;  // false when the key is correctly entered.
              int key2 = 0;  // the key to decode

              // stop when the key is correctly entered.
              while (flag3) {
                System.out.println("Enter the integer key between 1~1000 :");
                if (STDIN.hasNextInt()) { // check if the key is an integer
                  flag3 = false;
                  key2 = STDIN.nextInt();
                  if (min > key2 || key2 > max) { // check if the key is between 1~1000
                    flag3 = true;
                  }
                } else {
                  STDIN.nextLine(); // skip \n
                }
              }

              System.out.println("The sentence has be decoded:");
              System.out.println(Decode(cipher, key2)); // decode the text with key
              break;
            case 3: // Decode an encrypted txt file
              int key3 = 0; // the key to decode
              boolean flag4 = true; // false when the key is correctly entered

              // stop when the key is correctly entered
              while (flag4) {
                System.out.print("Enter the integer key between 1~1000: ");
                if (STDIN.hasNextInt()) { // check if the key is an integer
                  flag4 = false;
                  key3 = STDIN.nextInt();

                  if (min > key3 || key3 > max) { // check if the key is between 1~1000
                    flag4 = true;
                  }
                }
                STDIN.nextLine(); // skip \n
              }

              boolean flag5 = true; // false when the entered filename is existed

              // stop when the entered filename is existed
              while (flag5) {
                System.out.print("Enter an existed encrypted filename need to be decoded: ");
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
                    System.out.println(Decode(line, key3)); // print the decode result
                    pw.println(Decode(line, key3)); // save the decode result in output.txt
                    pw.flush();
                  }

                  // close the Scanner and PrintWriter
                  inFile.close();
                  pw.close();

                  System.out.println("Decode successful, the result is saved in output.txt");
                }
              }
              break;
            case 4: // quit the machine
              flag = false;
              System.out.println("The machine stops.");
              break;
            default: // the command is incorrectly formatted, re-ask the user to enter
              flag1 = true;
          }
        } else {
          STDIN.nextLine(); // skip \n
        }

        if (flag1) { // keep run if the command is incorrectly formatted.
          System.out.println("Enter 1 or 2 or 3 or 4 only.");
          menu();
        }
      }
    } catch (Exception e) { // Some IO exception may be caught.
      System.out.println(e.getMessage());
    }
  }
}
