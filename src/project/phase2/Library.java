/** The pahse2 package contains the LibrarySystem and Library classes */
package project.phase2;

import java.io.Serializable;

import java.util.Scanner;

import project.phase2.books.*;

import project.phase2.members.*;

import static project.phase2.Search.*;

import static project.phase2.books.Book.*;

import static project.phase2.books.BookStatus.*;

/**
 * Summary: This Singleton class represents a library management access. Objects
 * of this class are mutable and you can use setters and getters to access them.
 * <p>
 * 
 * @author Hamed Salmanizadegan @2023
 *         <p>
 * @see java.lang.String
 * @see java.util.Scanner
 */
public class Library implements Serializable {
    private static final long serialVersionUID = 6653163296668750535L;
    /** instance: will hold 0 or 1 to prevent other Library class creations */
    private static short instance = 0;
    private static int memNo;
    private static Member[] members;
    private static int bookNo;
    private static Book[] books;
    private static Date todayDate;
    /** Creation of only one library object */
    private static Library library = new Library();

    /**
     * Private constructor used to have Singleton Design Pattern for Library class
     * creation.
     */
    private Library() {
    }

    /**
     * Summary: This method sets numbers of library members and books to crate
     * library class once and then returns library class address.
     * <p>
     * Usage:
     * {@code Library library = Library.getInstance(getMembersNo(scanner), getBooksNo(scanner));}
     * <p>
     * 
     * @param memNo  (int)
     * @param bookNo (int)
     *               <p>
     * @return library (Library)
     */
    public static Library getInstance(int memNo, int bookNo, short Instance) {
	if (instance == 0) {
	    Library.memNo = memNo;
	    members = new Member[memNo];
	    Library.bookNo = bookNo;
	    books = new Book[bookNo];
	    instance++;
	} else if (Instance == 1) {
	    Library.memNo = memNo;
	    members = new Member[memNo];
	    Library.bookNo = bookNo;
	    books = new Book[bookNo];
	    instance = 1;
	} else
	    System.out.println("No more object can be created. The first info will remain.");
	return library;
    }

    /**
     * Returns the current today's date.
     *
     * @return The today's date.
     */
    public static Date getTodayDate() {
	return todayDate;
    }

    /**
     * Sets the current today's date.
     *
     * @param todayDate The date to set as today's date.
     */
    public static void setTodayDate(Date todayDate) {
	Library.todayDate = todayDate;
    }

    /**
     * Gets the number of members in the library.
     *
     * @return The number of members.
     */
    public static int getMemberNo() {
	return memNo;
    }

    /**
     * Sets the array of members in the library.
     *
     * @param members An array of Member objects representing the members in the
     *                library.
     */
    static void setMembers(Member[] members) {
	Library.members = members;
    }

    /**
     * Sets the array of books in the library.
     *
     * @param books An array of Book objects representing the books in the library.
     */
    static void setBooks(Book[] books) {
	Library.books = books;
    }

    /**
     * Returns an array of Member objects representing the members in the library.
     *
     * @return An array of Member objects.
     */
    public static Member[] getMembers() {
	return members;
    }

    /**
     * Gets the number of books in the library.
     *
     * @return The number of books.
     */
    public static int getBooksNo() {
	return bookNo;
    }

    /**
     * Returns an array of Book objects representing the books in the library.
     *
     * @return An array of Book objects.
     */
    public static Book[] getBooks() {
	return books;
    }

    /**
     * Summary: This method adds a new member using Member Class to the library
     * unless it's membership capacity is full.
     * <p>
     * 
     * @param scanner (Scanner)
     *                <p>
     * @see java.lang.String
     */
    public void addMember(Scanner scanner) {
	scanner.nextLine(); // To be able to use scanner.nextLine() properly.
	/**
	 * filled (boolean): this variable Shows whether new membership is possible or
	 * not.
	 */
	boolean filled = true;
	for (int i = 0; i < memNo; i++) {
	    if (members[i] == null) {
		System.out.println("Please enter the member Info.");
		members[i] = new Member(getName(scanner), getGender(scanner), getAge(scanner));
		System.out.println("New member ID is " + members[i].getId() + ".");
		filled = false;
		break;
	    }
	}
	if (filled)
	    System.out.println(
		    "There is no more capasity to add a member. The library cpasity is " + memNo + " members.");
    }

