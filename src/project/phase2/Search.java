package project.phase2;

import static project.phase2.Library.*;

import static project.phase2.books.Book.*;

import java.io.Serializable;

import java.util.Scanner;

import project.phase2.books.*;

import project.phase2.members.Member;

/**
 * This class provides static methods for searching among members and books in
 * the library.
 * <p>
 * The `Search` class encapsulates various methods to search for members and
 * books in the library's collections. It includes both basic and advanced
 * search methods, enabling users to search by different criteria such as name,
 * ID, age, gender, borrowed books, book title, author, cost, pages number,
 * published date, and book status.
 * <p>
 * <b>Author:</b> Hamed Salmanizadegan @2023
 * <p>
 * 
 * @see java.lang.String
 * @see java.util.Scanner
 * @see project.phase2.Library
 * @see project.phase2.members.Member
 * @see project.phase2.books.Book
 */
public class Search implements Serializable {
    private static final long serialVersionUID = 6653163296668750535L;
    private static Member[] members = getMembers();
    private static Book[] books = getBooks();

    /**
     * There is no need to make any instances of the class which lead to the private
     * constructor.
     */
    private Search() {
    }

    /**
     * Displays a prompt and allows the operator to choose a search method.
     *
     * @param scanner The scanner to read user input.
     * @return The index of the desired member in the members array.
     * @see project.phase2.Library.containString
     */
    public static int searchPrompt(Scanner scanner) {
	int method = 0, i = -1;
	while (!(method == 1 || method == 2)) {
	    System.out.println("How do you prefer to look for a member?");
	    System.out.printf("%s%n%s%n", "1) Search by name", "2) Advanced search");
	    System.out.print("Please enter a number (1 or 2) to select your search method: ");
	    method = scanner.nextInt();
	    System.out.println();
	}
	switch (method) {
	case 1: // search by name
	    i = simpleSearch(scanner);
	    break;
	case 2: // advance search
	    i = advanceSearch(scanner);
	}
	return i;
    }

    /**
     * Summary: This method asks a Yes/No question using input prompt and if the
     * operator enters "Yes" true will be returned, otherwise false.
     * <p>
     * 
     * @param scanner (Scanner)
     *                <p>
     * @return i (int): index of desired members arrays
     */
    private static int advanceSearch(Scanner scanner) {
	int method = 0, id = -1;
	long[] IDs = membersIds();
	scanner.nextLine(); // To be able to use scanner.nextLine() properly.
	while (true) {
	    method = advancePrompt(scanner);
	    switch (method) {
	    case 1: // Search by name
		IDs = nameSearch(IDs, scanner);
		break;
	    case 2: // Search by ID
		IDs = idSearch(IDs, scanner);
		break;
	    case 3: // Search by age
		IDs = ageSearch(IDs, scanner);
		break;
	    case 4: // Search by gender
		IDs = genderSearch(IDs, scanner);
		break;
	    case 5: // Search by borrowed books
		IDs = findMembersViaBookSearch(IDs, scanner);
		break;
	    }
	    System.out.println();
	    if (noResult(IDs))
		return -1;
	    if (!filter(scanner)) {
		id = selectResult(IDs, scanner, true);
		break;
	    }
	    System.out.println();
	}
	return id;
    }

    /**
     * Searches for members based on gender input.
     *
     * @param IDs     The initial list of member IDs to filter.
     * @param scanner The scanner to read user input.
     * @return The filtered list of member IDs.
     */
    private static long[] genderSearch(long[] IDs, Scanner scanner) {
	scanner.nextLine();
	int found = 0;
	while (true) {
	    System.out.println("Please enter the member's gender (F/M).");
	    String gender = getGender(scanner);
	    for (int i = 0; i < IDs.length; i++) {
		if (IDs[i] > 0 && containString(members[i].getGender(), gender)) {
		    IDs[i] = members[i].getId();
		    System.out.printf("ID: %d --> Name: %s --> Gender: %s%n", IDs[i], members[i].getName(), gender);
		    found++;
		} else
		    IDs[i] = -1;
	    }
	    if (found == 0)
		System.out.println("No one was found!");
	    else
		return IDs;
	    if (noResult(IDs))
		return IDs;
	}
    }

    /**
     * Prompts the operator to select a search result.
     *
     * @param IDs           The list of member IDs.
     * @param scanner       The scanner to read user input.
     * @param advanceSearch Indicates if it's an advanced search.
     * @return The index of the selected member.
     */
    private static int selectResult(long[] IDs, Scanner scanner, boolean advanceSearch) {
	if (advanceSearch) {
	    for (int i = 0; i < IDs.length; i++) {
		if (IDs[i] > 0)
		    System.out.printf("ID: %d ---> Name: %s%n", members[i].getId(), members[i].getName());
	    }
	}
	long id;
	if (noResult(IDs))
	    return -1;
	do {
	    System.out.print("Please enter the ID of your desired member: ");
	    id = scanner.nextLong();
	} while (!checkInputId(IDs, id));
	return searchId(IDs, id);
    }

