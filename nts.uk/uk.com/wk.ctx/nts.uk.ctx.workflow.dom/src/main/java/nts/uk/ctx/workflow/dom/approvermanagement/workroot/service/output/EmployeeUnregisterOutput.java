package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.EmployeeApproveDto;
@Data
public class EmployeeUnregisterOutput {
	private EmployeeApproveDto empInfor;
	
	private List<Integer> appType;
	
	
}
