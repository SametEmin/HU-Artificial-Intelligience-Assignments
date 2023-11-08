

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class member extends instances{
	Date borrowDate ;
	Date borrowDeadline;
	String className;
	List<book> readenBooks=new ArrayList<book>();
	public member(int id) {
		super(id);
	}
	public List<book> getstuList() {
		return null;
	}
	public List<book> getaceList(){
		return null;
	}
	
	
	public List<Date> getStuBookBorrowDateList(){
		return null;
	}
	public List<Date> getAceBookBorrowDateList(){
		return null;
	}
	public List<Date> getaceDeadlineList() {
		return null;
	}
	public List<Date> getstuDeadlineList() {
		return null;
	}
	
}