    /**
     * Summary: This method asks the operator to enter a name and returns it.
     * <p>
     * 
     * @param scanner (Scanner)
     *                <p>
     * @return (String)
     */
    static String getName(Scanner scanner) {
	String name = null;
	do {
	    System.out.print("Name: ");
	    name = scanner.nextLine();
	} while (name.length() == 0);
	return name;
    }

    /**
     * Summary: This method forces the operator to enter a gender for a member.
     * <p>
     * 
     * @param scanner (Scanner)
     *                <p>
     * @return gender (String)
     *         <p>
     * @see project.phase2.containString
     */
    static String getGender(Scanner scanner) {
	System.out.print("Gender (M/F): ");
	String gender;
	while (true) {
	    gender = scanner.next();
	    if (containString("M", gender) || containString("F", gender))
		break;
	    else {
		System.out.println("Please enter a valid gender name.");
		System.out.print("Gender (M/F): ");
	    }
	}
	return gender;
    }

    /**
     * Summary: This method forces the operator to enter age older than 4 years for
     * a member.
     * <p>
     * 
     * @param scanner (Scanner)
     *                <p>
     * @return age (long)
     */
    static long getAge(Scanner scanner) {
	System.out.print("Age: ");
	long age;
	while (true) {
	    age = scanner.nextLong();
	    if (age < 5) {
		System.out.println("Please enter a valid age.(above 5 years old.)");
		System.out.print("Age: ");
	    } else {
		return age;
	    }
	}
    }

    /**
     * Summary: This method searches for a member based on the input information and
     * then deletes them.
     * <p>
     * 
     * @param scanner (Scanner)
     */
    public void deleteMember(Scanner scanner) {
	int i = searchPrompt(scanner);
	if (i != -1) {
	    System.out.println(members[i]);
	    scanner.nextLine(); // To be able to use scanner.nextLine() properly.
	    String prompt = "Would you like to delete the member? (Yes/No)";
	    if (doYouWant(scanner, prompt)) {
		Book[] borrowedBooks = members[i].getBorrowedBooks();
		for (int j = 0; j < borrowedBooks.length; j++) {
		    if (borrowedBooks[j] != null) {
			books[searchId(booksIds(), borrowedBooks[j].getId())].returnBook();
			members[i] = null;
		    }
		}
	    }
	} else
	    System.out.println("No member was selected");
    }

    /**
     * Summary: This method asks a Yes/No question using input prompt and if the
     * operator enters "Yes" true will be returned, otherwise false.
     * <p>
     * 
     * @param scanner (Scanner)
     * @param prompt  (String)
     *                <p>
     * @return true/false (boolean)
     *         <p>
     * 
     * @see project.phase2.Library.containString
     */
    static boolean doYouWant(Scanner scanner, String prompt) {
	while (true) {
	    System.out.println(prompt);
	    String input = scanner.next();
	    if (containString("Yes", input))
		return true;
	    else if (containString("No", input))
		return false;
	}
    }

    /**
     * Summary: This method edits a member's properties.
     * <p>
     * 
     * @param scanner
     */
    public void editMember(Scanner scanner) {
	int i = searchPrompt(scanner);
	if (i != -1) {
	    System.out.println();
	    System.out.println(members[i]);
	    scanner.nextLine();
	    String prompt = "Would you like to edit the member's information? (Yes/No)";
	    if (doYouWant(scanner, prompt))
		editMemInfo(i, scanner);
	    if (members[i].getBorrowedBooks()[0] != null) { // He/She has borrowed at least one book
		prompt = "Would you like to edit the member's borrowed books? (Yes/No)";
		if (doYouWant(scanner, prompt))
		    editLentBook(i, scanner);
	    }
	} else
	    System.out.println("No member was selected.");
    }

    /**
     * Summary: This method asks the operator new information and edits a member
     * info.
     * <p>
     * 
     * @param i       (int): index of desired member
     * @param scanner
     */
    private static void editMemInfo(int i, Scanner scanner) {
	System.out.println("Pleasse enter the members new information:");
	scanner.nextLine(); // To be able to use scanner.nextLine() properly.
	members[i].setName(getName(scanner));
	members[i].setGender(getGender(scanner));
	members[i].setAge(getAge(scanner));
	System.out.println();
    }

