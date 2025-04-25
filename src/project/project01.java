package project;

import java.util.Scanner;

public class project01 {
    public static void main(String[] args) {
	Scanner scanner = new Scanner(System.in);

	// Input of the number of library members
	int memNo = getMembersNo(scanner);

	// Input of desired action to edit library Doc
	int inputAction = getAction(scanner);

	// Create library Doc
	String[] memNames = new String[memNo];
	String[] memGender = new String[memNo];
	int[] memAge = new int[memNo];
	int[] memID = new int[memNo];
	createMemID(memID);
	String[] memBooks = new String[memNo];

	while (inputAction != 6) {
	    switch (inputAction) {
	    case 1: // Add a member
		addMember(memNames, memGender, memAge, memID, memBooks, scanner);
		break;
	    case 2: // Delete a member
		deleteMember(memNames, memGender, memAge, memID, memBooks, scanner);
		break;
	    case 3: // Edit a member
		editMember(memNames, memGender, memAge, memID, memBooks, scanner);
		break;
	    case 4: // Show a member
		showMember(memNames, memGender, memAge, memID, memBooks, scanner);
		break;
	    case 5: // Manage lent books
		manageLentBooks(memNames, memGender, memAge, memID, memBooks, scanner);
		// System.out.println(Arrays.toString(memID));
		break;
	    }
	    inputAction = getAction(scanner);
	}
	scanner.close();
    }

    private static int getMembersNo(Scanner scanner) {
	System.out.print("Please enter number of prospective library members: ");
	int memNo = 0;
	while (memNo <= 0) {
	    memNo = scanner.nextInt();
	    if (memNo <= 0)
		System.out.println("Please enter number of members as an integer number bigger than zero. : )");
	}
	System.out.println();
	return memNo;
    }

    private static int getAction(Scanner scanner) {
	int inputAction = 0;
	while (inputAction <= 0 || inputAction > 6) {
	    String action1 = "1) Add a member";
	    String action2 = "2) Delete a member";
	    String action3 = "3) Edit a member";
	    String action4 = "4) Show a member";
	    String action5 = "5) Manage lent books";
	    String action6 = "6) Exit";

	    System.out.println("What would you like to do?");
	    System.out.printf("%s%n%s%n%s%n%s%n%s%n%s%n", action1, action2, action3, action4, action5, action6);
	    System.out.print("Please enter a number between 1 & 6: ");
	    inputAction = scanner.nextInt();
	    System.out.println();
	}
	return inputAction;
    }

    private static void createMemID(int[] memID) {
	for (int i = 0; i < memID.length; i++) {
	    memID[i] = -1;
	}
    }

    private static void addMember(String[] memNames, String[] memGender, int[] memAge, int[] memID, String[] memBooks,
	    Scanner scanner) {
	scanner.nextLine();
	boolean filled = true;
	for (int i = 0; i < memID.length; i++) {
	    if (memID[i] < 1) {
		memID[i] = i + 1;
		getName(memNames, i, scanner);
		getGender(memGender, i, scanner);
		getAge(memAge, i, scanner);
		System.out.println("New member ID is " + memID[i] + ".");
		createBorrowedBook(i, memBooks);
		System.out.println();
		filled = false;
		break;
	    }
	}
	if (filled)
	    System.out.println("There is no more space to add a member. : (");
    }

    private static void createBorrowedBook(int i, String[] memBooks) {
	memBooks[i] = "Has Not borrowed yet!";
    }

    private static void getName(String[] memNames, int id, Scanner scanner) {
	System.out.print("Name: ");
	memNames[id] = scanner.nextLine();
    }

    private static void getGender(String[] memGender, int id, Scanner scanner) {
	System.out.print("Gender (M/F): ");
	while (true) {
	    String name = scanner.nextLine();
	    if (name.compareToIgnoreCase("Male") == name.length() - 4
		    || name.compareToIgnoreCase("Female") == name.length() - 6) {
		memGender[id] = name;
		break;
	    } else {
		System.out.println("Please enter a valid gender name.");
		System.out.print("Gender (M/F): ");
	    }
	}
    }

