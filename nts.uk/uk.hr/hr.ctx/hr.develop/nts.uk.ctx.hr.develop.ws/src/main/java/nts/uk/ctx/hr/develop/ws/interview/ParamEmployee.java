package nts.uk.ctx.hr.develop.ws.interview;

import java.util.List;

import lombok.Getter;
import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
@Getter
public class ParamEmployee {
	List<String> listEmployeeID ;
	GeneralDate date;
	public ParamEmployee(List<String> listEmployeeID, GeneralDate date) {
		super();
		this.listEmployeeID = listEmployeeID;
		this.date = date;
	}
	
	
	
	
}