    /**
     * Summary: This method says that whether the baseString contains all the
     * desiredStr chars or not while ignoring lower-upper case.
     * <p>
     * Usage:
     * {@code boolean check = containString("Hamed Salmanizadegan", "salmani");}
     * "check will be true"
     * <p>
     * 
     * @param baseString (String)
     * @param deiredStr  (String)
     *                   <p>
     * @return true/false (boolean): if the baseString contains the desiredStr, true
     *         will be returned, and if not false will be returned.
     *         <p>
     * @see java.lang.String
     */
    public static boolean containString(String baseString, String deiredStr) {
	int counterChar = 0;
	for (int i = 0; i < baseString.length(); i++) {
	    if (baseString.charAt(i) == deiredStr.charAt(counterChar)
		    || baseString.charAt(i) == deiredStr.charAt(counterChar) + 32
		    || baseString.charAt(i) == deiredStr.charAt(counterChar) - 32) {
		if (++counterChar == deiredStr.length())
		    return true;
	    } else
		counterChar = 0;
	}
	return false;
    }

    /**
     * Summary: This method searches the desired member and prints it's information.
     * <p>
     * 
     * @param scanner
     * @see project.phase2.searchPrompt
     */
    public void showMember(Scanner scanner) {
	int i = searchPrompt(scanner);
	if (i != -1) {
	    System.out.println();
	    System.out.println("Desired member Info:");
	    System.out.println(members[i]);
	} else
	    System.out.println("No member was selected.");
    }

    /**
     * Summary: This method adds a new book using Book Class to the library unless
     * its book capacity is full.
     * <p>
     * 
     * @param scanner (Scanner)
     *                <p>
     * @see java.lang.String
     */
    public void addBook(Scanner scanner) {
	// scanner.nextLine(); // To be able to use scanner.nextLine() properly.
	/**
	 * filled (boolean): this variable Shows whether new membership is possible or
	 * not.
	 */
	boolean filled = true;
	for (int i = 0; i < bookNo; i++) {
	    if (books[i] == null) {
		System.out.println("Please enter the book Info.");
		books[i] = new Book();
		System.out.println("New book ID is " + books[i].getId() + ".");
		filled = false;
		break;
	    }
	}
	if (filled)
	    System.out.println("There is no more capasity to add a book. The librry capasity is " + bookNo + " books.");
    }

    /**
     * Summary: This method finds a desired book and then delete it from the library
     * inventory.
     * <p>
     * 
     * @param scanner (Scanner)
     */
    public void deleteBook(Scanner scanner) {
	System.out.println("Please specify thes book first to delete it.");
	int i = searchBook(scanner);
	if (i != -1) {
	    System.out.printf("The Selected Book Info:\n%s\n", books[i]);
	    String prompt = "Do you want to delete the book? (Yes/No)";
	    if (doYouWant(scanner, prompt))
		books[i] = null;
	} else
	    System.out.println("No book was deleted.");
    }