    /**
     * Summary: This method finds members based on the member's age inputed by the
     * operator.
     * <p>
     * 
     * @param IDs     ( long[] )
     * @param scanner
     *                <p>
     * 
     * @return IDs ( long[] )
     */
    private static long[] ageSearch(long[] IDs, Scanner scanner) {
	int found = 0;
	while (true) {
	    System.out.println("Please enter the member's age.");
	    long age = getAge(scanner);
	    for (int i = 0; i < IDs.length; i++) {
		if (IDs[i] > 0 && members[i].getAge() == age) {
		    IDs[i] = members[i].getId();
		    System.out.printf("ID: %d --> Name: %s%n --> Age: %d%n", IDs[i], members[i].getName(), age);
		    found++;
		} else
		    IDs[i] = -1;
	    }
	    if (found == 0)
		System.out.println("No one was found!");
	    else
		return IDs;
	    if (noResult(IDs))
		return IDs;
	}
    }

    /**
     * Summary: This method finds a member based on the member's ID inputed by the
     * operator.
     * <p>
     * 
     * @param IDs     ( long[] )
     * @param scanner
     *                <p>
     * 
     * @return IDs ( long[] )
     */
    private static long[] idSearch(long[] IDs, Scanner scanner) {
	while (true) {
	    System.out.print("Please enter the member's ID: ");
	    long id = scanner.nextLong();
	    int i = searchId(IDs, id);
	    if (i == -1) {
		System.out.println("No member was found!");
		return IDs;
	    }
	    System.out.printf("ID: %d ---> Name: %s%n", members[i].getId(), members[i].getName());
	    for (int j = 0; j < IDs.length; j++) {
		if (j != i)
		    IDs[j] = -1;
	    }
	    break;
	}
	return IDs;
    }

    /**
     * Summary: This method prompts all available methods to be selected by the
     * operator.
     * <p>
     * 
     * @param scanner
     * @return method (int)
     */
    private static int advancePrompt(Scanner scanner) {
	int method = 0;
	String method1 = "1) Search By Name";
	String method2 = "2) Search By ID";
	String method3 = "3) Search By Age";
	String method4 = "4) Search By Gender";
	String method5 = "5) Search By Borrowed Books";
	while (method < 1 || method > 6) {
	    System.out.println("Which method do you prefer to search by?");
	    System.out.printf("%s%n%s%n%s%n%s%n%s%n", method1, method2, method3, method4, method5);
	    System.out.print("Please enter a number between 1 & 5 to select your search method: ");
	    method = scanner.nextInt();
	    System.out.println();
	}
	return method;
    }

    /**
     * Filters and limits the search results using additional prompts.
     *
     * @param scanner The scanner to read user input.
     * @return `true` if the operator wants to add more filters, `false` otherwise.
     */
    private static boolean filter(Scanner scanner) {
	// scanner.nextLine();
	String prompt = "Would you like to add a filter to limit the search results? (Yes/No)";
	return doYouWant(scanner, prompt);
    }

    /**
     * Summary: This method creates IDs as a long array which is initiated by -1
     * value and then it will assign to each not-null member Id.
     * <p>
     * 
     * @return IDs ( long[ ] )
     * 
     */
    static long[] membersIds() {
	long[] IDs = new long[getMemberNo()];
	initIds(IDs, -1);
	for (int i = 0; i < IDs.length; i++) {
	    if (members[i] != null)
		IDs[i] = members[i].getId();
	}
	return IDs;
    }

    /**
     * Summary: This method asks a Yes/No question using input prompt and if the
     * operator enters "Yes" true will be returned, otherwise false.
     * <p>
     * 
     * @param scanner (Scanner)
     *                <p>
     * @return i (int): index of desired members array
     *         <p>
     * 
     * @see project.phase2.Library.containString
     */
    private static int simpleSearch(Scanner scanner) {
	scanner.nextLine(); // To be able to use scanner.nextLine() properly.
	long[] IDs = nameSearch(membersIds(), scanner);
	return selectResult(IDs, scanner, false);
    }

    /**
     * Summary: This method iterates IDs and returns index of the desired ID.
     * 
     * @param IDs ( long[ ] )
     * @param id  (long): desired member's ID
     *            <p>
     * @return i (int): index of desired ID
     */
    static int searchId(long[] IDs, long id) {
	for (int i = 0; i < IDs.length; i++) {
	    if (id == IDs[i])
		return i;
	}
	return -1;
    }

    /**
     * Summary: This method checks whether the desired member's ID input exist among
     * found IDs.
     * 
     * @param IDs ( long[ ] )
     * @param id  (long): desired member's ID input
     *            <p>
     * @return true/false (boolean)
     */
    private static boolean checkInputId(long[] IDs, long id) {
	for (int i = 0; i < IDs.length; i++) {
	    if (id == IDs[i])
		return true;
	}
	return false;
    }

