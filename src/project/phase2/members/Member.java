package project.phase2.members;

import java.io.Serializable;

import java.util.Scanner;

import project.phase2.Library;

import project.phase2.books.Book;

/**
 * This class provides information about a member held in the library.
 * <p>
 * The `Member` class represents a library member with attributes such as their
 * unique ID, name, gender, age, and the books they have borrowed. This class
 * allows setting and getting member attributes, as well as methods for
 * generating unique IDs for new members and interacting with borrowed books.
 * <p>
 * <b>Author:</b> Hamed Salmnizadegan @2023
 */
public class Member implements Serializable {
    private static final long serialVersionUID = 6653163296668750535L;
    private static long instances = 0;
    private final long id;
    private String name;
    private String gender;
    private long age;
    /**
     * borrowedBooks (Book[]): Each member can borrow maximum 10 books
     * simultaneously.
     */
    private Book[] borrowedBooks = new Book[10];

    /**
     * The only constructor of the class.
     *
     * @param name   The name of the member.
     * @param gender The gender of the member.
     * @param age    The age of the member.
     */
    public Member(String name, String gender, long age) {
	id = setId();
	this.name = name;
	this.gender = gender;
	this.age = age;
    }

    /**
     * Sets and returns a unique ID for a new member.
     *
     * This method generates a unique ID for a new member in the Library system. It
     * searches through the existing array of books to find the highest existing ID
     * and sets the 'instances' variable to that value. The 'instances' variable is
     * a static class variable that keeps track of the highest ID assigned to any
     * member so far.
     *
     * @return A new unique ID for the member to be added.
     */
    private static long setId() {
	Member[] members = Library.getMembers();
	if (members != null) {
	    for (int i = 0; i < members.length; i++) {
		if (members[i] != null && members[i].getId() > instances)
		    instances = members[i].getId();
	    }
	}
	return ++instances;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
	this.gender = gender;
    }

    /**
     * @param age the age to set
     */
    public void setAge(long age) {
	this.age = age;
    }

    /**
     * @param borrowedBooks the borrowedBooks to set
     */
    public void setBorrowedBooks(Book[] borrowedBooks) {
	this.borrowedBooks = borrowedBooks;
    }

    /**
     * @return the instances
     */
    public static long getInstances() {
	return instances;
    }

    /**
     * @return the id
     */
    public Long getId() {
	return id;
    }

    /**
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @return the gender
     */
    public String getGender() {
	return gender;
    }

    /**
     * @return the age
     */
    public Long getAge() {
	return age;
    }

    /**
     * @return the borrowedBooks
     */
    public Book[] getBorrowedBooks() {
	return borrowedBooks;
    }

    /**
     * Returns the formatted information about the member.
     *
     * @return A formatted string containing member information.
     */
    @Override
    public String toString() {
	String info = "ID: " + id + "  Name: " + name + "  Age: " + age + "  Gender: " + gender + "\n";
	boolean noBorrowed = true;
	for (int j = 0; j < borrowedBooks.length; j++) {
	    if (borrowedBooks[j] != null) {
		info += "Borrowed Books:\n" + borrowedBooks[j];
		noBorrowed = false;
	    }
	}
	if (noBorrowed)
	    info += "Has not borrowed any books yet.\n";
	return info;
    }

    /**
     * Prompts the user to input a member ID using the provided scanner.
     *
     * @param scanner The scanner to use for input.
     * @return The inputted member ID.
     */
    public static long inputId(Scanner scanner) {
	long id = 0;
	do {
	    System.out.print("Member ID: ");
	    id = scanner.nextLong();
	} while (id < 1);
	return id;
    }
}