    /**
     * Edits the information of a book based on user input.
     *
     * This method allows the user to edit the information of a book. The user is
     * prompted to specify the book they want to edit by entering its index. The
     * current information of the selected book is displayed, and the user is
     * prompted to input the book's new information, including title, author, cost,
     * number of pages, and published date.
     * 
     * After updating the book's information, the user is prompted to input the
     * book's state. If the book is not in a 'Loaned' state, the book's state is
     * updated to the user-specified state. If the book is in a 'Loaned' state and
     * the user specifies the state as 'Ready', a warning is displayed with the
     * option to consider the book as returned.
     * 
     * If the book index is not found, a message is displayed indicating that no
     * book was edited.
     *
     * @param scanner The Scanner object used to gather user input.
     */
    public void editBook(Scanner scanner) {
	System.out.println("Please specify the book first to edit it.");
	int i = searchBook(scanner); // Search for the book based on user input

	if (i != -1) {
	    System.out.printf("The Selected Book Info:\n%s\n", books[i]);
	    scanner.nextLine(); // To be able to use scanner.nextLine() properly.
	    System.out.println("Please input the book's new info.");

	    // Prompt the user to input various attributes of the book
	    books[i].setTitle(inputTitle(scanner));
	    books[i].setAuthor(inputAuthor(scanner));
	    books[i].setCost(inputCost(scanner));
	    books[i].setPagesNo(inputPagesNo(scanner));
	    books[i].setPublishedDate(new Date());

	    // Prompt the user to input the book's state
	    BookStatus state = inputState(scanner);

	    if (!books[i].getState().equals(Loaned)) {
		// Update the book's state if it's not in a 'Loaned' state
		books[i].setState(state);
	    } else if (state.equals(Ready)) {
		// If the book is 'Loaned' and the state is 'Ready', prompt the user to consider
		// the book as returned
		String prompt = "Warning: The book is already loaned.\nDo you want to consider it returned? (Yes/No)";
		if (doYouWant(scanner, prompt)) {
		    returnBook(i); // Consider the book as returned
		}
	    }
	    // Check if the book is still in a 'Loaned' state
	    if (books[i].getState().equals(Loaned))
		// Ask the user if they want to edit the borrower's information
		if (doYouWant(scanner, "Do you want to edit who has borrowed the book? (Yes/No)")) {
		    int method = 0;

		    // Loop until the user selects a valid action
		    while (method < 1 || method > 2) {
			System.out.println("What is your desired action to edit the member who borrowed the book?");
			System.out.println(
				"1) Remove him/her and return the book\n2) Replace him/her with another member");
			System.out.print("Please enter a number (1 or 2) to select your action: ");
			method = scanner.nextInt();
		    }

		    // Find the index of the member who borrowed the book
		    int memberIndex = searchId(membersIds(), books[i].getLentToMember().getId());

		    // Based on the selected action, perform the corresponding operation
		    switch (method) {
		    case 1: // Remove him/her and return the book
			if (returnABook(memberIndex, i))
			    System.out.println("The loaned book returned successfully.");
			else
			    System.out.println("The loaned book did NOT return successfully!");
			break;

		    case 2: // Replace him/her with another member
			replaceWhoBorrowed(memberIndex, i, scanner);
		    }
		}
	} else
	    System.out.println("No book was edited.");
    }

    /**
     * Replaces the member who borrowed a book with another member, based on user
     * input.
     *
     * @param memberIndex The index of the member who currently borrowed the book.
     * @param bookIndex   The index of the book being borrowed.
     * @param scanner     The Scanner object for user input.
     */
    private void replaceWhoBorrowed(int memberIndex, int bookIndex, Scanner scanner) {
	System.out.println("Please specify the member to become who borrowed the book.");

	// Search for the member to replace with, based on user input
	int newMemberIndex = searchPrompt(scanner);

	// Check if the new member can borrow one more book
	if (lendBookTo(bookIndex, newMemberIndex))
	    System.out.println("The member was replaced as the borrower of the book successfully.");
	else {
	    System.out.println("Sorry! The member was not replaced.");

	    // Prompt to search for another member if desired
	    if (doYouWant(scanner, "Do you want to search for another member? (Yes/No)"))
		replaceWhoBorrowed(memberIndex, bookIndex, scanner);
	}
    }

