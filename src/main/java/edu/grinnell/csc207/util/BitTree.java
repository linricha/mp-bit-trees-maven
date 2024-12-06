package edu.grinnell.csc207.util;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Trees intended to be used in storing mappings between fixed-length
 * sequences of bits and corresponding values.
 *
 * @author Richard Lin
 * @author Samuel A. Rebelsky
 */
public class BitTree {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The total length of the bit Strings.
   */
  int totalBits;

  /**
   * The start/head node of the BitTree.
   */
  BitTreeNode head;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Constructs a BitTree that takes bit Strings of length n.
   *
   * @param n The number of total bits.
   */
  public BitTree(int n) {
    this.totalBits = n;
  } // BitTree(int)

  // +---------------+-----------------------------------------------
  // | Local helpers |
  // +---------------+

  /**
   * Throws an Exception if the length if bits is incorrect.
   *
   * @param bits The String to check
   * @throws IndexOutOfBoundsException An exception.
   */
  void incorrectBitLength(String bits) throws IndexOutOfBoundsException {
    if (bits.length() != this.totalBits) {
      throw new IndexOutOfBoundsException();
    } // if
  } // incorrectBitLength(String)

  /**
   * Checks that bits contains only 1's and 0's.
   *
   * @param bits The String to check.
   * @throws IndexOutOfBoundsException An exception.
   */
  void checkBitsString(String bits) throws IndexOutOfBoundsException {
    for (int i = 0; i < bits.length(); i++) {
      if (bits.charAt(i) != '1' && bits.charAt(i) != '0') {
        throw new IndexOutOfBoundsException();
      } // if
    } // for
  } // checkBitsString(String)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Creates a branch in the tree starting from this.head using the character values
   * stored in bits, setting the value of the node at the end of the branch with value.
   *
   * @param bits The string of bits to read.
   * @param value The string to be set as the value at the end of the branch.
   * @throws IndexOutOfBoundsException An exception.
   */
  public void set(String bits, String value) throws IndexOutOfBoundsException {

    if (this.head == null) {
      this.head = new BitTreeNode();
    } // if

    BitTreeNode current = this.head;

    this.incorrectBitLength(bits);
    this.checkBitsString(bits);

    // Builds out a branch of the tree for the given bits
    for (int i = 0; i < bits.length(); i++) {

      // Creates the orientation of the branch and
      // iterates down the branch
      if (bits.charAt(i) == '0') {
        if (current.leftChild == null) {
          current.leftChild = new BitTreeNode();
        } // if
        current = current.leftChild;
      } else if (bits.charAt(i) == '1') {
        if (current.rightChild == null) {
          current.rightChild = new BitTreeNode();
        } // if
        current = current.rightChild;

      } // if/else-if
    } // for

    //Sets the node at the end with a value.
    current.value = value;

  } // set(String, String)

  /**
   * Gets the value associated with bits for the BitTree
   * by traversing through the tree with for each character of
   * bits.
   *
   * @param bits The String that indicates how to traverse the tree.
   * @return The value at the end of the tree.
   * @throws IndexOutOfBoundsException An Exception.
   */
  public String get(String bits) throws IndexOutOfBoundsException {

    BitTreeNode current = this.head;

    this.incorrectBitLength(bits);
    this.checkBitsString(bits);

    // Reaches the end of the Tree if no exceptions thrown.
    for (int i = 0; i < bits.length(); i++) {
      if (bits.charAt(i) == '0' && current.leftChild != null) {
        current = current.leftChild;
      } else if (bits.charAt(i) == '1' && current.rightChild != null) {
        current = current.rightChild;
      } else {
        throw new IndexOutOfBoundsException(); // maybe Remove
      } // if/else-if/else
    } // for

    return current.value;
  } // get(String)

  /**
   * Prints out the contents of the branch in the form:
   * BitString,value.
   *
   * @param pen Used to print.
   */
  public void dump(PrintWriter pen) {
    this.bitTreeTraverserNodes(this.head, "", pen);

  } // dump(PrintWriter)

  /**
   * A helper function of dump that recursively looks at the current branches in
   * the Bit Tree and prints the Bit String created from traversing down the
   * branches of the Bit Tree and the value.
   *
   * @param node The current node to look at.
   * @param currentBitStr The current Bit String formed from traversing
   * one branch of the BitTree.
   * @param pen Used for printing.
   */
  public void bitTreeTraverserNodes(BitTreeNode node, String currentBitStr, PrintWriter pen) {
    if (node.leftChild == null && node.rightChild == null) {
      try {
        pen.printf("%s,%s\n", currentBitStr, node.value);
      } catch (Exception e) {
        // should not throw an exception since recursion gets correct bit String.
      } // try/catch
    } // if

    if (node.leftChild != null) {
      bitTreeTraverserNodes(node.leftChild, currentBitStr + "0", pen);
    } // if

    if (node.rightChild != null) {
      bitTreeTraverserNodes(node.rightChild, currentBitStr + "1", pen);
    } // if
  } // bitTreeTraverserNodes(BitTreeNode, String, PrintWriter)

  /**
   * Creates a Tree using set and source.
   *
   * @param source A input source containing strings
   * of the format (bits),(val).
   */
  public void load(InputStream source) {

    Scanner look = new Scanner(source);

    // Reads through each line of the InputStream.
    while (look.hasNext()) {
      String line = look.nextLine();

      String bits = line.substring(0, totalBits);

      // start at totalBits + 1 since there is a comma (skips over comma)
      String value = line.substring(totalBits + 1);

      this.set(bits, value);
    } // while

    look.close();
  } // load(InputStream)

} // class BitTree