    private static void getAge(int[] memAge, int id, Scanner scanner) {
	System.out.print("Age: ");
	while (true) {
	    int age = scanner.nextInt();
	    if (age < 5) {
		System.out.println("Please enter a valid age.(above 5 years old.)");
		System.out.print("Age: ");
	    } else {
		memAge[id] = age;
		break;
	    }
	}
    }

    private static void deleteMember(String[] memNames, String[] memGender, int[] memAge, int[] memID,
	    String[] memBooks, Scanner scanner) {
	int id = searchPrompt(memNames, memGender, memAge, memID, memBooks, scanner);
	showMemberInfo(id, memNames, memGender, memAge, memBooks);
	scanner.nextLine();
	String prompt = "Would you like to delete the member? (Yes/No)";
	if (doYouWant(scanner, prompt))
	    deletID(id, memNames, memGender, memAge, memID, memBooks);
    }

    private static boolean doYouWant(Scanner scanner, String prompt) {
	while (true) {
	    System.out.println(prompt);
	    String input = scanner.nextLine();
	    if (input.compareToIgnoreCase("Yes") == input.length() - 3)
		return true;
	    else if (input.compareToIgnoreCase("No") == input.length() - 2)
		return false;
	}
    }

    private static void deletID(int id, String[] memNames, String[] memGender, int[] memAge, int[] memID,
	    String[] memBooks) {
	memID[id] = -1;
	memNames[id] = "Deleted Member";
	memGender[id] = "Deleted Member";
	memAge[id] = -1;
	memBooks[id] = "Deleted Member";
    }

    private static int searchPrompt(String[] memNames, String[] memGender, int[] memAge, int[] memID, String[] memBooks,
	    Scanner scanner) {
	int method = 0, id = -1;
	while (!(method == 1 || method == 2)) {
	    System.out.println("How do you prefer to look for a member?");
	    System.out.printf("%s%n%s%n", "1) Search by name", "2) Advanced search");
	    System.out.print("Please enter a number (1 or 2) to select your search method: ");
	    method = scanner.nextInt();
	    System.out.println();
	}
	switch (method) {
	case 1: // search by name
	    id = simpleSearch(memNames, memID, scanner);
	    break;
	case 2: // advance search
	    id = advanceSearch(memNames, memGender, memAge, memID, memBooks, scanner);
	}
	return id;
    }

    private static int simpleSearch(String[] memNames, int[] memID, Scanner scanner) {
	scanner.nextLine();
	int[] IDs = nameSearch(memNames, memID, scanner);
	int id = 0;
	while (true) {
	    System.out.print("Please enter the ID of your desired member: ");
	    id = scanner.nextInt();
	    if (id > 0 && IDs[id - 1] != -1) // ?????????????? Edited
		break;
	}
	return id - 1;
    }

    private static int[] nameSearch(String[] memNames, int[] memID, Scanner scanner) {
	scanner.nextLine();
	int[] IDs = new int[memID.length];
	createMemID(IDs);
	int found = 0;
	while (true) {
	    System.out.println("Please enter the member's name.");
	    String name = scanner.nextLine();
	    for (int i = 0; i < memID.length; i++) {
		if (memID[i] != -1 && containString(memNames[i], name)) {
		    IDs[i] = memID[i];
		    System.out.printf("ID: %d ------> Name: %s%n", memID[i], memNames[i]);
		    found++;
		}
		if (i + 1 == memID.length || memID[i + 1] == 0)
		    break;
		else if (memID[i + 1] == -1)
		    continue;
	    }
	    if (found == 0)
		System.out.println("No one was found!");
	    else
		break;
	}
	return IDs;
    }

