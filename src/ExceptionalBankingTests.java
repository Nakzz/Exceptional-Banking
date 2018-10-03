//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: AccessControl
// Files: AccessControl.java, AccessControlTest.java, User.java
// Course: CS300, Fall 2018
//
// Author: Ajmain Naqib
// Email: naqib@wisc.edu
// Lecturer's Name: Gary Dahl
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: (identify each person and describe their help in detail)
// Online Sources:
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

public class ExceptionalBankingTests {

  public static void main(String[] args) {
    int fails = 0;
    if (!testAccountBalance()) {
      System.out.println("testLogin1 [bad username] failed");
      fails++;
    }
//    if (!testOverdraftCount()) {
//      System.out.println("testLogin2 [good login] failed");
//      fails++;
//    }
//    if (!testResetPassword()) {
//      System.out.println("testResetPassword failed");
//      fails++;
    
//    }

    if (fails == 0)
      System.out.println("All tests passed!");

  }

public static boolean testAccountBalance() {
  Account acc = new Account("TestBalance"); 
  int balance =0;
  
  try {
    acc.addTransactionGroup("5 10 -20 30 -20 -20");      // -20 
//  balance = acc.getCurrentBalance();                // correct
//  System.out.println("Balace for 1: " + balance);       
  
  acc.addTransactionGroup("0 1 1 1 0 0 1 1 1 1");  // 1 is deposit 0 is subtract so should be +5
//  balance = acc.getCurrentBalance();
//  System.out.println("Balace 0: " + balance);       // correct
  
  acc.addTransactionGroup("2 1 2 3 0");         // -(20+ 80 + 240) = -340  
  
  balance = acc.getCurrentBalance();
//  System.out.println("Balace 2: " + balance);
  }
  catch(Exception e) {
    System.out.println(e);
  }

  
  
  if (balance == -355) {
    return true;
  }
  
  return false; }

public static boolean testOverdraftCount() {
  Account acc = new Account("TestOverdraft"); 
  int overdraft =0;
  
  try {
    acc.addTransactionGroup("1 10 -20 35 -20 -20");      //o: 3 b: -15
    acc.addTransactionGroup("0 1 1 1 0 0 1 1 1 1");  // o: 5 b: -10
    acc.addTransactionGroup("1 135 ");      //o: 5 b: 120
    acc.addTransactionGroup("2 1 2 3 0");         //o: 6 b: -220  
  }
  catch(Exception e) {
    System.out.println(e);
  }
 
  overdraft= acc.getNumberOfOverdrafts();
  
  if (overdraft == 6) {
    return true;
  }
  
  
  return false; }

/**
 * This method checks whether the TransactionGroup constructor throws an exception with an
 * appropriate message, when it is passed an empty int[].
 * @return true when test verifies correct functionality, and false otherwise.
 */
public static boolean testTransactionGroupEmpty() { return false; }
 
/**
 * This method checks whether the TransactionGroup constructor throws an exception with an
 * appropriate message, when then first int in the provided array is not 0, 1, or 2.
 * @return true when test verifies correct functionality, and false otherwise.
 */
public static boolean testTransactionGroupInvalidEncoding() { return false; }
 
/**
 * This method checks whether the Account.addTransactionGroup method throws an exception with an
 * appropriate message, when it is passed a quick withdraw encoded group that contains negative
 * numbers of withdraws.
 * @return true when test verifies correct functionality, and false otherwise.
 */
public static boolean testAccountAddNegativeQuickWithdraw() { return false; }
 
/**
 * This method checks whether the Account.addTransactionGroup method throws an exception with an
 * appropriate message, when it is passed a string with space separated English words (non-int).
 * @return true when test verifies correct functionality, and false otherwise.
 */
public static boolean testAccountBadTransactionGroup() { return false; }
 
/**
 * This method checks whether the Account.getTransactionAmount method throws an exception with an
 * appropriate message, when it is passed an index that is out of bounds.
 * @return true when test verifies correct functionality, and false otherwise.
 */
public static boolean testAccountIndexOutOfBounds() { return false; }
 
/**
 * This method checks whether the Account constructor that takes a File parameter throws an 
 * exception with an appropriate message, when it is passed a File object that does not correspond
 * to an actual file within the file system.
 * @return true when test verifies correct functionality, and false otherwise.
 */
public static boolean testAccountMissingFile() { return false; }

}