    /**
     * Lends a book to a member.
     *
     * This method facilitates the process of lending a book to a member in the
     * Library system. It takes the index of the book to be lent and the index of
     * the member to whom the book will be lent. The method checks the member's
     * borrowing capacity and finds an available slot in the member's list of
     * borrowed books to lend the new book. If the lending is successful, the book's
     * lent status is updated along with the member's list of borrowed books.
     *
     * @param bookIndex   The index of the book to be lent. Must be a valid index
     *                    within the 'books' array.
     * @param memberIndex The index of the member to whom the book will be lent.
     *                    Must be a valid index within the 'members' array.
     *
     * @throws IndexOutOfBoundsException if either 'bookIndex' or 'memberIndex' is
     *                                   out of range (i.e., negative or greater
     *                                   than/equal to the array size).
     */
    /**
     * Lends a book to a specific member and updates their borrowing status.
     *
     * This method allows lending a book to a member of the library. It first
     * retrieves the list of borrowed books for the specified member. It then
     * searches for an available slot in the member's list of borrowed books to lend
     * the new book. If an available slot is found, the book is added to the
     * member's borrowed books, and the necessary updates are made to the book's
     * lent status, return date, and the member's borrowing status. If the member
     * has already reached their borrowing capacity (maximum number of books they
     * can borrow simultaneously), the method informs the member that they cannot
     * borrow more books until they return some of their borrowed books.
     *
     * @param bookIndex   The index of the book to be lent in the 'books' array.
     * @param memberIndex The index of the member to whom the book will be lent in
     *                    the 'members' array.
     * @throws IndexOutOfBoundsException If the specified 'bookIndex' or
     *                                   'memberIndex' is out of bounds.
     */
    private boolean lendBookTo(int bookIndex, int memberIndex) throws IndexOutOfBoundsException {
	// Retrieve the list of borrowed books for the specified member
	Book[] borrowedBooks = members[memberIndex].getBorrowedBooks();
	boolean loaned = false;

	// Find an available slot in the member's list of borrowed books to lend the new
	// book
	for (int i = 0; i < borrowedBooks.length; i++) {
	    if (borrowedBooks[i] == null) {
		borrowedBooks[i] = books[bookIndex];
		loaned = true;
		books[bookIndex].lendBook(members[memberIndex]);
		break;
	    }
	}

	// Update the member's list of borrowed books and the book's lent status
	if (loaned) {
	    Date date;
	    do {
		System.out.println("Please enter the book return date.");
		date = new Date();
	    } while (!date.isAfter(todayDate));

	    members[memberIndex].setBorrowedBooks(borrowedBooks);
	    books[bookIndex].setLentToMember(members[memberIndex]);
	    books[bookIndex].setState(Loaned);
	    books[bookIndex].setReturningDate(date);
	    return true;
	} else {
	    // If the member has reached their borrowing capacity, inform them
	    System.out.println("Member is not allowed to borrow any more books.\nEach member can only borrow "
		    + borrowedBooks.length + " books at most simultaneously.");
	    return false;
	}
    }

    /**
     * Summary: This method prints the inventory of library books.
     * <p>
     * 
     * @param scanner (Scanner)
     */
    public void showBooks(Scanner scanner) {
	System.out.println("The Inventory Of Library Books:");
	for (int i = 0; i < books.length; i++) {
	    if (books[i] != null)
		System.out.println(books[i]);
	}
    }

    /**
     * Summary: This method finds a desired book and then shows its info.
     * <p>
     * 
     * @param scanner (Scanner)
     */
    public void showABook(Scanner scanner) {
	System.out.println("Please specify the book to show its info.");
	int i = searchBook(scanner);
	if (i != -1) {
	    System.out.println(books[i]);
	} else
	    System.out.println("No book was selected.");
    }

    /**
     * Initiates the process of lending a book to a member based on user input.
     *
     * This method guides the user through the process of lending a book to a
     * member. It first prompts the user to specify the book they want to lend. The
     * method then searches for the specified book based on the user's input. If the
     * book is found and its state is 'Ready', it proceeds to prompt the user to
     * specify the member who wishes to borrow the book. After obtaining the
     * member's input, it lends the book to the specified member using the
     * 'lendBookTo' method. If the book is not found or its state is not 'Ready',
     * the method informs the user that the book cannot be lent. If the specified
     * member is not found, it displays a message indicating that no member was
     * found. If the specified book is not found, it notifies the user that no book
     * was lent.
     *
     * @param scanner The Scanner object to read user input.
     */
    public void lendBook(Scanner scanner) {
	System.out.println("Please specify the book to lend it.");
	// Search for the book based on user input
	int i = searchBook(scanner);

	// Check if the book exists and its state is 'Ready'
	if (!books[i].getState().equals(Ready)) {
	    System.out.println("Sorry, This book cannot be lent.");
	} else {
	    if (i != -1) {
		System.out.println("Please set the member who wants to borrow the book.");
		// Prompt the user to specify the member who wishes to borrow the book
		int memberIndex = searchPrompt(scanner);

		if (memberIndex != -1) {
		    // Lend the book to the specified member
		    lendBookTo(i, memberIndex);
		} else {
		    System.out.println("No member was found.");
		}
	    } else {
		System.out.println("No book was lent.");
	    }
	}
    }

