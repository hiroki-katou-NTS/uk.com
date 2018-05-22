package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ApproverAsApplicationInforOutput {
	/**
	 * フェーズ
	 */
	int phaseNumber = 0;
	/**
	 * 承認形態
	 */
	String approvalForm = "";
	
	List<EmployeeOrderApproverAsAppOutput> lstEmpInfo = new ArrayList<>();
}
