package project.phase2.books;

import java.io.Serializable;

import java.util.Objects;

import java.util.Scanner;

/**
 * This class represents dates and provides methods related to date
 * manipulation.
 * <p>
 * The `Date` class defines a basic structure for representing dates with day,
 * month, and year components. It includes methods for user input validation and
 * comparison of dates. The class provides methods to input and validate day,
 * month, and year components, as well as to check if a date is after another
 * date.
 * <p>
 * <b>Author:</b> Hamed Salmnizadegan @2023
 */
public class Date implements Serializable {
    private static final long serialVersionUID = 6653163296668750535L;
    private int day;
    private int month;
    private int year;

    /**
     * The constructor of the class that initializes the date components.
     *
     * This constructor initializes the date components (day, month, and year) by
     * prompting the user for input using a scanner. The methods `inputYear`,
     * `inputMonth`, and `inputDay` are used to ensure valid input for each
     * component.
     */
    public Date() {
	Scanner scannerDate = new Scanner(System.in);
	year = inputYear(scannerDate);
	month = inputMonth(scannerDate);
	day = inputDay(scannerDate);
    }

    /**
     * Returns the string representation of the date.
     *
     * This method returns a string in the format "day/month/year" representing the
     * date components.
     *
     * @return The formatted date string.
     */
    @Override
    public String toString() {
	return day + "/" + month + "/" + year;
    }

    /**
     * Generates a hash code for the date object.
     *
     * This method generates a hash code based on the day, month, and year
     * components of the date object.
     *
     * @return The hash code of the date.
     */
    @Override
    public int hashCode() {
	return Objects.hash(day, month, year);
    }

    /**
     * Compares this date with another object for equality.
     *
     * This method compares the day, month, and year components of two date objects
     * to determine if they are equal.
     *
     * @param obj The object to compare to.
     * @return `true` if the objects are equal, `false` otherwise.
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Date other = (Date) obj;
	return day == other.day && month == other.month && year == other.year;
    }

    /**
     * Returns the day component of the date.
     *
     * @return The day component.
     */
    public int getDay() {
	return day;
    }

    /**
     * Sets the day component of the date.
     *
     * @param day The new day component to set.
     */
    public void setDay(int day) {
	this.day = day;
    }

    /**
     * Returns the month component of the date.
     *
     * @return The month component.
     */
    public int getMonth() {
	return month;
    }

    /**
     * Sets the month component of the date.
     *
     * @param month The new month component to set.
     */
    public void setMonth(int month) {
	this.month = month;
    }

    /**
     * Returns the year component of the date.
     *
     * @return The year component.
     */
    public int getYear() {
	return year;
    }

    /**
     * Sets the year component of the date.
     *
     * @param year The new year component to set.
     */
    public void setYear(int year) {
	this.year = year;
    }

    /**
     * Forces the operator to input a valid day.
     *
     * This method prompts the operator to input a valid day value between 1 and 31,
     * and returns the validated day value.
     *
     * @param scanner The scanner to use for input.
     * @return The validated day value.
     */
    public static int inputDay(Scanner scanner) {
	int day = 0;
	do {
	    System.out.print("Day: ");
	    day = scanner.nextInt();
	} while (day < 1 || day > 31);
	return day;
    }

    /**
     * Forces the operator to input a valid month.
     *
     * This method prompts the operator to input a valid month value between 1 and
     * 12, and returns the validated month value.
     *
     * @param scanner The scanner to use for input.
     * @return The validated month value.
     */
    public static int inputMonth(Scanner scanner) {
	int month = 0;
	do {
	    System.out.print("Month: ");
	    month = scanner.nextInt();
	} while (month < 1 || month > 12);
	return month;
    }

    /**
     * Forces the operator to input a valid year.
     *
     * This method prompts the operator to input a valid year value between 200 and
     * 2023, and returns the validated year value.
     *
     * @param scanner The scanner to use for input.
     * @return The validated year value.
     */
    public static int inputYear(Scanner scanner) {
	int year = 0;
	do {
	    System.out.print("Year: ");
	    year = scanner.nextInt();
	} while (year < 200 || year > 2123);
	return year;
    }

    /**
     * This method checks whether this date is after that date (the input parameter)
     * and if this date if before that date or at the same date it returns false.
     * <p>
     * 
     * @param that (Date)
     *             <p>
     * @return true/false (boolean)
     */
    public boolean isAfter(Date that) {
	if (this.year < that.getYear())
	    return false;
	else if (this.year > that.getYear())
	    return true;
	if (this.month < that.getMonth())
	    return false;
	else if (this.month > that.getMonth())
	    return true;
	if (this.day < that.getDay())
	    return false;
	else if (this.day > that.getDay())
	    return true;
	return false;
    }
}