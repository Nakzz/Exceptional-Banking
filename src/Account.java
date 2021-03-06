import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class Account {

  private static final int MAX_GROUPS = 10000;
  private static int nextUniqueId = 1000;

  private String name;
  private final int UNIQUE_ID;
  private TransactionGroup[] transactionGroups;
  private int transactionGroupsCount;

  public Account(String name) {
    this.name = name;
    this.UNIQUE_ID = Account.nextUniqueId;
    Account.nextUniqueId++;
    this.transactionGroups = new TransactionGroup[MAX_GROUPS];
    this.transactionGroupsCount = 0;
  }

  public Account(File file) throws FileNotFoundException {
    // NOTE: THIS METHOD SHOULD NOT BE CALLED MORE THAN ONCE, BECAUSE THE
    // RESULTING BEHAVIOR IS NOT DEFINED WITHIN THE JAVA SPECIFICATION ...
//    Scanner in; = new Scanner(System.in);
    Scanner in;
    
    // TODO: ... THIS WILL BE UPDATED TO READ FROM A FILE INSTEAD OF SYSTEM.IN.
    try {
      in = new Scanner(file);
    }
    catch(FileNotFoundException e) {
      throw new FileNotFoundException( file +" does not exist. Please check the directory.");
    }
    finally {
      in = new Scanner(System.in);
    }
    
    this.name = in.nextLine();
    this.UNIQUE_ID = Integer.parseInt(in.nextLine());
    Account.nextUniqueId = this.UNIQUE_ID + 1;
    this.transactionGroups = new TransactionGroup[MAX_GROUPS];
    this.transactionGroupsCount = 0;
    String nextLine = "";
    while (in.hasNextLine())
      try {
        this.addTransactionGroup(in.nextLine());
      } catch (DataFormatException e) {
        System.out.println(e);
        e.printStackTrace();
        continue;
      }
    in.close();
  }

  public int getId() {
    return this.UNIQUE_ID;
  }

  public void addTransactionGroup(String command) throws DataFormatException {
    
    if (!command.contains(" "))
      throw new DataFormatException(
        "addTransactionGroup requires string commands that contain only space separated integer values");

    if(this.transactionGroupsCount >=  MAX_GROUPS) throw new OutOfMemoryError (
      "Capacity of this Account's storage is "+ MAX_GROUPS+ "transactions.");
    
    String[] parts = command.split(" ");
    int[] newTransactions = new int[parts.length];
    for (int i = 0; i < parts.length; i++)
      try {
        newTransactions[i] = Integer.parseInt(parts[i]);
      }
    catch (NumberFormatException e) {
      throw new DataFormatException(
        "addTransactionGroup requires string commands that contain only space separated integer values"); 
      }
      
    TransactionGroup t = null;
    try {
      t = new TransactionGroup(newTransactions);
    } catch (DataFormatException e) {

      e.printStackTrace();
    }
    this.transactionGroups[this.transactionGroupsCount] = t;
    this.transactionGroupsCount++;
  }

  public int getTransactionCount() {
    int transactionCount = 0;
    for (int i = 0; i < this.transactionGroupsCount; i++)
      transactionCount += this.transactionGroups[i].getTransactionCount();
    return transactionCount;
  }
  //@throws IndexOutOfBoundsException
  public int getTransactionAmount(int index) {
    int currTransactionCount = getTransactionCount();
    if (index > currTransactionCount)
      throw new IndexOutOfBoundsException("Trying to access index " + index
        + ", while total transaction count is " + currTransactionCount);
    
    int transactionCount = 0;
    for (int i = 0; i < this.transactionGroupsCount; i++) {
      int prevTransactionCount = transactionCount;
      transactionCount += this.transactionGroups[i].getTransactionCount();
      if (transactionCount > index) {
        index -= prevTransactionCount;
        return this.transactionGroups[i].getTransactionAmount(index);
      }
    }
    return -1;
  }

  public int getCurrentBalance() {
    int balance = 0;
    int size = this.getTransactionCount();
    for (int i = 0; i < size; i++)
      balance += this.getTransactionAmount(i);
    return balance;
  }

  public int getNumberOfOverdrafts() {
    int balance = 0;
    int overdraftCount = 0;
    int size = this.getTransactionCount();
    for (int i = 0; i < size; i++) {
      int amount = this.getTransactionAmount(i);
      balance += amount;
      if (balance < 0 && amount < 0)
        overdraftCount++;
    }
    return overdraftCount;
  }

}
