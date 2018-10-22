package nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FullJoinWwfmtApprovalPhase {
	public String companyId;
	public String branchId;
	public String approvalPhaseId;
	public int approvalForm;
	public int browsingPhase;
	public int phaseDispOrder;
	public String approverId;
	public String jobId;
	public String employeeId;
	public int approverDispOrder;
	public int approvalAtr;
	public int confirmPerson;
	
}