    /**
     * Searches for members based on name input.
     *
     * @param IDs     The initial list of member IDs to filter.
     * @param scanner The scanner to read user input.
     * @return The filtered list of member IDs.
     * @see project.phase2.Library.containString
     */
    private static long[] nameSearch(long[] IDs, Scanner scanner) {
	scanner.nextLine(); // To be able to use scanner.nextLine() properly.
	int found = 0;
	while (found == 0) {
	    System.out.println("Please enter the member's name.");
	    String name = getName(scanner);
	    for (int i = 0; i < IDs.length; i++) {
		if (IDs[i] != -1 && containString(members[i].getName(), name)) {
		    IDs[i] = members[i].getId();
		    System.out.printf("ID: %d ---> Name: %s%n", members[i].getId(), members[i].getName());
		    found++;
		} else
		    IDs[i] = -1;
	    }
	    if (found == 0)
		System.out.println("No one was found!");
	    if (noResult(IDs))
		return IDs;
	}
	return IDs;
    }

    /**
     * Initializes an array of IDs.
     *
     * @param IDs  The array to initialize.
     * @param init The initial value to assign.
     */
    private static void initIds(long[] IDs, int init) {
	for (int i = 0; i < IDs.length; i++) {
	    IDs[i] = init;
	}
    }

    /**
     * Displays search methods for books and allows the operator to choose one.
     *
     * @param scanner The scanner to read user input.
     * @return The index of the desired book in the books array.
     */
    public static int searchBook(Scanner scanner) {
	long[] IDs = booksIds();
	int method = 0;
	scanner.nextLine(); // To be able to use scanner.nextLine() properly.
	do {
	    method = searchBookPrompt(scanner);
	    switch (method) {
	    case 1: // Search By Title
		IDs = searchTitle(IDs, scanner, false);
		break;
	    case 2: // Search By Author
		IDs = searchAuthor(IDs, scanner, false);
		break;
	    case 3: // Search By Book ID
		IDs = searchId(IDs, scanner, false);
		break;
	    case 4: // Search By Book Cost
		IDs = searchCost(IDs, scanner, false);
		break;
	    case 5: // Search By Book Pages Number
		IDs = searchPagesNo(IDs, scanner, false);
		break;
	    case 6: // Search By Book Published Date
		IDs = searchPublishedDate(IDs, scanner, false);
		break;
	    case 7: // Search By Book Status
		IDs = searchBookStatus(IDs, scanner, false);
		break;
	    case 8: // Search By Who Borrowed The Book Info
		IDs = searchWhoBorrowed(IDs, scanner);
	    }
	    if (noResult(IDs))
		return -1;
	} while (filter(scanner));
	return selectBook(IDs, scanner);
    }

    /**
     * Displays search methods for books and allows the operator to choose one.
     *
     * @param IDs     The initial list of book IDs to filter.
     * @param scanner The scanner to read user input.
     * @return The index of the desired book in the books array.
     */
    public static int searchBook(long[] IDs, Scanner scanner) {
	int method = 0;
	scanner.nextLine(); // To be able to use scanner.nextLine() properly.
	do {
	    method = searchBookPrompt(scanner, IDs);
	    switch (method) {
	    case 1: // Search By Title
		IDs = searchTitle(IDs, scanner, false);
		break;
	    case 2: // Search By Author
		IDs = searchAuthor(IDs, scanner, false);
		break;
	    case 3: // Search By Book ID
		IDs = searchId(IDs, scanner, false);
		break;
	    case 4: // Search By Book Cost
		IDs = searchCost(IDs, scanner, false);
		break;
	    case 5: // Search By Book Pages Number
		IDs = searchPagesNo(IDs, scanner, false);
		break;
	    case 6: // Search By Book Published Date
		IDs = searchPublishedDate(IDs, scanner, false);
		break;
	    case 7: // Search By Who Borrowed The Book Info
		IDs = searchWhoBorrowed(IDs, scanner);
	    }
	    if (noResult(IDs))
		return -1;
	} while (filter(scanner));
	return selectBook(IDs, scanner);
    }

