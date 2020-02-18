package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmpOrderApproverAsApp {
	int approverOrder;
	String employeeName;
	boolean confirmPerson;
}
