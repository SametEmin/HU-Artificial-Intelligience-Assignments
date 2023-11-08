

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class system {
	fileReading reader = new fileReading();
	List<book> bookList = new ArrayList<>();
	List<String[]> commandList;
	List<member> memberList = new ArrayList<>();
	List<Integer> stuIdList = new ArrayList<>();
	List<Integer> aceIdList = new ArrayList<>();
	List<Integer> priIdList = new ArrayList<>();
	List<Integer> hanIdList = new ArrayList<>();
	List<Integer> borBookIdList = new ArrayList<>();
	List<Integer> retBookIdList = new ArrayList<>();
	List<Integer> readBookIdList = new ArrayList<>();
	List<String> borBookStrList = new ArrayList<>();
	List<String> retBookStrList = new ArrayList<>();
	List<String> readBookStrList = new ArrayList<>();
	int memberId = 1;
	int bookId = 1;
	String output = "";

	public void commandHandling(String path) {
		try {
			reader.readFile(path);
			commandList = reader.comArr;
		} catch (IOException e) {
			// There is an error in reading file
		}

		for (int i = 0; i < commandList.size(); i++) {

			String command = commandList.get(i)[0];
			switch (command) {
			case "addBook":

				bookList.add(new book(commandList.get(i)[1], bookId));
				if (commandList.get(i)[1].equals("H")) {
					hanIdList.add(bookId);
				} else if (commandList.get(i)[1].equals("P")) {
					priIdList.add(bookId);
				}
				book book = elementFinder(bookList, bookId);
				output += String.format("Created new book: %s [id: %d]\n", book.type, bookId++);
				break;

			// We add member into memberlist
			case "addMember":
				int index;
				if (commandList.get(i)[1].equals("S")) {
					memberList.add(new student(memberId));
					memberList.get(memberId - 1).className = "Student";
					stuIdList.add(memberId);
				} else if (commandList.get(i)[1].equals("A")) {
					memberList.add(new acedemic(memberId));
					memberList.get(memberId - 1).className = "Academic";
					aceIdList.add(memberId);
				}
				index = memberList.indexOf(elementFinder(memberList, memberId));
				memberId++;
				output += String.format("Created new member: %s [id: %d]\n", memberList.get(index).className,
						memberList.get(index).id);
				break;
			/*
			 * borrowing book this function check if this process valid and setting
			 * borrowing date which is own by member
			 * 
			 */

			case "borrowBook":
				member member1 = elementFinder(memberList, Integer.parseInt(commandList.get(i)[2]));
				book book1 = elementFinder(bookList, Integer.parseInt(commandList.get(i)[1]));
				try {
					if (member1.className.equals("Student")) {
						if (book1.type.equals("Handwritten")) {

						}
					}
					if (!book1.isItAccessible) {

					}

					if (member1.className.equals("Student")) {
						if (member1.getstuList().size() < 2) {
							member1.getstuList().add(book1);
						} else {
							output += "You have exceeded the borrowing limit!\n";
							throw new Exception();
						}

					} else {
						if (member1.getaceList().size() < 4) {
							member1.getaceList().add(book1);
						} else {
							output += "You have exceeded the borrowing limit!\\n";
							throw new Exception();
						}
					}

					borrowdateSetter(member1, commandList.get(i)[3]);

					String text = String.format("The book [%s] was borrowed by member [%s] at %s\n",
							commandList.get(i)[1], commandList.get(i)[2], commandList.get(i)[3]);
					output += text;
					borBookIdList.add(member1.id);
					borBookStrList.add(text);
					book1.isItAccessible = false;

				} catch (ArrayIndexOutOfBoundsException e) {

					output += "You can not borrow this book!\n";
				} catch (Exception e) {
				}

				break;

			// returning book
			// determine the book and the member
			// and remove book from member
			// and change state of access
			case "returnBook":
				String text2 = "";
				member member2 = elementFinder(memberList, Integer.parseInt(commandList.get(i)[2]));
				book book2 = elementFinder(bookList, Integer.parseInt(commandList.get(i)[1]));

				try {
					Float fee = feeCalc(commandList.get(i)[3],
							member2.className.equals("Student")
									? member2.getStuBookBorrowDateList().get(finder(borBookIdList, book2.id))
									: member2.getAceBookBorrowDateList().get(finder(borBookIdList, book2.id)));

					text2 = String.format("The book [%s] was returned by member [%s] at %s Fee: %.0f\n",
							commandList.get(i)[1], commandList.get(i)[2], commandList.get(i)[3], fee);
					borBookStrList.remove(finder(borBookIdList, Integer.parseInt(commandList.get(i)[1])));
					borBookIdList.remove(finder(borBookIdList, Integer.parseInt(commandList.get(i)[1])));

					if (member2.className.equals("Student")) {

						member2.getstuList().remove(finder(member2.getstuList(), book2));
					} else {
						member2.getaceList().remove(finder(member2.getstuList(), book2));
					}

				} catch (Exception e) {

				}
				try {
					readBookStrList.remove(finder(readBookIdList, Integer.parseInt(commandList.get(i)[1])));
					readBookIdList.remove(finder(readBookIdList, Integer.parseInt(commandList.get(i)[1])));
					text2 = String.format("The book [%s] was returned by member [%s] at %s Fee: %.0f\n",
							commandList.get(i)[1], commandList.get(i)[2], commandList.get(i)[3], 0f);

					if (member2.className.equals("Student")) {
						member2.readenBooks.remove(finder(member2.readenBooks, book2));

					} else {
						member2.readenBooks.remove(finder(member2.readenBooks, book2));
					}
					book2.isItAccessible = true;

				} catch (Exception e) {
				}
				//
				//
				// in here fee is calculated by help of the feeCalc function

				output += text2;
				retBookIdList.add(member2.id);
				retBookStrList.add(text2);
				break;
			case "extendBook":

				SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				Date date = null;

				member member4 = elementFinder(memberList, Integer.parseInt(commandList.get(i)[2]));
				book book4 = elementFinder(bookList, Integer.parseInt(commandList.get(i)[1]));
				book4.id = Integer.parseInt(commandList.get(i)[1]);
				if (!book4.isItExtendable) {
					output += "You cannot extend the deadline!\n";
					continue;
				}

				if (member4.className.equals("Student")) {
					try {
						calendar.setTime(formatter.parse(commandList.get(i)[3]));
						calendar.add(Calendar.DAY_OF_MONTH, 7);
						date = calendar.getTime();
					} catch (ParseException e1) {

					}

					output += String.format("The deadline of book [%d] was extended by member [%d] at %s\n", book4.id,
							member4.id, dateToString(member4.getstuDeadlineList().get(bookList.indexOf(book4))));
					try {
						member4.getstuDeadlineList().set(bookList.indexOf(book4),
								formatter.parse(commandList.get(i)[3]));
					} catch (ParseException e) {

					}
					output += String.format("New deadline of book [%d] is %s\n", book4.id, dateToString(date));
				} else {
					try {
						member4.getaceDeadlineList().set(bookList.indexOf(book4),
								formatter.parse(commandList.get(i)[3]));
					} catch (ParseException e) {

					}
					try {
						calendar.setTime(formatter.parse(commandList.get(i)[3]));
						calendar.add(Calendar.DAY_OF_MONTH, 14);
						date = calendar.getTime();

					} catch (ParseException e1) {

					}
					output += String.format("The deadline of book [%d] was extended by member [%d] at %s\n", member4.id,
							book4.id, dateToString(member4.getaceDeadlineList().get(bookList.indexOf(book4))));
					output += String.format("New deadline of book [%d] is %s\n", book4.id, dateToString(date));

				}
				book4.isItExtendable = false;

				break;
			case "readInLibrary":
				member member3 = elementFinder(memberList, Integer.parseInt(commandList.get(i)[2]));
				book book3 = elementFinder(bookList, Integer.parseInt(commandList.get(i)[1]));
				try {
					// We apply different functions according to being student or academic
					if (member3.className.equals("Student")) {
						if (book3.type.equals("Handwritten")) {
							output += "Students can not read handwritten books!\n";
							throw new Exception();
						}
					}
					if (!book3.isItAccessible) {
						output += "You can not read this book!\n";
						throw new Exception();
					}

					member3.readenBooks.add(book3);
					book3.isItAccessible = false;
					String text3 = String.format("The book [%s] was read in library by member [%s] at %s\n",
							commandList.get(i)[1], commandList.get(i)[2], commandList.get(i)[3]);
					output += text3;
					readBookIdList.add(member3.id);
					readBookStrList.add(text3);
				} catch (Exception e) {

				}

				break;

			case "getTheHistory":
				// We iterate all of lists and add up lines to output string.
				output += "History of library:\n\n";
				output += String.format("Number of students: %d\n", stuIdList.size());
				for (int id : stuIdList) {
					output += String.format("Student [id: %d]\n", id);
				}
				output += "\n";
				output += String.format("Number of academics: %d\n", aceIdList.size());
				for (int id : aceIdList) {
					output += String.format("Academic [id: %d]\n", id);
				}
				output += "\n";
				output += String.format("Number of printed books: %d\n", priIdList.size());
				for (int id : priIdList) {
					output += String.format("Printed [id: %d]\n", id);
				}
				output += "\n";
				output += String.format("Number of handwritten books: %d\n", hanIdList.size());
				for (int id : hanIdList) {
					output += String.format("Handwritten [id: %d]\n", id);
				}
				output += "\n";

				output += String.format("Number of borrowed books: %d\n", borBookStrList.size());
				for (String line : borBookStrList) {
					output += line;
				}
				output += "\n";
				output += String.format("Number of books read in library: %d\n", readBookStrList.size());
				for (String line : readBookStrList) {
					output += line;
				}

				break;
			default:
				// There is no command like that.
				break;
			}
		}
	}

//public element finder finds the insantce with information about id.
	public <E extends instances> E elementFinder(List<E> li, int id) {

		for (int i = 0; i < li.size(); i++) {
			if (li.get(i).id == id) {
				return li.get(i);
			}
		}
		return null;
	}

	// Its difference from above function is that:
	// This function finds element index with elements itself.
	public <E> int finder(List<E> list, E element) {
		for (int i = 0; i < list.size(); i++) {
			if (element == list.get(i)) {
				return list.indexOf(element);
			}
		}
		return 0;
	}

	// finds the correct position of the borrowing date and change it.
	public void borrowdateSetter(member member, String currDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
		Date currentDate;
		try {
			currentDate = formatter.parse(currDate);
			if (member.className.equals("Student")) {
				member.getStuBookBorrowDateList().add(currentDate);
			} else {
				member.getAceBookBorrowDateList().add(currentDate);
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			calendar.add(Calendar.DAY_OF_MONTH, 7);
			currentDate = calendar.getTime();
			if (member.className.equals("Student")) {
				member.getstuDeadlineList().add(currentDate);
			} else {
				member.getaceDeadlineList().add(currentDate);
			}
		} catch (ParseException e) {
			// We have a problem in parsing date
		}

	}

	// current date - deadline date= fee to pay.
	float feeCalc(String date, Date oldDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
		Date datee;
		try {
			datee = formatter.parse(date);
			if ((datee.getTime() - oldDate.getTime()) / 1000 / 60 / 60 / 24 > 7) {
				return (datee.getTime() - oldDate.getTime()) / 1000 / 60 / 60 / 24 - 7;
			} else {
				return 0;
			}

		} catch (ParseException e) {
			// We have a problem in parsing date
		}
		return 0;

	}

	public String dateToString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		return formatter.format(date);
	}
}
