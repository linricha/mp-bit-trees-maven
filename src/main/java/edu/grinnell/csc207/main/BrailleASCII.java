package edu.grinnell.csc207.main;

import java.io.PrintWriter;
import edu.grinnell.csc207.util.BrailleAsciiTables;

/** NEED TO ADD AUTHORS TO EVERYTHING AND COMMENTS
 *
 */
public class BrailleASCII {
  // +------+--------------------------------------------------------
  // | Main |
  // +------+

  /**
   *
   */
  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true);
    PrintWriter error = new PrintWriter(System.err, true);

    runTranslator(args, pen, error);

    pen.close();
    error.close();
  } // main(String[]

  static void runTranslator(String[] args, PrintWriter pen, PrintWriter error) {
    if (args.length != 2) {
      error.println("Invalid number of Command-line Arguments. Need Only 2.");
      return;
    } else {

      try {
        if (args[1].compareTo("ascii") == 0) {
          printMessage(args[1], pen, error, 0);

        } else if (args[1].compareTo("braille") == 0) {
          printMessage(args[1], pen, error, 1);

        } else if (args[1].compareTo("unicode") == 0) {
          printMessage(args[1], pen, error, 2);

        } else {
          error.println("Invalid translation option. Valid ones are: \'ascii\',\'braille\', or \'unicode\'.");
        } // if/else-if/else-if/else
      } catch (Exception e) {
        error.println("\nTrouble translating because No corresponding value."); // clear failed translation by doing \n before
      } // try/catch
    } // if/else

  }

  // ascii = 0, braille = 1, unicode = 2
  static void printMessage(String message, PrintWriter pen, PrintWriter error, int mode) {
    int bitLength = 1; // default is braille.

    if (mode == 0 || mode == 2) { // for ascii / unicode 
      bitLength = 6;
    } // if

    if (message.length() % bitLength != 0) {
      error.println("Invalid length of bit string.");
      return;
    } // if

    if (mode == 0) {
      for (int i = 0; i < message.length() /  bitLength; i += bitLength) {
        pen.print(BrailleAsciiTables.toAscii(message.substring(i, i + bitLength)));
      } // for
    } else if (mode == 1) {
      for (int i = 0; i < message.length() /  bitLength; i += bitLength) {
        pen.print(BrailleAsciiTables.toBraille(message.charAt(i)));
      } // for
    } else { // mode == 2
      for (int i = 0; i < message.length() /  bitLength; i += bitLength) {
        pen.println(BrailleAsciiTables.toUnicode(message.substring(i, i + bitLength)));
      } // for
    } // if/else-if/else-if

    pen.flush();
    pen.println();
  } // printMessage(String, PrintWriter, PrintWriter, int)

} // class BrailleASCII
