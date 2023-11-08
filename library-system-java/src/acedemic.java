
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class acedemic extends member {
	int id;
	List<book> aceBookList=new ArrayList<book>();
	List<Date> aceBookDeadlineList=new ArrayList<Date>();
	List<Date> aceBookBorrowDateList=new ArrayList<Date>();
	public acedemic(int id) {
		super(id);
	}
	@Override
	public List<book> getaceList(){
		return aceBookList;
	}
	@Override
	public List<Date> getaceDeadlineList(){
		return aceBookDeadlineList;
	}
	@Override
	public List<Date> getAceBookBorrowDateList() {
		return aceBookBorrowDateList;
	}
}
