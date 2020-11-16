package nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.history.DateHistoryItem;

@AllArgsConstructor
@Getter
public class BusinessTypeOfEmployeeHis {

	private BusinessTypeOfEmployee employee;
	
	private DateHistoryItem history;
}
