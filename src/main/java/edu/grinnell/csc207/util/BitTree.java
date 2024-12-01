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
   * Creates a BitTreeNode child for node based on the value of val
   * and gets set to the child node.
   *
   * @param val A char of '1' or '0'.
   * @param node A node that gets a child and then gets set to the child.
   */
  void branchCreator (char val, BitTreeNode node) {
    if (val == '0') {
      node.leftChild = new BitTreeNode();
      node = node.leftChild;
    } else if (val == '1') {
      node.rightChild = new BitTreeNode();
      node = node.rightChild;
    } // if/else-if
  } // branchCreator(char, BitTreeNode)

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
      if (bits.charAt(i) != '0' && bits.charAt(i) != '0') {
        throw new IndexOutOfBoundsException();
      } // if
    } // for
  } // checkBitsString(String)

  /**
   * Checks that the next node that need to be reached exists as 
   * indicated by bit. Throws an exception if bit does not indicate
   * correctly or node does not exist.
   *
   * @param bit The value indicating where node should traverse to next.
   * @param node The node that is traversing.
   * @throws IndexOutOfBoundsException An exception.
   */
  void BitTreeTraverser(char bit, BitTreeNode node) throws IndexOutOfBoundsException{
    if (bit == '0' && !(node.leftChild.equals(null))) {
      node = node.leftChild;
    } else if (bit == '1' && !(node.rightChild.equals(null))) {
      node = node.rightChild;
    } else {
      throw new IndexOutOfBoundsException();
    } // if/else-if/else

  } // BitTreeTraverser

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   *
   */
  public void set(String bits, String value) throws IndexOutOfBoundsException {

    BitTreeNode current = this.head;

    this.incorrectBitLength(bits);
    this.checkBitsString(bits);

    for (int i = 0; i < bits.length(); i ++) {
      this.branchCreator(bits.charAt(i), current);
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
  public String get(String bits) {

    BitTreeNode current = this.head;
  
    this.incorrectBitLength(bits);

    // Reaches the end of the Tree if no exceptions thrown.
    for (int i = 0; i < bits.length(); i++) {
      BitTreeTraverser(bits.charAt(i), current);
    } // for

    return current.value;
  } // get(String, String)

  /**
   *
   */
  public void dump(PrintWriter pen) {
    // STUB
  } // dump(PrintWriter)

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
      String value = line.substring(totalBits + 1);

      this.set(bits, value);
    } // while

    look.close();
  } // load(InputStream)

} // class BitTree
