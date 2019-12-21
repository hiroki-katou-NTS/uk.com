package nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FullJoinWwfmtApprovalPhase {
	public String companyId;
	public String approvalId;
	public int phaseOrder;
	public int approvalForm;
	public int browsingPhase;
	public String jobId;
	public String employeeId;
	public int approverOrder;
	public int approvalAtr;
	public int confirmPerson;
	
}