    private static boolean containString(String baseString, String deiredStr) { // says that whether the base string
	// contains the desired string or not
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

    private static int advanceSearch(String[] memNames, String[] memGender, int[] memAge, int[] memID,
	    String[] memBooks, Scanner scanner) {
	int method = 0, id = -1;
	int[] IDs = memID;
	// scanner.nextLine();
	while (true) {
	    method = advancePrompt(scanner);
	    switch (method) {
	    case 1: // Search by name
		IDs = nameSearch(memNames, IDs, scanner);
		break;
	    case 2: // Search by ID
		IDs = idSearch(memNames, IDs, scanner);
		break;
	    case 3: // Search by age
		IDs = ageSearch(memNames, IDs, memAge, scanner);
		break;
	    case 4: // Search by gender
		IDs = genderSearch(memNames, IDs, memGender, scanner);
		break;
	    case 5: // Search by borrowed books
		IDs = booksSearch(memNames, IDs, memBooks, scanner);
		break;
	    }
	    System.out.println();

	    if (!filter(scanner)) {
		id = selectMember(IDs, memNames, scanner);
		break;
	    }
	    System.out.println();
	}
	return id;
    }

    private static int[] booksSearch(String[] memNames, int[] memID, String[] memBooks, Scanner scanner) {
	scanner.nextLine();
	int[] IDs = new int[memID.length];
	createMemID(IDs);
	int found = 0;
	while (true) {
	    System.out.println("Please enter name of the book.");
	    String bookName = scanner.nextLine();
	    for (int i = 0; i < memID.length; i++) {
		if (containString(memBooks[i], bookName)) {
		    IDs[i] = memID[i];
		    System.out.printf("ID: %d ---> Name: %s%n ---> Borrowed bsooks: %s%n", memID[i], memNames[i],
			    memBooks[i]);
		    found++;
		}
		if (i + 1 == memID.length || memID[i + 1] == 0)
		    break;
		else if (memID[i + 1] == -1)
		    continue;
	    }
	    if (found == 0)
		System.out.println("No one was found!");
	    else
		return IDs;
	}
    }

    private static int[] genderSearch(String[] memNames, int[] memID, String[] memGender, Scanner scanner) {
	scanner.nextLine();
	int[] IDs = new int[memID.length];
	createMemID(IDs);
	int found = 0;
	while (true) {
	    System.out.println("Please enter the member's gender (F/M).");
	    String gender;
	    do {
		gender = scanner.nextLine();
	    } while (gender.length() == 0);
	    for (int i = 0; i < memID.length; i++) {
		if (memID[i] > 0 && memGender[i] != null && containString(memGender[i], gender)) {
		    IDs[i] = memID[i];
		    System.out.printf("ID: %d ---> Name: %s%n ---> Gender: %s%n", memID[i], memNames[i], memGender[i]);
		    found++;
		} else
		    IDs[i] = -1; // ???????????????????????????????!!!!!!!!!!!!!!!!!!
		if (i + 1 == memID.length || memID[i + 1] == 0)
		    break;
		else if (memID[i + 1] == -1)
		    continue;
	    }
	    if (found == 0)
		System.out.println("No one was found!");
	    else
		return IDs;
	}
    }

    private static int[] ageSearch(String[] memNames, int[] memID, int[] memAge, Scanner scanner) {
	int[] IDs = new int[memID.length];
	createMemID(IDs);
	int found = 0;
	while (true) {
	    System.out.print("Please enter the member's age: ");
	    int age = 0;
	    while (true) {
		age = scanner.nextInt();
		if (age < 5) {
		    System.out.println("Please enter a valid age.(above 5 years old.)");
		    System.out.print("Age: ");
		} else
		    break;
	    }
	    for (int i = 0; i < memID.length; i++) {
		if (memID[i] > 0 && memAge[i] == age) {
		    IDs[i] = memID[i];
		    System.out.printf("ID: %d ---> Name: %s%n ---> Age: %d%n", memID[i], memNames[i], age);
		    found++;
		    if (i + 1 == memID.length || memID[i + 1] == 0)
			break;
		    else if (memID[i + 1] == -1)
			continue;
		} else
		    IDs[i] = -1;
	    }
	    if (found == 0)
		System.out.println("No one was found!");
	    else
		return IDs;
	}
    }

    private static int[] idSearch(String[] memNames, int[] memID, Scanner scanner) {
	int[] IDs = new int[memID.length];
	createMemID(IDs);
	while (true) {
	    System.out.print("Please enter the member's ID: ");
	    int id = scanner.nextInt();
	    if (id > 0 && id <= memID.length && memID[id - 1] > 0) {
		IDs[id - 1] = memID[id - 1];
		System.out.printf("ID: %d ------> Name: %s%n", memID[id - 1], memNames[id - 1]);
		break;
	    } else
		System.out.println("No one was found!");
	    break;
	}
	return IDs;
    }

    private static int selectMember(int[] IDs, String[] memNames, Scanner scanner) {
	int id = -1;
	for (int i = 0; i < IDs.length; i++) {
	    if (IDs[i] > 0)
		System.out.printf("ID: %d ---> Name: %s%n", IDs[i], memNames[i]);
	}
	while (true) {
	    System.out.print("Please enter the ID of your desired member: ");
	    id = scanner.nextInt();
	    if (id > 0 && IDs[id - 1] > 0)
		break;
	}
	return id - 1;
    }

    private static boolean filter(Scanner scanner) {
	scanner.nextLine();
	System.out.println("Would you like to add a filter to search more? (Yes/No)");
	while (true) {
	    String input = scanner.nextLine();
	    if (input.compareToIgnoreCase("Yes") == input.length() - 3) {
		return true;
	    } else if (input.compareToIgnoreCase("No") == input.length() - 2)
		return false;
	}
    }

    private static int advancePrompt(Scanner scanner) {
	int method = 0;
	String method1 = "1) Search by name";
	String method2 = "2) Search by ID";
	String method3 = "3) Search by age";
	String method4 = "4) Search by gender";
	String method5 = "5) Search by borrowed books";
	while (method < 1 || method > 6) {
	    System.out.println("Which method do you prefer to search by?");
	    System.out.printf("%s%n%s%n%s%n%s%n%s%n", method1, method2, method3, method4, method5);
	    System.out.print("Please enter a number between 1 & 5 to select your search method: ");
	    method = scanner.nextInt();
	    System.out.println();
	}
	return method;
    }

    private static void editMember(String[] memNames, String[] memGender, int[] memAge, int[] memID, String[] memBooks,
	    Scanner scanner) {
	int id = searchPrompt(memNames, memGender, memAge, memID, memBooks, scanner);
	showMemberInfo(id, memNames, memGender, memAge, memBooks);
	scanner.nextLine();
	String prompt = "Would you like to edit the member's information? (Yes/No)";
	if (doYouWant(scanner, prompt))
	    editInfo(id, memNames, memGender, memAge, memBooks, scanner);
    }

    private static void editInfo(int id, String[] memNames, String[] memGender, int[] memAge, String[] memBooks,
	    Scanner scanner) {
	System.out.println();
	getName(memNames, id, scanner);
	getGender(memGender, id, scanner);
	getAge(memAge, id, scanner);
	noteBorrowedBooks(memBooks, id, scanner);
	System.out.println();
    }

    private static void noteBorrowedBooks(String[] memBooks, int id, Scanner scanner) {
	scanner.nextLine();
	// System.out.println("Please enter the borrowed books' names. (such as: Book1,
	// Book2, etc.)");
	System.out.println("Borrowed books' names (such as: Book1, Book2, etc.): ");
	memBooks[id] = scanner.nextLine();
    }

    private static void showMember(String[] memNames, String[] memGender, int[] memAge, int[] memID, String[] memBooks,
	    Scanner scanner) {
	int id = searchPrompt(memNames, memGender, memAge, memID, memBooks, scanner);
	showMemberInfo(id, memNames, memGender, memAge, memBooks);
    }

    private static void showMemberInfo(int id, String[] memNames, String[] memGender, int[] memAge, String[] memBooks) {
	System.out.println();
	System.out.println("Name: " + memNames[id]);
	System.out.println("ID: " + (id + 1));
	System.out.println("Gender: " + memGender[id]);
	System.out.println("Age: " + memAge[id]);
	System.out.println("Borrowed Books: " + memBooks[id]);
    }

    private static void manageLentBooks(String[] memNames, String[] memGender, int[] memAge, int[] memID,
	    String[] memBooks, Scanner scanner) {
	int action = manageBooksPrompt(scanner);
	switch (action) {
	case 1: // Add lent books
	    addBook(memNames, memGender, memAge, memID, memBooks, scanner);
	    break;
	case 2: // Show lent books
	    showBook(memNames, memGender, memAge, memID, memBooks, scanner);
	    break;
	case 3: // Edit lent books
	    editBook(memNames, memGender, memAge, memID, memBooks, scanner);
	    break;
	case 4: // Delete lent books
	    deleteBook(memNames, memGender, memAge, memID, memBooks, scanner);
	    break;
	}
    }

    private static void deleteBook(String[] memNames, String[] memGender, int[] memAge, int[] memID, String[] memBooks,
	    Scanner scanner) {
	int method = 0;
	while (method != 1 && method != 2) {
	    System.out.println("1) Select a member to delete their book.");
	    System.out.println("2) Write the name of a lent book to delete that.");
	    method = scanner.nextInt();
	}
	switch (method) {
	case 1: // Select a member to delete their book.
	    System.out.println("Select a member to delete their book..");
	    int id1 = searchPrompt(memNames, memGender, memAge, memID, memBooks, scanner);
	    renameBook(id1, memBooks, "Delete", scanner);
	    break;
	case 2: // Write the name of a lent book to delete that.
	    scanner.nextLine();
	    int id2 = searchBookName(memBooks, memID, memNames, scanner);
	    renameBook(id2, memBooks, "Delete", scanner);
	    break;
	}
    }

    private static void editBook(String[] memNames, String[] memGender, int[] memAge, int[] memID, String[] memBooks,
	    Scanner scanner) {
	int method = 0;
	while (method != 1 && method != 2) {
	    System.out.println("1) Select a member to edit their book.");
	    System.out.println("2) Write the name of a lent book to edit that.");
	    method = scanner.nextInt();
	}
	switch (method) {
	case 1: // Select a member to edit their book.
	    System.out.println("Select a member to edit their book.");
	    int id1 = searchPrompt(memNames, memGender, memAge, memID, memBooks, scanner);
	    renameBook(id1, memBooks, "Edit", scanner);
	    break;
	case 2: // Write the name of a lent book to edit that.
	    scanner.nextLine();
	    int id2 = searchBookName(memBooks, memID, memNames, scanner);
	    renameBook(id2, memBooks, "Edit", scanner);
	    break;
	}
    }

    private static String askName(int id, String[] memBooks, Scanner scanner) {
	while (true) {
	    System.out.println("Please enter name of the book you intend to edit.");
	    String inputName = scanner.nextLine();
	    if (containString(memBooks[id], inputName))
		return inputName;
	    else
		System.out.println("No correspond name was found!");
	}
    }

    private static void renameBook(int id, String[] memBooks, String toDo, Scanner scanner) {
	scanner.nextLine();
	String searchedName = askName(id, memBooks, scanner);
	String newName = "";
	if (containString("Edit", toDo)) {
	    System.out.println("Please write the new book's name to be replaced.");
	    newName = scanner.nextLine();
	}
	int leftIndex = findLeftIndex(memBooks[id], searchedName);
	int rightIndex = findRightIndex(leftIndex, memBooks[id], searchedName);
	replaceWord(leftIndex, rightIndex, newName, memBooks, id);
    }

    private static void replaceWord(int leftIndex, int rightIndex, String newName, String[] memBooks, int id) {
	String left = "";
	String right = "";
	for (int i = 0; i < leftIndex; i++) {
	    left += memBooks[id].charAt(i);
	}
	String booksnames = left;
	booksnames = booksnames.concat(newName);
	for (int i = rightIndex; i < memBooks[id].length(); i++) {
	    right += memBooks[id].charAt(i);
	}
	memBooks[id] = booksnames.concat(right);
    }

    private static int findLeftIndex(String booksName, String searchedName) {
	int leftIndex = booksName.indexOf(searchedName);
	if (leftIndex == -1)
	    for (int i = 0; i < booksName.length(); i++) {
		if (booksName.charAt(i) == searchedName.charAt(0)) {
		    leftIndex = i;
		}
	    }
	leftIndex = wordLeftIndex(leftIndex, booksName);
	return leftIndex;
    }

    private static int findRightIndex(int leftIndex, String booksName, String searchedName) {
	int rightIndex = booksName.lastIndexOf(searchedName);
	if (rightIndex == -1)
	    for (int i = leftIndex + 1; i < booksName.length(); i++)
		if (booksName.charAt(i) == searchedName.charAt(searchedName.length() - 1))
		    rightIndex = i;
	rightIndex = wordRightIndex(rightIndex, booksName);
	return rightIndex;
    }

    private static int wordRightIndex(int rightIndex, String booksName) {
	for (int j = rightIndex + 1; j < booksName.length(); j++)
	    if (booksName.charAt(j) == ',')
		return j;
	return booksName.length();
    }

    private static int wordLeftIndex(int leftIndex, String booksName) {
	for (int j = leftIndex; j > 0; j--)
	    if ((booksName.charAt(j - 1) == ' ' && booksName.charAt(j - 2) == ',') || booksName.charAt(j - 1) == ',')
		return j;

	return leftIndex;
    }

    private static void showBook(String[] memNames, String[] memGender, int[] memAge, int[] memID, String[] memBooks,
	    Scanner scanner) {
	int method = 0;
	while (method != 1 && method != 2) {
	    System.out.println("1) Select a member to show their books.");
	    System.out.println("2) Write the name of a lent book to show whom borrowed that.");
	    method = scanner.nextInt();
	}
	switch (method) {
	case 1: // Select a member to show their books.
	    System.out.println("Select a member to show their books.");
	    int id1 = searchPrompt(memNames, memGender, memAge, memID, memBooks, scanner);
	    System.out.println("Borrowed Books: " + memBooks[id1]);
	    break;
	case 2: // Write the name of a lent book to show whom borrowed that.
	    scanner.nextLine();
	    int id2 = searchBookName(memBooks, memID, memNames, scanner);
	    System.out.println("Borrowed Books: " + memBooks[id2]);
	    break;
	}
    }

    private static int searchBookName(String[] memBooks, int[] memID, String[] memNames, Scanner scanner) {
	int[] IDs = new int[memBooks.length];
	createMemID(IDs);
	int found = 0;
	while (true) {
	    System.out.println("Write the borrowed book's name.");
	    String name = scanner.nextLine();
	    for (int i = 0; i < memID.length; i++) {
		if (containString(memBooks[i], name)) {
		    IDs[i] = memID[i];
		    System.out.printf("ID: %d  Name: %s  Books: %s%n", memID[i], memNames[i], memBooks[i]);
		    found++;
		}
		if (i + 1 == memID.length || memID[i + 1] == 0)
		    break;
		else if (memID[i + 1] == -1)
		    continue;
	    }
	    if (found == 0)
		System.out.println("No one was found!");
	    else
		break;
	}
	int id = -1;
	while (true) {
	    System.out.print("Please enter the ID of your desired member: ");
	    id = scanner.nextInt();
	    if (id > 0 && IDs[id - 1] > 0)
		break;
	}
	return id - 1;
    }

    private static void addBook(String[] memNames, String[] memGender, int[] memAge, int[] memID, String[] memBooks,
	    Scanner scanner) {
	System.out.println("Select a member.");
	int id = searchPrompt(memNames, memGender, memAge, memID, memBooks, scanner);
	System.out.println("Please write the lent books' names: (eg: Book1, Book2)");
	scanner.nextLine();
	String name = scanner.nextLine();
	if (containString(memBooks[id], "Has Not borrowed")) {
	    memBooks[id] = name;
	    System.out.println("Saved");
	} else {
	    name = " ,".concat(name);
	    memBooks[id] = memBooks[id].concat(name);
	    System.out.println("Saved");
	}
	System.out.println();
    }

    private static int manageBooksPrompt(Scanner scanner) {
	int action = 0;
	String action1 = "1) Add lent books";
	String action2 = "2) Show lent books";
	String action3 = "3) Edit lent books";
	String action4 = "4) Delete lent books";
	while (action < 1 || action > 5) {
	    System.out.println("What is your desired action?");
	    System.out.printf("%s%n%s%n%s%n%s%n", action1, action2, action3, action4);
	    System.out.print("Please enter a number between 1 & 4 to select your search method: ");
	    action = scanner.nextInt();
	    System.out.println();
	}
	return action;
    }

}