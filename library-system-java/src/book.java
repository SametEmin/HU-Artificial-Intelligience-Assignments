
import java.util.Date;
import java.util.List;

public class book extends instances{
	public String type;
	public int id;
	public boolean isItAccessible=true;
	public boolean isItExtendable=true;
	public book(String type,int id) {
		super(id);
		if (type.equals("P")) {
			this.type="Printed";
		}
		if (type.equals("H")) {
			this.type="Handwritten";
		}
		
	}
}
class instances{
	int id;
	public instances(int id) {
		this.id=id;
	}
	
}

