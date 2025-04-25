package project.phase2.books;

import static project.phase2.Library.*;

import java.util.Scanner;

/**
 * This enum represents the possible statuses of a book in the library.
 * <p>
 * The `BookStatus` enum class defines three possible statuses for a book:
 * "Ready", "Loaned", and "Binding". These statuses are used to indicate whether
 * a book is available for borrowing, currently loaned to a member, or being
 * bound, respectively. The enum also provides a utility method `inputState` for
 * prompting the user to input a book status and returning the corresponding
 * enum value.
 * <p>
 * <b>Author:</b> Hamed Salmnizadegan @2023
 */
public enum BookStatus {
    Ready, Loaned, Binding;

    private static final long serialVersionUID = 6653163296668750535L;

    /**
     * Prompts the user to input a book status and returns the corresponding enum
     * value.
     *
     * This method prompts the user to input a book status (as a string) using the
     * provided scanner. It continuously checks the input against the possible
     * status values ("Ready", "Loaned", "Binding"), and returns the corresponding
     * `BookStatus` enum value once a valid status is entered.
     *
     * @param scanner The scanner to use for input.
     * @return The `BookStatus` enum value corresponding to the input status.
     */
    public static BookStatus inputState(Scanner scanner) {
        String state;
        while (true) {
            System.out.print("Book's Status: ");
            state = scanner.nextLine();
            if (containString("Ready", state))
                return Ready;
            if (containString("Loaned", state))
                return Loaned;
            if (containString("Binding", state))
                return Binding;
        }
    }
}