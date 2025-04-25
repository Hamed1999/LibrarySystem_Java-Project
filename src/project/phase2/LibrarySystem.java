/** The pahse2 package contains the LibrarySystem and Library classes */
package project.phase2;

import java.util.Scanner;

import project.phase2.books.*;

import project.phase2.members.Member;

import java.io.*;

/**
 * Summary: This class represents a library System that interfaces with a
 * library operator and has the public static void main(String[] args) method.
 * Objects of this class are mutable.
 * <p>
 * 
 * @author Hamed Salmanizadegan @2023r
 *         <p>
 * @see java.lang.String
 * @see java.util.Scanner
 */
public class LibrarySystem implements Serializable {
    private static final long serialVersionUID = 6653163296668750535L;
    private static Library library;

    /**
     * Private constructor to prevent the creation of any instances of LibrarySystem
     */
    private LibrarySystem() {
    }

    /**
     * The main entry point of the library management system. This method
     * initializes and manages the library based on user input.
     *
     * @param args The command-line arguments (not used in this context).
     */
    public static void main(String[] args) {
	Scanner scanner = new Scanner(System.in);
	String prompt = "Do you want to load the library data? (Yes/No)";
	if (Library.doYouWant(scanner, prompt))
	    loadData();
	else
	    // Singleton creation of Library class
	    library = Library.getInstance(getMembersNo(scanner), getBooksNo(scanner), (short) 0);
	System.out.println("Please enter today's date.");
	Library.setTodayDate(new Date());
	int inputAction = 0;
	do {
	    inputAction = getManageAction(scanner);
	    switch (inputAction) {
	    case 1: // Manage Members
		manageMembers(library, scanner);
		break;
	    case 2: // Manage Lent Books
		manageLentBooks(library, scanner);
		break;
	    case 3: // Manage Books
		manageBooks(library, scanner);
		break;
	    case 4: // Save The Library
		saveData();
		break;
	    case 5: // Load The Library
		loadData();
	    }
	} while (inputAction != 6);
	scanner.close();
    }

    /**
     * Loads data from a serialized file and populates the library with the
     * retrieved information. The data must have been previously serialized using
     * the corresponding `saveData()` method.
     * <p>
     * This method reads the serialized data from the file "file.ser" and
     * initializes the library properties, including the list of members and books,
     * based on the retrieved data. The serialized data should contain the following
     * information in the specified order: 1. The value of 'instance', which is a
     * short indicating a specific state. 2. The number of members, represented as
     * an integer. 3. The array of members, represented as an array of
     * {@link Member} objects. 4. The number of books, represented as an integer. 5.
     * The array of books, represented as an array of {@link Book} objects. 6. The
     * library instance, which can be constructed using the 'memNo' and 'bookNo'
     * values.
     * <p>
     * Note: The 'file.ser' file should be in a valid serialized format with the
     * correct ordering of data.
     * <p>
     * If any error occurs during the process of loading and deserializing the data,
     * an error message will be printed to the standard error stream.
     *
     * @see Member
     * @see Book
     * @see Library
     */
    private static void loadData() {
	try {
	    // Replace "file.ser" with the path to the serialized file
	    String filePath = "file.ser";

	    // Create a FileInputStream to read the serialized data from the file
	    FileInputStream fileInputStream = new FileInputStream(filePath);

	    // Create an ObjectInputStream to deserialize the data from the file
	    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

	    // Read the properties from the file
	    short instance = objectInputStream.readShort();
	    int memNo = objectInputStream.readInt();
	    Member[] Members = (Member[]) objectInputStream.readObject();
	    int bookNo = objectInputStream.readInt();
	    Book[] books = (Book[]) objectInputStream.readObject();
	    library = Library.getInstance(memNo, bookNo, instance);
	    Library.setMembers(Members);
	    Library.setBooks(books);

	    // Close the streams
	    objectInputStream.close();
	    fileInputStream.close();

	    System.out.println("Data loaded successfully.");
	} catch (IOException | ClassNotFoundException e) {
	    System.err.println("Error occurred while loading the data: " + e.getMessage());
	}
    }

