package project.phase2.books;

import java.util.Scanner;

import project.phase2.members.Member;

import project.phase2.Library;

import java.io.Serializable;

/**
 * This class provides information about a book held in the library.
 * <p>
 * The `Book` class represents a book with attributes such as its unique ID,
 * title, author, cost, number of pages, published date, returning date, state
 * (e.g., ready or loaned), and the member to whom it's lent. This class allows
 * setting and getting book attributes, as well as methods for generating unique
 * IDs for new books, interacting with borrowed books, and managing book
 * properties like title, author, cost, etc.
 * <p>
 * <b>Author:</b> Hamed Salmnizadegan @2023
 */
public class Book implements Serializable {

    private static final long serialVersionUID = 6653163296668750535L;
    // Properties
    private static long instances = 0;
    private long id;
    private String title;
    private String author;
    private long cost;
    private long pagesNo;
    private Date publishedDate;
    private Date returningDate;
    private BookStatus state = BookStatus.Ready;
    /**
     * lentToMember (Member): Indicates address of the member who borrowed the book.
     */
    private Member lentToMember = null;

    /**
     * The only constructor of the class.
     *
     * This constructor initializes a new book with attributes including its unique
     * ID, title, author, cost, number of pages, and published date. The book's ID
     * is generated using the `setId` method.
     */
    public Book() {
	id = setId();
	Scanner scannerBook = new Scanner(System.in);
	title = inputTitle(scannerBook);
	author = inputAuthor(scannerBook);
	cost = inputCost(scannerBook);
	pagesNo = inputPagesNo(scannerBook);
	System.out.println("Please input the book published date.");
	publishedDate = new Date();
    }

    /**
     * Sets and returns a unique ID for a new book.
     *
     * This method is responsible for generating a unique ID for a new book in the
     * Library system. It searches through the existing array of books to find the
     * highest existing ID and sets the 'instances' variable to that value. The
     * 'instances' variable is a static class variable that keeps track of the
     * highest ID assigned to any book so far.
     *
     * @return A new unique ID for the book to be added.
     */
    private static long setId() {
	Book[] books = Library.getBooks();
	if (books != null) {
	    for (int i = 0; i < books.length; i++) {
		if (books[i] != null && books[i].getId() > instances)
		    instances = books[i].getId();
	    }
	}
	return ++instances;
    }

    /**
     * Prompts the user to input the number of pages using the provided scanner.
     *
     * @param scanner The scanner to use for input.
     * @return The inputted number of pages.
     */
    public static long inputPagesNo(Scanner scanner) {
	long pageNo = 0;
	do {
	    System.out.print("Number Of Pages: ");
	    pageNo = scanner.nextLong();
	} while (pageNo < 10);
	return pageNo;
    }

    /**
     * 
     * @param scanner
     * @return cost (long)
     */
    public static long inputCost(Scanner scanner) {
	long cost = 0;
	do {
	    System.out.print("Cost (Tomans): ");
	    cost = scanner.nextLong();
	} while (cost < 20000);
	return cost;
    }

    /**
     * 
     * @param scanner
     * @return author (String)
     */
    public static String inputAuthor(Scanner scanner) {
	String author = null;
	do {
	    System.out.print("Author: ");
	    author = scanner.nextLine();
	} while (author.length() == 0);
	return author;
    }

    /**
     * 
     * @param scanner
     * @return title (String)
     */
    public static String inputTitle(Scanner scanner) {
	String title = null;
	do {
	    System.out.print("Title: ");
	    title = scanner.nextLine();
	} while (title.length() == 0);
	return title;
    }

    /**
     * 
     * @param scanner
     * @return id (long)
     */
    public static long inputId(Scanner scanner) {
	long id = 0;
	do {
	    System.out.print("Book ID: ");
	    id = scanner.nextLong();
	} while (id < 1);
	return id;
    }

    // Behaviors or Methods

    public void setCost(long cost) {
	this.cost = cost;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
	this.author = author;
    }

    /**
     * @param pagesNo the pagesNo to set
     */
    public void setPagesNo(long pagesNo) {
	this.pagesNo = pagesNo;
    }

    /**
     * @param lentToMember the lentToMember to set
     */
    public void setLentToMember(Member lentToMember) {
	this.lentToMember = lentToMember;
    }

    public long getCost() {
	return cost;
    }

    public long getPagesNo() {
	return pagesNo;
    }

    public void setPublishedDate(Date publishedDate) {
	this.publishedDate = publishedDate;
    }

    /**
     * @param returningDate the returningDate to set
     */
    public void setReturningDate(Date returningDate) {
	if (state.equals(BookStatus.Loaned))
	    this.returningDate = returningDate;
    }

    public Date getPublishedDate() {
	return publishedDate;
    }

    /**
     * @return the returningDate
     */
    public Date getReturningDate() {
	return returningDate;
    }

    public String getTitle() {
	return title;
    }

    public String getAuthor() {
	return author;
    }

    public long getId() {
	return id;
    }

    public void lendBook(Member lentToMember) {
	if (state == BookStatus.Ready) {
	    state = BookStatus.Loaned;
	    this.lentToMember = lentToMember;
	} else
	    System.out.printf("Oops, Sorry!%nThe book is not ready to be lent.%n");
    }

    /**
     * @return the lentToMember
     */
    public Member getLentToMember() {
	return lentToMember;
    }

    public void returnBook() {
	state = BookStatus.Ready;
	lentToMember = null;
    }

    public void bookBinding() {
	if (state == BookStatus.Loaned) {
	    System.out.printf("Oops, Sorry!%nThe book has been lent and you'll have to wait.%n");
	} else
	    state = BookStatus.Binding;
    }

    /**
     * Returns the formatted information about the book.
     *
     * @return A formatted string containing book information.
     */
    @Override
    public String toString() {
	String info = "Book ID: " + id + ", " + "Title: " + title + ", " + "Author: " + author + ", "
		+ "Cost (Tomans): " + cost + ", " + "Pages No: " + pagesNo + ", " + "Published Date: " + publishedDate
		+ ", " + "Status: " + state.toString();
	if (state.equals(BookStatus.Loaned))
	    info += ", " + "Lent To: " + lentToMember.getName() + ", " + "Member ID: " + lentToMember.getId() + ", "
		    + "Return Date: " + returningDate;
	return info;
    }

    /**
     * Compares this book to the specified object to check for equality.
     *
     * @param obj The object to compare against this book.
     * @return `true` if the books are equal, `false` otherwise.
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null || getClass() != obj.getClass())
	    return false;
	Book book2 = (Book) obj;
	return title.equalsIgnoreCase(book2.getTitle()) && author.equalsIgnoreCase(book2.getAuthor())
		&& cost == book2.getCost();
    }

    /**
     * @return The number of instances of `Book` class created so far.
     */
    public static long getBooksInstances() {
	return instances;
    }

    /**
     * @return The state of the book.
     */
    public BookStatus getState() {
	return state;
    }

    /**
     * Sets the state of the book.
     *
     * @param state The state to set for the book.
     */
    public void setState(BookStatus state) {
	this.state = state;
    }
}