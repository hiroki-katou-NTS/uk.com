package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeApproverOutput {
	/** employeeId */
	private String empId;
	/** employee code */
	private String empCD;
	/** EmployeeName */
	private String eName;
	
}