    /**
     * Saves the library data to a serialized file.
     * <p>
     * This method serializes the library data, including the library's instance,
     * member count, member array, book count, and book array, and saves it to a
     * specified file using Java's serialization mechanism. The data is saved in a
     * binary format, which can be later deserialized to restore the library state.
     * <p>
     * Note: This method should be called when the library data needs to be
     * persisted for future use or when the application is exiting to ensure that
     * the data is not lost.
     * 
     * @throws IOException if an I/O error occurs while writing the data to the
     *                     file.
     *                     <p>
     * @see Library#getMemberNo()
     * @see Library#getMembers()
     * @see Library#getBooksNo()
     * @see Library#getBooks()
     */
    private static void saveData() {
	try {
	    // Replace "file.ser" with the desired file path and name
	    String filePath = "file.ser";

	    // Create a FileOutputStream to write the serialized data to the file
	    FileOutputStream fileOutputStream = new FileOutputStream(filePath);

	    // Create an ObjectOutputStream to serialize the data and write it to the file
	    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

	    // Write the properties to the file
	    short instance = 1;
	    objectOutputStream.writeShort(instance);
	    objectOutputStream.writeInt(Library.getMemberNo());
	    objectOutputStream.writeObject(Library.getMembers());
	    objectOutputStream.writeInt(Library.getBooksNo());
	    objectOutputStream.writeObject(Library.getBooks());

	    // Close the streams
	    objectOutputStream.close();
	    fileOutputStream.close();

	    System.out.println("Data saved successfully.");
	} catch (IOException e) {
	    System.err.println("Error occurred while saving the data: " + e.getMessage());
	}
    }

    /**
     * This method prints all actions that an operator can select among them to
     * access three library management system.
     * <p>
     * 
     * @return inputAction (int): must contain a number between 1-6 to declare a
     *         specific action
     *         <p>
     * @see java.lang.String
     * @see java.lang.Scanner
     */
    private static int getManageAction(Scanner scanner) {
	int inputAction = 0;
	while (inputAction < 1 || inputAction > 6) {
	    String action1 = "1) Manage The Members";
	    String action2 = "2) Manage The Lent Books";
	    String action3 = "3) Manage The Books";
	    String action4 = "4) Save The Library";
	    String action5 = "5) Load The Library";
	    String action6 = "6) Exit The Library System";
	    System.out.println();
	    System.out.println("What would you like to do?");
	    System.out.printf("%s%n%s%n%s%n%s%n%s%n%s%n", action1, action2, action3, action4, action5, action6);
	    System.out.print("Please enter a number between 1 & 6: ");
	    inputAction = scanner.nextInt();
	    System.out.println();
	}
	return inputAction;
    }

    /**
     * Summary: This method represents four different actions for library members'
     * management.
     * <p>
     * 
     * @param library
     * @param scanner
     */
    private static void manageMembers(Library library, Scanner scanner) {
	int inputAction = 0;
	do {
	    inputAction = getMembersAction(scanner);
	    switch (inputAction) {
	    case 1: // Add a member
		library.addMember(scanner);
		break;
	    case 2: // Delete a member
		library.deleteMember(scanner);
		break;
	    case 3: // Edit a member
		library.editMember(scanner);
		break;
	    case 4: // Show a member
		library.showMember(scanner);
	    }
	} while (inputAction != 5);
    }

    /**
     * This method prints all possible actions that an operator can select among
     * them to manage members and gets a number between 1-5 which will be returned
     * as an inputAction variable.
     * <p>
     * 
     * @return inputAction (int): must contain a number between 1-5 to declare a
     *         specific action
     *         <p>
     * @see java.lang.String
     * @see java.lang.Scanner
     */
    private static int getMembersAction(Scanner scanner) {
	int inputAction = 0;
	while (inputAction < 1 || inputAction > 5) {
	    String action1 = "1) Add A Member";
	    String action2 = "2) Delete A Member";
	    String action3 = "3) Edit A Member";
	    String action4 = "4) Show A Member";
	    String action5 = "5) Exit The Section";
	    System.out.println();
	    System.out.println("What would you like to do?");
	    System.out.printf("%s%n%s%n%s%n%s%n%s%n", action1, action2, action3, action4, action5);
	    System.out.print("Please enter a number between 1 & 5: ");
	    inputAction = scanner.nextInt();
	    System.out.println();
	}
	return inputAction;
    }

    /**
     * Summary: This method represents four different actions for lending library
     * book management.
     * <p>
     * 
     * @param library
     * @param scanner
     *                <p>
     * @see project.phase2.Library#lendBook
     * @see project.phase2.LibrarySystem#showLentBooks
     * @see project.phase2.Library#editLentBook
     * @see project.phase2.Library#returnBook
     */
    private static void manageLentBooks(Library library, Scanner scanner) {
	int inputAction = getLentBooksAction(scanner);
	switch (inputAction) {
	case 1: // Lend A Book
	    library.lendBook(scanner);
	    break;
	case 2: // Show Lent Books
	    showLentBooks(library, scanner);
	    break;
	case 3: // Edit A Lent Book
	    library.editLentBook(scanner);
	    break;
	case 4: // Return A Lent Book
	    library.returnBook(scanner);
	    break;
	}
    }

