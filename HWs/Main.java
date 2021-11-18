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

public class Main {

  private static final Scanner STDIN = new Scanner(System.in);
  private static final String title = "Name: Ziyuan Zhang, Email: zzhang949@wisc.edu, LEC: 001\n";
  private static boolean flag = true;

  public static void main(String[] args) {
    System.out.print(title);
    System.out.println("Welcome to the cipher machine!");

    while (flag) {
      menu();
      driver();
    }
  }

  public static void menu() {
    System.out.println("\nWhat you can do:\n"
        + "Type 1: Encrypt a sentence\n"
        + "Type 2: Decode a sentence\n"
        + "Type 3: Decode a txt file\n"
        + "Type 4: Quit the cipher machine");
  }

  public static void wrongCommand() {
    System.out.println("You must enter 1~4 as command.");
  }

  public static String Encrypt(String text, int key) {
    String cipher = "";
    char[] chs = text.toCharArray();
    for (int i = 0; i < chs.length; i++) {
      cipher += String.valueOf((char) (chs[i] + key));
    }

    return cipher;
  }

  public static String Decode(String cipher, int key) {
    String text = "";
    char[] chs = cipher.toCharArray();
    for (int i = 0; i < chs.length; i++) {
      text += String.valueOf((char) (chs[i] - key));
    }

    return text;
  }

  public static void driver() {
    try {
      boolean flag1 = true;
      while (flag1) {
        if (STDIN.hasNextInt()) {
          flag1 = false;
          int command = STDIN.nextInt();
          STDIN.nextLine(); // skip \n
          switch (command) {
            case 1:
              System.out.println("Enter a sentence need to be encrypted:");
              String text = STDIN.nextLine();

              boolean flag2 = true;
              int key1 = 0;
              while (flag2) {
                System.out.println("Enter an integer key between 1~1000 :");
                if (STDIN.hasNextInt()) {
                  flag2 = false;
                  key1 = STDIN.nextInt();
                  if (0 >= key1 || key1 >= 1000) {
                    flag2 = true;
                  }
                } else {
                  STDIN.nextLine();
                }
              }

              System.out.println("The sentence has be encrypted:");
              System.out.println(Encrypt(text, key1));
              break;
            case 2:
              System.out.println("Enter a cipher need to be decoded:");
              String cipher = STDIN.nextLine();
              boolean flag3 = true;
              int key2 = 0;
              while (flag3) {
                System.out.println("Enter the integer key between 1~1000 :");
                if (STDIN.hasNextInt()) {
                  flag3 = false;
                  key2 = STDIN.nextInt();
                  if (0 >= key2 || key2 >= 1000) {
                    flag3 = true;
                  }
                } else {
                  STDIN.nextLine();
                }
              }

              System.out.println("The sentence has be decoded:");
              System.out.println(Decode(cipher, key2));
              break;
            case 3:
              int key3 = 0;
              boolean flag4 = true;
              while (flag4) {
                System.out.print("Enter the integer key between 1~1000: ");
                if (STDIN.hasNextInt()) {
                  flag4 = false;
                  key3 = STDIN.nextInt();

                  if (0 >= key3 || key3 >= 1000) {
                    flag4 = true;
                  }
                }
                STDIN.nextLine(); // skip \n
              }

              boolean flag5 = true;
              while (flag5) {
                System.out.print("Enter an existed encrypted filename need to be decoded: ");
                String fileName = STDIN.nextLine();
                File file = new File(fileName);

                if (file.exists()) {
                  flag5 = false;
                  Scanner inFile = new Scanner(file);
                  PrintWriter pw = new PrintWriter("output.txt");
                  System.out.println("The un-encrypted text is:");
                  while (inFile.hasNextLine()) {
                    String line = inFile.nextLine();
                    System.out.println(Decode(line, key3));
                    pw.println(Decode(line, key3));
                    pw.flush();
                  }
                  inFile.close();
                  pw.close();
                  System.out.println("Decode successful, the result is saved in output.txt");
                }
              }
              break;
            case 4:
              flag = false;
              System.out.println("The machine stops.");
              break;
            default:
              flag1 = true;
          }
        } else {
          STDIN.nextLine();
        }

        if (flag1) {
          System.out.println("Enter 1 or 2 or 3 or 4 only.");
          menu();
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