    /**
     * Displays a list of books that are currently lent to library members.
     *
     * This method interacts with the user through the provided scanner to display a
     * list of books that are currently in a lent state (i.e., borrowed by library
     * members). It iterates through the 'books' array and checks the state of each
     * book. If a book is found to be in the "Loaned" state, it is displayed as part
     * of the lent books list. If no lent books are found, an appropriate message is
     * shown.
     *
     * @param scanner The scanner object used to get input from the user (unused in
     *                this method).
     */
    public void showLentBooks(Scanner scanner) {
	boolean wasAny = false;
	for (int i = 0; i < books.length; i++) {
	    if (books[i] != null && books[i].getState().equals(Loaned)) {
		if (!wasAny) {
		    System.out.println("The lent books list:");
		}
		wasAny = true;
		System.out.println(books[i]);
	    }
	}
	if (!wasAny) {
	    System.out.println("There is no lent book.");
	}
    }

    /**
     * Displays information about a specific lent book based on user input.
     *
     * This method first generates an array of book IDs representing the lent books
     * in the library. It then interacts with the user through the provided scanner
     * to get input for selecting a specific lent book by its ID. The method
     * searches for the book based on the input ID and displays detailed information
     * about the selected lent book if found. If no lent book is found with the
     * specified ID, an appropriate message is shown.
     *
     * @param scanner The scanner object used to get input from the user.
     */
    public void showALentBook(Scanner scanner) {
	long[] IDs = lentBooks();
	System.out.println("Please specify the lent book to show its info.");
	int bookIndex = searchBook(IDs, scanner);

	if (bookIndex != -1) {
	    System.out.println(books[bookIndex]);
	} else {
	    System.out.println("No lent book was selected.");
	}
    }

    /**
     * Displays a list of overdue books based on the current date and the books'
     * returning dates.
     *
     * This method iterates through the array of books and identifies books that are
     * currently on loan (i.e., in 'Loaned' state) and have exceeded their returning
     * dates. For each such book, it prints its information to the console. If there
     * are no overdue books, the method displays a message indicating that there are
     * no overdue books.
     *
     * @param scanner The Scanner object to read user input.
     */
    public void showOverdueBooks(Scanner scanner) {
	boolean wasAny = false;

	// Iterate through the array of books
	for (int i = 0; i < books.length; i++) {
	    // Check if the book is loaned and its returning date has passed
	    if (books[i] != null && books[i].getState().equals(Loaned)
		    && todayDate.isAfter(books[i].getReturningDate())) {
		if (!wasAny) {
		    System.out.println("The overdue books list:");
		}
		wasAny = true;
		System.out.println(books[i]); // Print information of the overdue book
	    }
	}

	// If no overdue books were found, display a message
	if (!wasAny) {
	    System.out.println("There is no overdue book.");
	}
    }

    /**
     * Creates an array of book IDs corresponding to books that are currently on
     * loan.
     *
     * This method generates an array of book IDs based on the current state of the
     * books. It iterates through the array of books and marks non-lent books by
     * setting their corresponding ID entry to -1. The resulting array contains the
     * IDs of books that are in the 'Loaned' state, while non-lent books are
     * indicated by -1 in the array. The array of book IDs is then returned.
     *
     * @return An array of book IDs representing books that are currently on loan.
     */
    private static long[] lentBooks() {
	long[] IDs = booksIds(); // Generate an array of book IDs

	// Mark non-lent books with -1 in the IDs array
	for (int i = 0; i < IDs.length; i++) {
	    if (!books[i].getState().equals(Loaned)) {
		IDs[i] = -1;
	    }
	}

	return IDs; // Return the array of book IDs
    }