    /**
     * Displays search methods for books and allows the operator to choose one.
     *
     * @param scanner The scanner to read user input.
     * @return The index of the desired book in the books array.
     */
    private static int searchBookPrompt(Scanner scanner, long[] IDs) {
	String method1 = "1) Search By Title";
	String method2 = "2) Search By Author";
	String method3 = "3) Search By Book ID";
	String method4 = "4) Search By Book Cost";
	String method5 = "5) Search By Book Pages Number";
	String method6 = "6) Search By Book Published Date";
	String method7 = "7) Search The Book By Info Of Who Borrowed it";
	int method = 0;
	while (method < 1 || method > 8) {
	    System.out.println("Which method do you prefer to search by a book?");
	    System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s", method1, method2, method3, method4, method5, method6,
		    method7, "Please enter a number between 1-7: ");
	    method = scanner.nextInt();
	}
	return method;
    }

    /**
     * This method creates IDs as a long array which is initiated by -1 value and
     * then it will assign to each not-null books Id.
     * <p>
     * 
     * @return IDs ( long[ ] )
     */
    static long[] booksIds() {
	long[] IDs = new long[getBooksNo()];
	initIds(IDs, -1);
	for (int i = 0; i < IDs.length; i++) {
	    if (books[i] != null)
		IDs[i] = books[i].getId();
	}
	return IDs;
    }

    /**
     * Displays search methods for books and allows the operator to choose one.
     *
     * @param scanner The scanner to read user input.
     * @return The index of the desired book in the books array.
     */
    private static int searchBookPrompt(Scanner scanner) {
	String method1 = "1) Search By Title";
	String method2 = "2) Search By Author";
	String method3 = "3) Search By Book ID";
	String method4 = "4) Search By Book Cost";
	String method5 = "5) Search By Book Pages Number";
	String method6 = "6) Search By Book Published Date";
	String method7 = "7) Search By Book Status";
	String method8 = "8) Search The Book By Info Of Who Borrowed it";
	int method = 0;
	while (method < 1 || method > 8) {
	    System.out.println("Which method do you prefer to search by a book?");
	    System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s", method1, method2, method3, method4, method5,
		    method6, method7, method8, "Please enter a number between 1-8: ");
	    method = scanner.nextInt();
	}
	return method;
    }

    /**
     * This method provides variety of search methods among the lent books to find
     * IDs of members who borrowed the book.
     * <p>
     * 
     * @param IDs     ( long[] )
     * @param scanner
     *                <p>
     * @return IDs ( long[] )
     */
    private static long[] findMembersViaBookSearch(long[] IDs, Scanner scanner) {
	int method = 0;
	do {
	    method = ViaBookSearchPrompt(scanner);
	    switch (method) {
	    case 1: // Search By Title
		IDs = searchTitle(IDs, scanner, true);
		break;
	    case 2: // Search By Author
		IDs = searchAuthor(IDs, scanner, true);
		break;
	    case 3: // Search By Book ID
		IDs = searchId(IDs, scanner, true);
		break;
	    case 4: // Search By Book Cost
		IDs = searchCost(IDs, scanner, true);
		break;
	    case 5: // Search By Book Pages Number
		IDs = searchPagesNo(IDs, scanner, true);
		break;
	    case 6: // Search By Book Published Date
		IDs = searchPublishedDate(IDs, scanner, true);
		break;
	    case 7: // Search By Book Status
		IDs = searchBookStatus(IDs, scanner, true);
	    }
	} while (filter(scanner));
	return IDs;
    }

    /**
     * This method prints all the possible methods to search a book and forces the
     * operator to input one of them.
     * <p>
     * 
     * @param scanner (Scanner) The input scanner to read user input.
     *                <p>
     * @return method (int) The selected search method.
     */
    private static int ViaBookSearchPrompt(Scanner scanner) {
	String method1 = "1) Search By Title";
	String method2 = "2) Search By Author";
	String method3 = "3) Search By Book ID";
	String method4 = "4) Search By Book Cost";
	String method5 = "5) Search By Book Pages Number";
	String method6 = "6) Search By Book Published Date";
	String method7 = "7) Search By Book Status";
	int method = 0;
	while (method < 1 || method > 7) {
	    System.out.println("Which method do you prefer to seaarch by a book?");
	    System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s", method1, method2, method3, method4, method5, method6,
		    method7, "Please enter a number between 1-8: ");
	    method = scanner.nextInt();
	}
	return method;
    }

    /**
     * This method asks the operator to select the desired book by inputting its ID.
     * <p>
     * 
     * @param IDs     ( long[ ] ) An array of book IDs.
     * @param scanner (Scanner) The input scanner to read user input.
     *                <p>
     * @return i (int) The index of the desired book.
     */
    static int selectBook(long[] IDs, Scanner scanner) {
	for (int i = 0; i < IDs.length; i++) {
	    if (IDs[i] > 0)
		System.out.printf("ID: %d --> Title: %s --> Author: %s%n", books[i].getId(), books[i].getTitle(),
			books[i].getAuthor());
	}
	long id;
	do {
	    System.out.print("Please enter the ID of your desired book: ");
	    id = scanner.nextLong();
	    if (noResult(IDs))
		return -1;
	} while (!checkInputId(IDs, id));
	return searchId(IDs, id);
    }

    /**
     * Summary: This method finds members/books IDs based on the book's title
     * inputed by the operator.
     * <p>
     * 
     * @param IDs        ( long[] )
     * @param scanner
     * @param wantMember (boolean): clarifies for which purpose we are searching
     *                   among the books
     *                   <p>
     * @return IDs ( long[] )
     */
    private static long[] searchTitle(long[] IDs, Scanner scanner, boolean wantMember) {
	scanner.nextLine(); // To be able to use scanner.nextLine() properly.
	int foundResult = 0, found;
	while (true) {
	    System.out.println("Please enter the book's title.");
	    String title = inputTitle(scanner);
	    for (int i = 0; i < IDs.length; i++) {
		if (IDs[i] < 1)
		    continue; // Not to check filtered results again.
		if (wantMember) {
		    Book[] memberBooks = members[i].getBorrowedBooks();
		    found = 0;
		    for (int j = 0; j < memberBooks.length; j++) {
			if (containString(memberBooks[j].getTitle(), title)) {
			    IDs[i] = members[i].getId();
			    System.out.printf("ID: %d --> Name: %s --> Borrowed Book: %s%n", IDs[i],
				    members[i].getName(), memberBooks[j].getTitle());
			    found = ++foundResult;
			}
		    }
		} else {
		    found = 0;
		    if (containString(books[i].getTitle(), title)) {
			IDs[i] = books[i].getId();
			System.out.printf("ID: %d --> Title: %s --> Author: %s%n", IDs[i], books[i].getTitle(),
				books[i].getAuthor());
			found = ++foundResult;
		    }
		}
		if (found == 0)
		    IDs[i] = -1;
	    }
	    if (foundResult == 0)
		System.out.println("Nothing was found!");
	    else
		return IDs;
	    if (noResult(IDs))
		return IDs;
	}
    }

    /**
     * Summary: This method finds members/books IDs based on the book's author
     * inputed by the operator.
     * <p>
     * 
     * @param IDs        ( long[] )
     * @param scanner
     * @param wantMember (boolean): clarifies for which purpose we are searching
     *                   among the books
     *                   <p>
     * @return IDs ( long[] )
     */
    private static long[] searchAuthor(long[] IDs, Scanner scanner, boolean wantMember) {
	int foundResult = 0, found;
	scanner.nextLine(); // To be able to use scanner.nextLine() properly.
	while (true) {
	    System.out.println("Please enter the book's author.");
	    String author = inputAuthor(scanner);
	    for (int i = 0; i < IDs.length; i++) {
		if (IDs[i] < 1)
		    continue; // Not to check filtered results again.
		if (wantMember) {
		    Book[] memberBooks = members[i].getBorrowedBooks();
		    found = 0;
		    for (int j = 0; j < memberBooks.length; j++) {
			if (containString(memberBooks[j].getAuthor(), author)) {
			    IDs[i] = members[i].getId();
			    System.out.printf("ID: %d --> Name: %s --> Borrowed Book's Author Name: %s%n", IDs[i],
				    members[i].getName(), memberBooks[j].getAuthor());
			    found = ++foundResult;
			}
		    }
		} else {
		    found = 0;
		    if (containString(books[i].getAuthor(), author)) {
			IDs[i] = books[i].getId();
			System.out.printf("ID: %d --> Title: %s --> Author: %s%n", IDs[i], books[i].getTitle(),
				books[i].getAuthor());
			found = ++foundResult;
		    }
		}
		if (found == 0)
		    IDs[i] = -1;
	    }
	    if (foundResult == 0)
		System.out.println("Nothing was found!");
	    else
		return IDs;
	    if (noResult(IDs))
		return IDs;
	}
    }

    /**
     * Summary: This method finds members/books IDs based on the book's ID inputed
     * by the operator.
     * <p>
     * 
     * @param IDs        ( long[] )
     * @param scanner
     * @param wantMember (boolean): clarifies for which purpose we are searching
     *                   among the books
     *                   <p>
     * @return IDs ( long[] )
     */
    private static long[] searchId(long[] IDs, Scanner scanner, boolean wantMember) {
	int foundResult = 0, found;
	while (true) {
	    System.out.println("Please enter the book's ID.");
	    long id = inputId(scanner);
	    for (int i = 0; i < IDs.length; i++) {
		if (IDs[i] < 1)
		    continue; // Not to check filtered results again.
		if (wantMember) {
		    Book[] memberBooks = members[i].getBorrowedBooks();
		    found = 0;
		    for (int j = 0; j < memberBooks.length; j++) {
			if (memberBooks[j].getId() == id) {
			    IDs[i] = members[i].getId();
			    System.out.printf("ID: %d --> Name: %s --> Borrowed Book's ID: %d%n", IDs[i],
				    members[i].getName(), memberBooks[j].getId());
			    found = ++foundResult;
			}
		    }
		} else {
		    found = 0;
		    if (books[i].getId() == id) {
			IDs[i] = books[i].getId();
			System.out.printf("ID: %d --> Title: %s --> Author: %s%n", IDs[i], books[i].getTitle(),
				books[i].getAuthor());
			found = ++foundResult;
		    }
		}
		if (found == 0)
		    IDs[i] = -1;
	    }
	    if (foundResult == 0)
		System.out.println("Nothing was found!");
	    else
		return IDs;
	    if (noResult(IDs))
		return IDs;
	}
    }

    /**
     * Summary: This method finds members/books IDs based on the book's cost inputed
     * by the operator.
     * <p>
     * 
     * @param IDs        ( long[] )
     * @param scanner
     * @param wantMember (boolean): clarifies for which purpose we are searching
     *                   among the books
     *                   <p>
     * @return IDs ( long[] )
     */
    private static long[] searchCost(long[] IDs, Scanner scanner, boolean wantMember) {
	int foundResult = 0, found;
	while (true) {
	    System.out.println("Please enter the book's cost.");
	    long cost = inputCost(scanner);
	    for (int i = 0; i < IDs.length; i++) {
		if (IDs[i] < 1)
		    continue; // Not to check filtered results again.
		if (wantMember) {
		    Book[] memberBooks = members[i].getBorrowedBooks();
		    found = 0;
		    for (int j = 0; j < memberBooks.length; j++) {
			if (memberBooks[j].getCost() == cost) {
			    IDs[i] = members[i].getId();
			    System.out.printf("ID: %d --> Name: %s --> Borrowed Book's Cost (Tomans): %d%n", IDs[i],
				    members[i].getName(), memberBooks[j].getCost());
			    found = ++foundResult;
			}
		    }
		} else {
		    found = 0;
		    if (books[i].getCost() == cost) {
			IDs[i] = books[i].getId();
			System.out.printf("ID: %d --> Title: %s --> Cost (Tomans): %s%n", IDs[i], books[i].getTitle(),
				books[i].getCost());
			found = ++foundResult;
		    }
		}
		if (found == 0)
		    IDs[i] = -1;
	    }
	    if (foundResult == 0)
		System.out.println("Nothing was found!");
	    else
		return IDs;
	    if (noResult(IDs))
		return IDs;
	}
    }

    /**
     * Summary: This method finds members/books IDs based on the book's pages number
     * inputed by the operator.
     * <p>
     * 
     * @param IDs        ( long[] )
     * @param scanner
     * @param wantMember (boolean): clarifies for which purpose we are searching
     *                   among the books
     *                   <p>
     * @return IDs ( long[] )
     */
    private static long[] searchPagesNo(long[] IDs, Scanner scanner, boolean wantMember) {
	int foundResult = 0, found;
	while (true) {
	    System.out.println("Please enter the book's pages number.");
	    long pagesNo = inputPagesNo(scanner);
	    for (int i = 0; i < IDs.length; i++) {
		if (IDs[i] < 1)
		    continue; // Not to check filtered results again.
		if (wantMember) {
		    Book[] memberBooks = members[i].getBorrowedBooks();
		    found = 0;
		    for (int j = 0; j < memberBooks.length; j++) {
			if (memberBooks[j].getPagesNo() == pagesNo) {
			    IDs[i] = members[i].getId();
			    System.out.printf("ID: %d --> Name: %s --> Borrowed Book's Pages Number: %d%n", IDs[i],
				    members[i].getName(), memberBooks[j].getPagesNo());
			    found = ++foundResult;
			}
		    }
		} else {
		    found = 0;
		    if (books[i].getPagesNo() == pagesNo) {
			IDs[i] = books[i].getId();
			System.out.printf("ID: %d --> Title: %s --> Pages Number: %s%n", IDs[i], books[i].getTitle(),
				books[i].getPagesNo());
			found = ++foundResult;
		    }
		}
		if (found == 0)
		    IDs[i] = -1;
	    }
	    if (foundResult == 0)
		System.out.println("Nothing was found!");
	    else
		return IDs;
	    if (noResult(IDs))
		return IDs;
	}
    }

    /**
     * Summary: This method finds members/books IDs based on the book's published
     * date inputed by the operator.
     * <p>
     * 
     * @param IDs        ( long[] )
     * @param scanner
     * @param wantMember (boolean): clarifies for which purpose we are searching
     *                   among the books
     *                   <p>
     * @return IDs ( long[] )
     */
    private static long[] searchPublishedDate(long[] IDs, Scanner scanner, boolean wantMember) {
	int foundResult = 0, found;
	while (true) {
	    Object searchDate = searchDateTypes(scanner);
	    System.out.println("Please enter the book's publisehed date.");
	    for (int i = 0; i < IDs.length; i++) {
		if (IDs[i] < 1)
		    continue; // Not to check filtered results again.
		if (wantMember) {
		    Book[] memberBooks = members[i].getBorrowedBooks();
		    found = 0;
		    for (int j = 0; j < memberBooks.length; j++) {
			if (searchDateCondition(memberBooks[j], searchDate)) {
			    IDs[i] = members[i].getId();
			    System.out.printf("ID: %d --> Name: %s --> Borrowed Book's Published Date: %s%n", IDs[i],
				    members[i].getName(), memberBooks[j].getPublishedDate());
			    found = ++foundResult;
			}
		    }
		} else {
		    found = 0;
		    if (searchDateCondition(books[i], searchDate)) {
			IDs[i] = books[i].getId();
			System.out.printf("ID: %d --> Title: %s --> Published Date: %s%n", IDs[i], books[i].getTitle(),
				books[i].getPublishedDate());
			found = ++foundResult;
		    }
		}
		if (found == 0)
		    IDs[i] = -1;
	    }
	    if (foundResult == 0)
		System.out.println("Nothing was found!");
	    else
		return IDs;
	    if (noResult(IDs))
		return IDs;
	}
    }

    /**
     * Summary: This method returns an Object which holds whether a date (Date class
     * object) or the variable year (integer) based on the date search type ordered
     * by the operator.
     * <p>
     * 
     * @param scanner (Scanner)
     *                <p>
     * @return Object: This object may contain a Date class object or the variable
     *         year as an integer.
     */
    private static Object searchDateTypes(Scanner scanner) {
	String prompt = "Would you prefer to search solely based on the book's published year? (Yes/No)";
	if (doYouWant(scanner, prompt)) {
	    return Date.inputYear(scanner); // year (int)
	} else
	    return new Date(); // date (Date)
    }

    /**
     * Summary: This method checks what the type of searchDate (input Object) is and
     * based on its type will return true if the input published date (year solely
     * or the complete date) is as same as book's published date.
     * <p>
     * 
     * @param book       (Book)
     * @param searchDate (Object): This object may contain a Date class object or
     *                   the variable year as an integer.
     *                   <p>
     * @return true/false (boolean)
     */
    private static boolean searchDateCondition(Book book, Object searchDate) {
	if (searchDate instanceof Integer) {
	    int year = (int) searchDate;
	    if (book.getPublishedDate().getYear() == year)
		return true;
	} else {
	    Date date = (Date) searchDate;
	    if (book.getPublishedDate().equals(date))
		return true;
	}
	return false;
    }

    /**
     * Summary: This method finds members/books IDs based on the book's status
     * inputed by the operator.
     * <p>
     * 
     * @param IDs        ( long[] )
     * @param scanner
     * @param wantMember (boolean): clarifies for which purpose we are searching
     *                   among the books
     *                   <p>
     * @return IDs ( long[] )
     */
    private static long[] searchBookStatus(long[] IDs, Scanner scanner, boolean wantMember) {
	int foundResult = 0, found;
	BookStatus state = BookStatus.Loaned;
	scanner.nextLine(); // To be able to use scanner.nextLine() properly.
	while (true) {
	    if (!wantMember) {
		System.out.println("Please enter the book's status." + " (" + BookStatus.Ready + "/" + BookStatus.Loaned
			+ "/" + BookStatus.Binding + ")");
		state = BookStatus.inputState(scanner);
		System.out.println("Check 1");
	    }
	    
	    for (int i = 0; i < IDs.length; i++) {
		if (IDs[i] < 1)
		    continue; // Not to check filtered results again.
		if (wantMember) {
		    Book[] memberBooks = members[i].getBorrowedBooks();
		    found = 0;
		    for (int j = 0; j < memberBooks.length; j++) {
			if (memberBooks[j].getState() == state) {
			    IDs[i] = members[i].getId();
			    System.out.printf("ID: %d --> Name: %s --> Borrowed Book's Status: %s%n", IDs[i],
				    members[i].getName(), memberBooks[j].getState());
			    found = ++foundResult;
			}
		    }
		} else {
		    found = 0;
		    if (books[i].getState().equals(state)) {
			IDs[i] = books[i].getId();
			System.out.printf("ID: %d --> Title: %s --> Book's Status: %s%n", IDs[i], books[i].getTitle(),
				books[i].getState());
			found = ++foundResult;
		    }
		}
		if (found == 0)
		    IDs[i] = -1;
	    }
	    if (foundResult == 0)
		System.out.println("Nothing was found!");
	    else
		return IDs;
	    if (noResult(IDs))
		return IDs;
	}
    }

    /**
     * Checks if there are no valid results in the given array of IDs.
     *
     * This method iterates through the array of IDs and checks if any of the IDs is
     * greater than 0, indicating a valid result. If it finds at least one valid
     * result, it returns false; otherwise, if all IDs are non-positive (less than
     * or equal to 0), it returns true.
     *
     * @param IDs (long[]) An array of IDs to be checked for valid results.
     *            <p>
     * @return true/false (boolean) {@code true} if there are no valid results (all
     *         IDs are non-positive), {@code false} otherwise.
     */
    private static boolean noResult(long[] IDs) {
	for (int i = 0; i < IDs.length; i++) {
	    if (IDs[i] > 0)
		return false;
	}
	return true;
    }

    /**
     * Summary: This method finds books IDs based on the information of who borrowed
     * them inputed by the operator.
     * <p>
     * 
     * @param IDs     ( long[] ) An array of book IDs.
     * @param scanner (Scanner) The input scanner to read user input.
     *                <p>
     * @return IDs (long[]) An updated array of book IDs after applying the search
     *         criteria.
     */
    private static long[] searchWhoBorrowed(long[] IDs, Scanner scanner) {
	int method = 0;
	scanner.nextLine(); // To be able to use scanner.nextLine() properly.
	while (method != 5) {
	    method = whoBorrowedPrompt(scanner);
	    switch (method) {
	    case 1: //
		IDs = whoBorrowedName(IDs, scanner);
		break;
	    case 2: // Search By ID
		IDs = whoBorrowedId(IDs, scanner);
		break;
	    case 3: // Search By Age
		IDs = whoBorrowedAge(IDs, scanner);
		break;
	    case 4: // Search By Gender
		IDs = whoBorrowedGender(IDs, scanner);
	    }
	    if (noResult(IDs))
		return IDs;
	    if (!filter(scanner))
		System.out.println("Back to the searching among books methods section.");
	    return IDs;
	}
	return IDs;
    }

    /**
     * Summary: This method prompts all available methods to be selected by the
     * operator.
     * <p>
     * 
     * @param scanner
     * @return method (int)
     */
    private static int whoBorrowedPrompt(Scanner scanner) {
	int method = 0;
	String method1 = "1) Search By Name";
	String method2 = "2) Search By ID";
	String method3 = "3) Search By Age";
	String method4 = "4) Search By Gender";
	String method5 = "5) Exit The Section";
	while (method < 1 || method > 5) {
	    System.out.println("Which method do you prefer to find books by info of who borrowed them?");
	    System.out.printf("%s%n%s%n%s%n%s%n%s%n", method1, method2, method3, method4, method5);
	    System.out.print("Please enter a number between 1 & 4 to input info of the person: ");
	    method = scanner.nextInt();
	    System.out.println();
	}
	return method;
    }

    /**
     * Summary: This method finds books IDs based on the name of who borrowed them
     * inputed by the operator.
     * <p>
     * 
     * @param IDs     ( long[] )
     * @param scanner
     *                <p>
     * @return IDs ( long[] )
     */
    private static long[] whoBorrowedName(long[] IDs, Scanner scanner) {
	int foundResult = 0;
	scanner.nextLine(); // To be able to use scanner.nextLine() properly.
	while (true) {
	    System.out.println("Please enter the name of who borrowed the book.");
	    String name = getName(scanner);
	    for (int i = 0; i < IDs.length; i++) {
		if (IDs[i] > 0 && books[i].getState() == BookStatus.Loaned
			&& containString(books[i].getLentToMember().getName(), name)) {
		    IDs[i] = books[i].getId();
		    System.out.printf("ID: %d --> Title: %s --> Name of Who Borrowed it: %s%n", IDs[i],
			    books[i].getTitle(), books[i].getLentToMember().getName());
		    foundResult++;
		} else
		    IDs[i] = -1;
	    }
	    if (foundResult == 0)
		System.out.println("Nothing was found!");
	    else
		return IDs;
	}
    }

    /**
     * Summary: This method finds books IDs based on the ID of who borrowed them
     * inputed by the operator.
     * <p>
     * 
     * @param IDs     ( long[] )
     * @param scanner
     *                <p>
     * @return IDs ( long[] )
     */
    private static long[] whoBorrowedId(long[] IDs, Scanner scanner) {
	int foundResult = 0;
	while (true) {
	    System.out.println("Please enter ID of who borrowed the book.");
	    long id = Member.inputId(scanner);
	    for (int i = 0; i < IDs.length; i++) {
		if (IDs[i] > 0 && books[i].getState() == BookStatus.Loaned
			&& books[i].getLentToMember().getId() == id) {
		    IDs[i] = books[i].getId();
		    System.out.printf("ID: %d --> Title: %s --> ID of Who Borrowed it: %d%n", IDs[i],
			    books[i].getTitle(), id);
		    foundResult++;
		} else
		    IDs[i] = -1;
	    }
	    if (foundResult == 0)
		System.out.println("Nothing was found!");
	    else
		return IDs;
	}
    }

    /**
     * Summary: This method finds books IDs based on age of who borrowed them
     * inputed by the operator.
     * <p>
     * 
     * @param IDs     ( long[] )
     * @param scanner
     *                <p>
     * @return IDs ( long[] )
     */
    private static long[] whoBorrowedAge(long[] IDs, Scanner scanner) {
	int foundResult = 0;
	while (true) {
	    System.out.println("Please enter age of who borrowed the book.");
	    long age = getAge(scanner);
	    for (int i = 0; i < IDs.length; i++) {
		if (IDs[i] > 0 && books[i].getState() == BookStatus.Loaned
			&& books[i].getLentToMember().getAge() == age) {
		    IDs[i] = books[i].getId();
		    System.out.printf("ID: %d --> Title: %s --> Age of Who Borrowed it: %d%n", IDs[i],
			    books[i].getTitle(), age);
		    foundResult++;
		} else
		    IDs[i] = -1;
	    }
	    if (foundResult == 0)
		System.out.println("Nothing was found!");
	    else
		return IDs;
	}
    }

    /**
     * Summary: This method finds books IDs based on gender of who borrowed them
     * inputed by the operator.
     * <p>
     * 
     * @param IDs     ( long[] )
     * @param scanner
     *                <p>
     * @return IDs ( long[] )
     */
    private static long[] whoBorrowedGender(long[] IDs, Scanner scanner) {
	int foundResult = 0;
	scanner.nextLine(); // To be able to use scanner.nextLine() properly.
	while (true) {
	    System.out.println("Please enter gender of who borrowed the book.");
	    String gender = getGender(scanner);
	    for (int i = 0; i < IDs.length; i++) {
		if (IDs[i] > 0 && books[i].getState() == BookStatus.Loaned
			&& books[i].getLentToMember().getGender().equalsIgnoreCase(gender)) {
		    IDs[i] = books[i].getId();
		    System.out.printf("ID: %d --> Title: %s --> Gender of Who Borrowed it: %s%n", IDs[i],
			    books[i].getTitle(), gender);
		    foundResult++;
		} else
		    IDs[i] = -1;
	    }
	    if (foundResult == 0)
		System.out.println("Nothing was found!");
	    else
		return IDs;
	}
    }
}