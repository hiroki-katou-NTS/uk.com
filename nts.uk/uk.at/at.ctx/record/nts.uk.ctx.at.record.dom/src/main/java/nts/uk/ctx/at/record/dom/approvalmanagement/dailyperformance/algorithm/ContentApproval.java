package nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class ContentApproval {
	private GeneralDate date;
	private boolean status;
	private String employeeId;
	private boolean flagRemmoveAll;
}
