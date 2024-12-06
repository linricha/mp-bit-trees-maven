package edu.grinnell.csc207.main;

import java.io.PrintWriter;
import edu.grinnell.csc207.util.BrailleAsciiTables;

/**
 * A class that runs a translator that translates from 
 * braille to ascii, braille to unicode, and ascii to braille.
 *
 * @author Richard Lin
 */
public class BrailleASCII {
  // +------+--------------------------------------------------------
  // | Main |
  // +------+

  /**
   * Translates from braille to ascii, braille to unicode, and ascii to braille.
   *
   * @param args Command-line Arguments.
   */
  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true);
    PrintWriter error = new PrintWriter(System.err, true);

    runTranslator(args, pen, error);

    pen.close();
    error.close();
  } // main(String[]

  /**
   * Runs a translator that works when given the correct parameters,
   * i.e. args has length 2 and its elements have a certain form.
   *
   * @param args The command-line arguments.
   * @param pen Used for printing.
   * @param error Prints errors.
   */
  static void runTranslator(String[] args, PrintWriter pen, PrintWriter error) {

    // Check that the command-line argument has a length of two.
    if (args.length != 2) {
      error.println("Invalid number of Command-line Arguments. Need Only 2.");
      return;
    } else {

      try {
        if (args[0].compareTo("ascii") == 0) {
          printMessage(args[1], pen, error, 0);

        } else if (args[0].compareTo("braille") == 0) {
          printMessage(args[1], pen, error, 1);

        } else if (args[0].compareTo("unicode") == 0) {
          printMessage(args[1], pen, error, 2);

        } else {
          error.println("Invalid translation option. Valid ones are: \'ascii\',\'braille\', or \'unicode\'.");
        } // if/else-if/else-if/else
      } catch (Exception e) {
        error.println("\nTrouble translating because No corresponding value."); // clear failed translation by doing \n before
      } // try/catch
    } // if/else

  } // runTranslator(String[], PrintWriter, PrintWriter)


  /**
   * Prints message translated into ascii, braille, or unicode as defined by mode.
   * Where mode = 0 for ascii, 1 for braille, and 2 for unicode.
   *
   * @param message A String to be translated.
   * @param pen Used for printing.
   * @param error Prints errors.
   * @param mode An int indicating how message should be translated.
   */
  static void printMessage(String message, PrintWriter pen, PrintWriter error, int mode) {
    
    // Assigns the length of each individual letter depending on the mode.
    int bitLength = 1; // default is braille.

    if (mode == 0 || mode == 2) { // for ascii / unicode 
      bitLength = 6;
    } // if

    // Message length does not contain the correct number of letters
    // as given by the length of each letter
    if (message.length() % bitLength != 0) {
      error.println("Invalid length of bit string.");
      return;
    } // if

    // Translate depending on mode
    if (mode == 0) {
      for (int i = 0; i < message.length(); i += bitLength) {
        pen.print(BrailleAsciiTables.toAscii(message.substring(i, i + bitLength)));
      } // for
    } else if (mode == 1) {
      for (int i = 0; i < message.length(); i += bitLength) {
        pen.print(BrailleAsciiTables.toBraille(message.charAt(i)));
      } // for
    } else { // mode == 2
      for (int i = 0; i < message.length(); i += bitLength) {
        pen.print(BrailleAsciiTables.toUnicode(message.substring(i, i + bitLength)));
      } // for
    } // if/else-if/else-if

    pen.flush();
    pen.println();
  } // printMessage(String, PrintWriter, PrintWriter, int)

} // class BrailleASCII
