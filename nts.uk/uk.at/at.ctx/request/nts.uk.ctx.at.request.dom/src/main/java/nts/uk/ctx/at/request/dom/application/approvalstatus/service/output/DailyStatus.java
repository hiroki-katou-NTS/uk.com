package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Setter
@Getter
public class DailyStatus {
	String empId;
	String empName;
	GeneralDate date;
	int stateSymbol;
}