    /**
     * Edits the information of a lent book based on user input.
     *
     * This method allows the user to edit the information of a lent book. It first
     * generates an array of book IDs for books that are currently on loan. The user
     * is prompted to specify the lent book they want to edit by entering its index.
     * The method then displays the current information of the selected book and
     * prompts the user to input the book's new information, including title,
     * author, cost, number of pages, and published date.
     * 
     * After updating the book's information, the user is prompted to input the
     * book's state. If the state is 'Ready', a warning is displayed if the book is
     * already loaned, and the user is given the option to consider the book as
     * returned. If the state is 'Binding', a similar warning is displayed with the
     * option to consider the book as returned and being bound.
     * 
     * If the book index is not found in the list of lent books, a message is
     * displayed indicating that no book was found.
     *
     * @param scanner The Scanner object used to gather user input.
     */
    public void editLentBook(Scanner scanner) {
	long[] IDs = lentBooks(); // Generate an array of book IDs for lent books
	System.out.println("Please specify the lent book first to edit it.");
	int bookIndex = searchBook(IDs, scanner); // Search for the book based on user input

	if (bookIndex != -1) {
	    System.out.printf("The Selected Book Current Info:\n%s\n", books[bookIndex]);
	    scanner.nextLine(); // To be able to use scanner.nextLine() properly.
	    System.out.println("Please input the book's new info.");

	    // Prompt the user to input various attributes of the book
	    books[bookIndex].setTitle(inputTitle(scanner));
	    books[bookIndex].setAuthor(inputAuthor(scanner));
	    books[bookIndex].setCost(inputCost(scanner));
	    books[bookIndex].setPagesNo(inputPagesNo(scanner));
	    books[bookIndex].setPublishedDate(new Date());

	    // Prompt the user to input the book's state
	    BookStatus state = inputState(scanner);

	    if (state == Ready) {
		String prompt = "Warning: The book is already loaned.\nDo you want to consider it returned? (Yes/No)";
		if (doYouWant(scanner, prompt)) {
		    returnABook(bookIndex); // Consider the book as returned
		}
	    } else if (state == Binding) {
		String prompt = "Warning: The book is already loaned.\nDo you want to consider it a book returned and now being bound? (Yes/No)";
		if (doYouWant(scanner, prompt)) {
		    returnABook(bookIndex); // Consider the book as returned
		    books[bookIndex].setState(Binding); // Set the book's state to 'Binding'
		}
	    }
	} else
	    System.out.println("No book was found.");
    }

    /**
     * Edits the borrowed books of a member based on user input.
     *
     * This method allows the user to edit the borrowed books of a member. The user
     * is prompted to select a book from the list of borrowed books of the specified
     * member. The current list of borrowed books is displayed, and the user is
     * prompted to choose an action to edit the selected lent book. The user can
     * either remove the book from the member's borrowed books list or replace it
     * with another book. If the user selects to replace the book, the method
     * `replaceBorrowedBook` is called to handle the replacement process.
     *
     * After each action, the user is prompted if they want to edit another borrowed
     * book of the same member. The Scanner object is used to gather user input
     * throughout the process.
     *
     * @param memberIndex The index of the member whose borrowed books are to be
     *                    edited.
     * @param scanner     The Scanner object used to gather user input.
     */
    private void editLentBook(int memberIndex, Scanner scanner) {
	Book[] borrowedBook = members[memberIndex].getBorrowedBooks();
	long[] IDs = new long[borrowedBook.length];

	while (true) {
	    System.out.println("List of his/her borrowed books:");

	    // Display the list of borrowed books and store their IDs
	    for (int i = 0; i < borrowedBook.length; i++) {
		if (borrowedBook[i] != null)
		    IDs[i] = borrowedBook[i].getId();
		else
		    IDs[i] = -1;
	    }

	    int bookIndex = selectBook(IDs, scanner); // Select a book from the list

	    if (bookIndex != -1) {
		int method = 0;
		while (method < 1 || method > 2) {
		    System.out.println("What is your desired action to edit the lent book?");
		    System.out
			    .println("1) Remove it from his/her borrowed books list\n2) Replace it with another book");
		    System.out.print("Please enter a number (1 or 2) to select your action: ");
		    method = scanner.nextInt();
		}

		switch (method) {
		case 1: // Remove it from his/her borrowed books list
		    if (returnABook(memberIndex, bookIndex)) {
			System.out.println("The book was removed successfully.");
		    } else {
			System.out.println("The book did NOT remove successfully!");
		    }
		    break;
		case 2: // Replace it with another book
		    replaceBorrowedBook(memberIndex, bookIndex, scanner);
		}
	    } else {
		System.out.println("No book was found.");
	    }

	    // Prompt the user if they want to edit another borrowed book
	    String prompt = "Do you want to edit one more of his/her borrowed books? (Yes/No)";
	    if (doYouWant(scanner, prompt)) {
		editLentBook(memberIndex, scanner);
	    } else {
		break;
	    }
	}
    }

