

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class student extends member{
	int id;
	List<book> stuBookList= new ArrayList<book>();
	List<Date> stuBookDeadlineList= new ArrayList<Date>();
	List<Date> stuBookBorrowDateList= new ArrayList<Date>();

	public student(int id) {
		super(id);
	}

	@Override
	public List<book> getstuList() {
		return stuBookList;
	}
	@Override
	public List<Date> getstuDeadlineList() {
		return stuBookDeadlineList;
	}
	@Override
	public List<Date> getStuBookBorrowDateList() {
		return stuBookBorrowDateList;
	}
}
