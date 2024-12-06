package edu.grinnell.csc207.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.management.RuntimeErrorException;
import java.io.IOException;

/**
 * A table that allows translation between ASCII, braille, and unicode.
 *
 * @author Richard Lin
 * @author Samuel A. Rebelsky
 */
public class BrailleAsciiTables {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * Conversions from ASCII to braille.
   */
  static final String a2b =
      "01000001,100000\n"
      + "01000010,110000\n"
      + "01000011,100100\n"
      + "01000100,100110\n"
      + "01000101,100010\n"
      + "01000110,110100\n"
      + "01000111,110110\n"
      + "01001000,110010\n"
      + "01001001,010100\n"
      + "01001010,010110\n"
      + "01001011,101000\n"
      + "01001100,111000\n"
      + "01001101,101100\n"
      + "01001110,101110\n"
      + "01001111,101010\n"
      + "01010000,111100\n"
      + "01010001,111110\n"
      + "01010010,111010\n"
      + "01010011,011100\n"
      + "01010100,011110\n"
      + "01010101,101001\n"
      + "01010110,111001\n"
      + "01010111,010111\n"
      + "01011000,101101\n"
      + "01011001,101111\n"
      + "01011010,101011\n"
      + "01100001,100000\n"
      + "01100010,110000\n"
      + "01100011,100100\n"
      + "01100100,100110\n"
      + "01100101,100010\n"
      + "01100110,110100\n"
      + "01100111,110110\n"
      + "01101000,110010\n"
      + "01101001,010100\n"
      + "01101010,010110\n"
      + "01101011,101000\n"
      + "01101100,111000\n"
      + "01101101,101100\n"
      + "01101110,101110\n"
      + "01101111,101010\n"
      + "01110000,111100\n"
      + "01110001,111110\n"
      + "01110010,111010\n"
      + "01110011,011100\n"
      + "01110100,011110\n"
      + "01110101,101001\n"
      + "01110110,111001\n"
      + "01110111,010111\n"
      + "01111000,101101\n"
      + "01111001,101111\n"
      + "01111010,101011\n"
      + "00100000,000000\n";

  /**
   * Conversions from braille to ASCII.
   */
  static final String b2a =
      "100000,A\n"
      + "110000,B\n"
      + "100100,C\n"
      + "100110,D\n"
      + "100010,E\n"
      + "110100,F\n"
      + "110110,G\n"
      + "110010,H\n"
      + "010100,I\n"
      + "010110,J\n"
      + "101000,K\n"
      + "111000,L\n"
      + "101100,M\n"
      + "101110,N\n"
      + "101010,O\n"
      + "111100,P\n"
      + "111110,Q\n"
      + "111010,R\n"
      + "011100,S\n"
      + "011110,T\n"
      + "101001,U\n"
      + "111001,V\n"
      + "101101,X\n"
      + "101111,Y\n"
      + "101011,Z\n"
      + "010111,W\n"
      + "000000, \n";

  /**
   * Conversions from braille to unicode.
   */
  static final String b2u =
      "000000,2800\n"
      + "100000,2801\n"
      + "010000,2802\n"
      + "110000,2803\n"
      + "001000,2804\n"
      + "101000,2805\n"
      + "011000,2806\n"
      + "111000,2807\n"
      + "000100,2808\n"
      + "100100,2809\n"
      + "010100,280A\n"
      + "110100,280B\n"
      + "001100,280C\n"
      + "101100,280D\n"
      + "011100,280E\n"
      + "111100,280F\n"
      + "000010,2810\n"
      + "100010,2811\n"
      + "010010,2812\n"
      + "110010,2813\n"
      + "001010,2814\n"
      + "101010,2815\n"
      + "011010,2816\n"
      + "111010,2817\n"
      + "000110,2818\n"
      + "100110,2819\n"
      + "010110,281A\n"
      + "110110,281B\n"
      + "001110,281C\n"
      + "101110,281D\n"
      + "011110,281E\n"
      + "111110,281F\n"
      + "000001,2820\n"
      + "100001,2821\n"
      + "010001,2822\n"
      + "110001,2823\n"
      + "001001,2824\n"
      + "101001,2825\n"
      + "011001,2826\n"
      + "111001,2827\n"
      + "000101,2828\n"
      + "100101,2829\n"
      + "010101,282A\n"
      + "110101,282B\n"
      + "001101,282C\n"
      + "101101,282D\n"
      + "011101,282E\n"
      + "111101,282F\n"
      + "000011,2830\n"
      + "100011,2831\n"
      + "010011,2832\n"
      + "110011,2833\n"
      + "001011,2834\n"
      + "101011,2835\n"
      + "011011,2836\n"
      + "111011,2837\n"
      + "000111,2838\n"
      + "100111,2839\n"
      + "010111,283A\n"
      + "110111,283B\n"
      + "001111,283C\n"
      + "101111,283D\n"
      + "011111,283E\n"
      + "111111,283F\n";

