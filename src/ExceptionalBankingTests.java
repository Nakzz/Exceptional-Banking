import java.io.File;

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
      System.out.println("testAccountBalance failed");
      fails++;
    }
    if (!testOverdraftCount()) {
      System.out.println("testOverdraftCount failed");
      fails++;
    }
//    
    if(!testTransactionGroupEmpty()) {
      System.out.println("testTransactionGroupEmpty failed");
    fails++;
    }
    
    if(!testTransactionGroupInvalidEncoding()) {
      System.out.println("testTransactionGroupInvalidEncoding failed");
    fails++;
    }
    
    if(!testAccountAddNegativeQuickWithdraw()) {
      System.out.println("testAccountAddNegativeQuickWithdraw failed");
    fails++;
    }
    
    if(!testAccountBadTransactionGroup()) {
      System.out.println("testAccountBadTransactionGroup failed");
    fails++;
    }
    if(!testAccountIndexOutOfBounds()) {
      System.out.println("testAccountIndexOutOfBounds failed");
    fails++;
    }
    
    if(!testAccountMissingFile()) {
      System.out.println("testAccountMissingFile failed");
    fails++;
    }
    


    if (fails == 0)
      System.out.println("All tests passed!");

  }

public static boolean testAccountBalance() {
  Account acc = new Account("TestBalance"); 
  int balance =0;
  
  try {
    acc.addTransactionGroup("1 10 -20 30 -20 -20");      // -20 
  balance = acc.getCurrentBalance();                // correct
  System.out.println("Balace for 1: " + balance);       
  
  acc.addTransactionGroup("0 1 1 1 0 0 1 1 1 1");  // 1 is deposit 0 is subtract so should be +5
  balance = acc.getCurrentBalance();
  System.out.println("Balace 0: " + balance);       // correct
  
  acc.addTransactionGroup("2 1 2 3 0");         // -(20+ 80 + 240) = -340  
  
  balance = acc.getCurrentBalance();
  System.out.println("Balace 2: " + balance);
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
public static boolean testTransactionGroupEmpty() { 
  
  
  int[] transactions = null; 
  int[] transactions2 = new int[] {};
  boolean fail=false;
  
  try {
    TransactionGroup acc = new TransactionGroup(transactions); 
  }
  catch(Exception e) {
    System.out.println(e);
    
    if (!e.getMessage().equalsIgnoreCase("transaction group encoding cannot be null or empty")) fail=true;
    
  }
  
  try {
    TransactionGroup acc = new TransactionGroup(transactions2); 
  }
  catch(Exception e) {
    System.out.println(e);
    if (!e.getMessage().equalsIgnoreCase("transaction group encoding cannot be null or empty")) fail=true;
  }
  
  if(!fail) return true;
  
  return false; }
 
/**
 * This method checks whether the TransactionGroup constructor throws an exception with an
 * appropriate message, when then first int in the provided array is not 0, 1, or 2.
 * @return true when test verifies correct functionality, and false otherwise.
 */
public static boolean testTransactionGroupInvalidEncoding() {   

  int[] transactions = new int[] {5, 9, 4};
  boolean fail=false;
  
  try {
    TransactionGroup acc = new TransactionGroup(transactions); 
  }
  catch(Exception e) {
    System.out.println(e);
    
    if (!e.getMessage().equalsIgnoreCase("the first element within a transaction group must be 0, 1, or 2")) fail=true;
    
  }
  
  if(!fail) return true;
  
  return false; }
 
/**
 * This method checks whether the Account.addTransactionGroup method throws an exception with an
 * appropriate message, when it is passed a quick withdraw encoded group that contains negative
 * numbers of withdraws.
 * @return true when test verifies correct functionality, and false otherwise.
 */
public static boolean testAccountAddNegativeQuickWithdraw() {   
  int[] transactions = new int[] {2, -2, -5, 2, 6};
boolean fail=false;

try {
  TransactionGroup acc = new TransactionGroup(transactions); 
}
catch(Exception e) {
  System.out.println(e);
  
  if (!e.getMessage().equalsIgnoreCase("quick withdraw transaction groups may not contain negative numbers")) fail=true;
  
}


if(!fail) return true;

return false; 

}
 
/**
 * This method checks whether the Account.addTransactionGroup method throws an exception with an
 * appropriate message, when it is passed a string with space separated English words (non-int).
 * @return true when test verifies correct functionality, and false otherwise.
 */
public static boolean testAccountBadTransactionGroup() { 
  Account acc = new Account("testAccountBadTransactionGroup"); 
  boolean fail=false;
  
  try {
    acc.addTransactionGroup("f u c k t h o t s o n g o d @uwmadison");
  }
  catch(Exception e) {
    System.out.println(e);
    
    if (!e.getMessage().equalsIgnoreCase("addTransactionGroup requires string commands that contain only space separated integer values")) fail=true;
  }
  
  if(!fail) return true;
  return false; 

}
 
/**
 * This method checks whether the Account.getTransactionAmount method throws an exception with an
 * appropriate message, when it is passed an index that is out of bounds.
 * @return true when test verifies correct functionality, and false otherwise.
 */
public static boolean testAccountIndexOutOfBounds() { 
  Account acc = new Account("testAccountIndexOutOfBounds"); 
  boolean fail=false;
  int index =2;
  int currTransactionCount = acc.getTransactionCount();
  
  try {
    acc.getTransactionAmount(2);
  }
  catch(Exception e) {
    System.out.println(e);
    
    if (!e.getMessage().equalsIgnoreCase("Trying to access index " + index
      + ", while total transaction count is " + currTransactionCount)) fail=true;
  }
  
  if(!fail) return true;
  return false; 
 }
 
/**
 * This method checks whether the Account constructor that takes a File parameter throws an 
 * exception with an appropriate message, when it is passed a File object that does not correspond
 * to an actual file within the file system.
 * @return true when test verifies correct functionality, and false otherwise.
 */
public static boolean testAccountMissingFile() { 
  
  boolean fail=false;
  File dummyFile = new File("DNE.txt"); 
  try {
    Account acc = new Account(dummyFile); 
  }
  catch(Exception e) {
    System.out.println(e);
    
    if (!e.getMessage().equalsIgnoreCase(dummyFile + " does not exist. Please check the directory.")) fail=true;
  }
  
  if(!fail) return true;
  
  
  return false; }

}