    /**
     * Replaces a borrowed book with another book for the specified member.
     *
     * This method allows the user to replace a borrowed book with another book for
     * the specified member. The user is prompted to specify the book that will
     * replace the existing borrowed book. The method first searches for the new
     * book based on user input and checks if the new book's state is 'Ready',
     * indicating that it can be lent. If the new book is eligible for lending and
     * the existing borrowed book is successfully returned using the method
     * `returnABook`, the new book is lent to the member using the method
     * `lendBookTo`, effectively replacing the old book with the new one.
     *
     * If the existing borrowed book cannot be returned successfully, or if the new
     * book's state is not 'Ready', the replacement process fails, and an
     * appropriate message is displayed to inform the user of the result.
     *
     * @param memberIndex The index of the member for whom the book is being
     *                    replaced.
     * @param bookIndex   The index of the book that is currently borrowed and is to
     *                    be replaced.
     * @param scanner     The Scanner object used to gather user input.
     */
    private void replaceBorrowedBook(int memberIndex, int bookIndex, Scanner scanner) {
	System.out.println("Please specify the book to be replaced with.");
	// Search for the book to replace with based on user input
	int newBookIndex = searchBook(scanner);

	// Check if the new book exists and its state is 'Ready'
	if (!books[newBookIndex].getState().equals(Ready)) {
	    System.out.println("Sorry, This book cannot be lent.");
	} else if (returnABook(memberIndex, bookIndex)) {
	    // Return the existing borrowed book and lend the new book
	    lendBookTo(newBookIndex, memberIndex);
	    System.out.println("The book replaced successfully!");
	} else {
	    System.out.println("The book did NOT replace successfully!");
	}
    }

    /**
     * This method specifies the borrowed book and returns it to the library.
     * <p>
     * 
     * @param scanner (Scanner)
     */
    public void returnBook(Scanner scanner) {
	System.out.println("Please specify the book first to return it.");
	int i = searchBook(scanner);
	if (i != -1) {
	    returnBook(i);
	} else
	    System.out.println("No book was selected.");
    }

    /**
     * This method returns the specified borrowed book to the library based on its
     * previous status.
     * <p>
     * 
     * @param indexOfBook (int)
     */
    private void returnBook(int indexOfBook) {
	if (books[indexOfBook].getState().equals(Loaned))
	    returnABook(indexOfBook);
	else {
	    books[indexOfBook].setState(Ready);
	    System.out.println("The bound book returned successfully.");
	}
    }

    /**
     * This method returns the borrowed book to the library and checks and updates
     * the book's status.
     * <p>
     * 
     * @param indexOfBook (int)
     */
    private void returnABook(int indexOfBook) {
	int memberIndex = searchId(membersIds(), books[indexOfBook].getLentToMember().getId());
	if (returnABook(memberIndex, indexOfBook))
	    System.out.println("The loaned book returned successfully.");
	else
	    System.out.println("The loaned book did NOT return successfully!");
    }

    /**
     * Returns a borrowed book to the library and updates member and book statuses.
     *
     * This method returns a borrowed book to the library and updates both the
     * member's and the book's statuses accordingly. It checks if the specified book
     * is actually borrowed by the given member, and if so, removes the book from
     * the member's list of borrowed books, resets the book's lent status, and
     * updates its state to 'Ready'. The method returns `true` if the book is
     * successfully returned, and `false` otherwise.
     *
     * @param memberIndex The index of the member who borrowed the book.
     * @param indexOfBook The index of the book being returned.
     * @return `true` if the book is successfully returned, `false` otherwise.
     */
    private boolean returnABook(int memberIndex, int indexOfBook) {
	Book[] borrowedBooks = books[indexOfBook].getLentToMember().getBorrowedBooks();
	for (int i = 0; i < borrowedBooks.length; i++) {
	    if (borrowedBooks[i] != null && borrowedBooks[i] == books[indexOfBook]) {
		borrowedBooks[i] = null;
		members[memberIndex].setBorrowedBooks(borrowedBooks);
		books[indexOfBook].returnBook();
		return true;
	    }
	}
	return false;
    }
}