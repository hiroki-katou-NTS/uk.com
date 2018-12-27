package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApproverAsAppInfor {
	/**
	 * フェーズ
	 */
	int phaseNumber = 0;
	/**
	 * 承認形態
	 */
	String approvalForm = "";
	
	List<EmpOrderApproverAsApp> lstEmpInfo = new ArrayList<>();
}
