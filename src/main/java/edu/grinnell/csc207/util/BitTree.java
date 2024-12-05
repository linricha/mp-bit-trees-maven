package edu.grinnell.csc207.util;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Trees intended to be used in storing mappings between fixed-length 
 * sequences of bits and corresponding values.
 *
 * @author Richard Lin
 */
public class BitTree {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  int totalBits;

  BitTreeNode head;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   *
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
  void checkBitsString(String bits) throws IndexOutOfBoundsException{
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
   *
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
        throw new IndexOutOfBoundsException();
      } // if/else-if/else
    } // for

    return current.value;
  } // get(String, String)

  /**
   *
   */
  public void dump(PrintWriter pen) {

    // BitTreeNode current = this.head;

    // Should initialize with all 0s.
    // int[] bitStrInt = new int[this.totalBits];

    // while(bitStrIntAdditionUpdate(bitStrInt, totalBits)) {

    //   try {
    //     String currentBitStrVal = this.get(this.bitStrArrToString(bitStrInt));
    //     pen.printf("%s,%s", bitStrArrToString(bitStrInt), currentBitStrVal);
    //   } catch (Exception e) {
    //     // do Nothing / Skip over
    //   } // try/catch

    //   bitStrInt[totalBits - 1]++;
    // } // while

    this.BitTreeTraverserNodes(this.head, 1, "", pen);

  } // dump(PrintWriter)

  // level starts at 1
  public void BitTreeTraverserNodes(BitTreeNode node, int level, String currentBitStr, PrintWriter pen) {
    if (node.leftChild == null && node.rightChild == null) {
      try {
        pen.printf("%s,%s\n", currentBitStr, node.value);
      } catch (Exception e) {
        // should not throw an exception since recursion gets correct bit String.
      } // try/catch
    } // if

    if (node.leftChild != null) {
      BitTreeTraverserNodes(node.leftChild, level + 1, currentBitStr + "0", pen);
    } // if

    if (node.rightChild != null) {
      BitTreeTraverserNodes(node.rightChild, level + 1, currentBitStr + "1", pen);
    } // if
  } // BitTreeTraverserNodes(BitTreeNode, int, String, PrintWriter)

  // public String bitStrArrToString(int[] bitStrArr) {
  //   String bitStr = "";

  //   for (int i = 0; i < bitStrArr.length; i++) {
  //     bitStr += Integer.toString(bitStrArr[i]);
  //   } // for
    
  //   return bitStr;
  // } // bitStrArrToString(int[], int)

  // public boolean bitStrIntAdditionUpdate(int[] bitStrInt, int index) {

  //   // checks if element at index need updating.
  //   if (bitStrInt[index] == 2) {
  //     if (index - 1 < 0) {
  //       // Used to indicate that everything has been checked
  //       return false;
  //     } else {
  //       bitStrInt[index - 1]++;
  //       bitStrInt[index] = 0;

  //       // checks if full
  //       // safe to call since index - 1 is checked above
  //       return bitStrIntAdditionUpdate(bitStrInt, index - 1);
  //     } // if/else
  //   } // if

  //   // returns true if no updating needed.
  //   return true;
  // }

  /**
   * Creates a Tree using set and source.
   *
   * @param source A input source containing strings
   * of the format (bits),(val).
   */
  public void load(InputStream source) {

    Scanner look = new Scanner(source);

    while(look.hasNext()) {
      String line = look.nextLine();

      String bits = line.substring(0, totalBits);

      // start at totalBits + 1 since there is a comma (skips over comma)
      String value = line.substring(totalBits + 1);

      this.set(bits, value);
    } // while

    look.close();
  } // load(InputStream)

} // class BitTree