  // +---------------+-----------------------------------------------
  // | Static fields |
  // +---------------+

  /**
   * Stores the BitTree for ASCII to braille translation.
   */
  static BitTree a2bTree = null;

  /**
   * Stores the BitTree for braille to ASCII translation.
   */
  static BitTree b2aTree = null;

  /**
   * Stores the BitTree for braille to Unicode translation.
   */
  static BitTree b2uTree = null;

  // +-----------------------+---------------------------------------
  // | Static helper methods |
  // +-----------------------+

  // Adds zeros to front of str depending on length disparity between intended and str
  // Assume that length is always less than str

  /**
   * Adds zero to str based on the difference between the intended length of the str
   * (length) and the actual length of str.
   *
   * @param length The intended length of str.
   * @param str The string to be checked.
   * @return Str with extras 0s in the front it, so that the returned string
   * has length length.
   */
  static String strAddZerosToFront(int length, String str) {

    String zerosStr = "";
    String zero = "0";

    // Computes and creates a string of the missing number of zeros.
    int numZeros = length - str.length();
    while (numZeros > 0) {
      zerosStr += zero;
      numZeros--;
    } // while

    return zerosStr + str;
  } // strAddZerosToFront(int, String)

  /**
   * Returns the string associated with bitStr as stored in bitTree.
   * Throws an exception if bitStr does not exist in bitTree.
   *
   * @param bitTree A BitTree that stores strings.
   * @param bitStr A string used to get the value stored in bitTree.
   * @return A string.
   * @throws RuntimeErrorException An exception
   */
  static String returnStrOrThrowException(BitTree bitTree, String bitStr)
      throws RuntimeErrorException {
    try {
      return bitTree.get(bitStr);
    } catch (Exception e) {
      throw new RuntimeException();
    } // try/catch
  } // returnStrOrThrowException(BitTree, String)

  // +----------------+----------------------------------------------
  // | Static methods |
  // +----------------+

  /**
   * Returns a String of the translation of a letter into braille.
   *
   * First if statement made by Sam R
   * (with exception to changes in variable names and a certain number).
   *
   * @param letter The char to be translated.
   * @return A String representing braille.
   * @throws RuntimeErrorException An exception.
   */
  public static String toBraille(char letter) throws RuntimeErrorException {

    // Make sure we've loaded the ASCII-to-braille tree.
    if (null == a2bTree) {
      a2bTree = new BitTree(8);
      InputStream a2bStream = new ByteArrayInputStream(a2b.getBytes());
      a2bTree.load(a2bStream);
      try {
        a2bStream.close();
      } catch (IOException e) {
        // We don't care if we can't close the stream.
      } // try/catch
    } // if

    String bitCharStr = strAddZerosToFront(8, Integer.toBinaryString(letter));

    return returnStrOrThrowException(a2bTree, bitCharStr);
  } // toBraille(char)

  /**
   * Returns a String of the translation of braille into a letter.
   *
   * First if statement made by Sam R
   * (with exception to changes in variable names and a certain number).
   *
   * @param bits The string of bits (braille) to be translated.
   * @return A String representing a letter.
   * @throws RuntimeErrorException An exception.
   */
  public static String toAscii(String bits) throws RuntimeErrorException {

    // Make sure we've loaded the braille-to-ASCII tree.
    if (null == b2aTree) {
      b2aTree = new BitTree(6);
      InputStream b2aStream = new ByteArrayInputStream(b2a.getBytes());
      b2aTree.load(b2aStream);
      try {
        b2aStream.close();
      } catch (IOException e) {
        // We don't care if we can't close the stream.
      } // try/catch
    } // if

    // change method to better name like getStrInTree
    return returnStrOrThrowException(b2aTree, bits);
  } // toAscii(String)

  /**
   * Returns a String of the translation of braille into a unicode character.
   *
   * First if statement made by Sam R
   * (with exception to changes in variable names and a certain number).
   *
   * @param bits The string of bits (braille) to be translated.
   * @return A String representing a unicode character.
   * @throws RuntimeException An exception.
   */
  public static String toUnicode(String bits) throws RuntimeException {

    // Make sure we've loaded the braille-to-unicode tree.
    if (null == b2uTree) {
      b2uTree = new BitTree(6);
      InputStream b2uStream = new ByteArrayInputStream(b2u.getBytes());
      b2uTree.load(b2uStream);
      try {
        b2uStream.close();
      } catch (IOException e) {
        // We don't care if we can't close the stream.
      } // try/catch
    } // if

    String unicodeStr = returnStrOrThrowException(b2uTree, bits);

    return new String(Character.toChars(Integer.parseInt(unicodeStr, 16)));
  } // toUnicode(String)
} // class BrailleAsciiTables
