package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeApproverAsApplicationOutput {
	/**
	 * 社員の情報
	 */
	EmployeeApproverOutput employeeInfor;
	/**
	 * apptype 
	 */
	Map<Integer, List<ApproverAsApplicationInforOutput>> mapAppTypeAsApprover;
}