    /**
     * This method provides some other methods which the operator can select among
     * them to see the lent books inventory or search among them to see a specific
     * lent book or the overdue books inventory.
     * <p>
     * 
     * @param library (Library)
     * @param scanner
     */
    private static void showLentBooks(Library library, Scanner scanner) {
	int action = 0;
	while (action < 1 || action > 4) {
	    System.out.println("What is your desired action?");
	    System.out.printf("%s%n%s%n%s%n%s%n", "1) Lent Books Inventory", "2) Show A Lent Book",
		    "3) Show Overdue Books", "4) Exit The Section");
	    System.out.print("Please enter a number between 1 & 4 to select your search method: ");
	    action = scanner.nextInt();
	    System.out.println();
	}
	switch (action) {
	case 1:
	    library.showLentBooks(scanner);
	    break;
	case 2:
	    library.showALentBook(scanner);
	    break;
	case 3:
	    library.showOverdueBooks(scanner);
	}
    }

    /**
     * This method prints all possible actions that an operator can select among
     * them to manage lent books and gets a number between 1-5 which will be
     * returned as an inputAction variable.
     * <p>
     * 
     * @return inputAction (int): must contain a number between 1-5 to declare a
     *         specific action
     *         <p>
     * @see java.lang.String
     * @see java.lang.Scanner
     */
    private static int getLentBooksAction(Scanner scanner) {
	int action = 0;
	String action1 = "1) Lend A Book";
	String action2 = "2) Show Lent Books";
	String action3 = "3) Edit A Lent Book";
	String action4 = "4) Return A Lent/Bound Book";
	String action5 = "5) Exit The Section";
	while (action < 1 || action > 5) {
	    System.out.println("What is your desired action?");
	    System.out.printf("%s%n%s%n%s%n%s%n%s%n", action1, action2, action3, action4, action5);
	    System.out.print("Please enter a number between 1 & 5 to select your search method: ");
	    action = scanner.nextInt();
	    System.out.println();
	}
	return action;
    }

    /**
     * Summary: This method represents four different actions to manage library
     * books.
     * <p>
     * 
     * @param library
     * @param scanner
     */
    private static void manageBooks(Library library, Scanner scanner) {
	int inputAction = 0;
	do {
	    inputAction = getBooksAction(scanner);
	    switch (inputAction) {
	    case 1: // Add a book
		library.addBook(scanner);
		break;
	    case 2: // Delete a book
		library.deleteBook(scanner);
		break;
	    case 3: // Edit a book
		library.editBook(scanner);
		break;
	    case 4: // Show books
		String prompt = "Do you want to see the books inventory? (Yes/No) \n"
			+ " If \"No\" you will be showed a desired book of the inventory.";
		if (Library.doYouWant(scanner, prompt))
		    library.showBooks(scanner);
		else
		    library.showABook(scanner);
	    }
	} while (inputAction != 5);
    }

    /**
     * This method prints all possible actions that an operator can select among
     * them to manage books inventory and gets a number between 1-5 which will be
     * returned as an inputAction variable.
     * <p>
     * 
     * @return inputAction (int): must contain a number between 1-5 to declare a
     *         specific action
     *         <p>
     * @see java.lang.String
     * @see java.lang.Scanner
     */
    private static int getBooksAction(Scanner scanner) {
	int inputAction = 0;
	while (inputAction <= 0 || inputAction > 6) {
	    String action1 = "1) Add A Book";
	    String action2 = "2) Delete A Book";
	    String action3 = "3) Edit A Book";
	    String action4 = "4) Show Books";
	    String action5 = "5) Exit The Section";
	    System.out.println();
	    System.out.println("What would you like to do?");
	    System.out.printf("%s%n%s%n%s%n%s%n%s%n", action1, action2, action3, action4, action5);
	    System.out.print("Please enter a number between 1 & 5: ");
	    inputAction = scanner.nextInt();
	    System.out.println();
	}
	return inputAction;
    }

    /**
     * This method forces the operator to enter number of prospective library
     * members.
     * <p>
     * 
     * @return memNo (int)
     *         <p>
     * @see java.lang.String
     * @see java.lang.Scanner
     */
    private static int getMembersNo(Scanner scanner) {
	System.out.print("Please enter number of prospective library members: ");
	int memNo = 0;
	do {
	    memNo = scanner.nextInt();
	    if (memNo <= 0)
		System.out.println("Please enter number of members as an integer number bigger than zero. : )");
	} while (memNo < 1);
	return memNo;
    }

    /**
     * This method forces the operator to enter number of prospective library books.
     * <p>
     * 
     * @return bookNo (int)
     *         <p>
     * 
     * @see java.lang.String
     * @see java.lang.Scanner
     */
    private static int getBooksNo(Scanner scanner) {
	System.out.print("Please enter number of prospective library books: ");
	int bookNo = 0;
	do {
	    bookNo = scanner.nextInt();
	    if (bookNo <= 0)
		System.out.println("Please enter number of books as an integer number bigger than zero. : )");
	} while (bookNo < 1);
	return bookNo;
    }
}