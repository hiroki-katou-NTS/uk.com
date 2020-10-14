package nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FullJoinWwfmtApprovalPhase {
	public String approvalId;
	public int phaseOrder;
	// public String branchId;
	public int approvalForm;
	public int browsingPhase;
	public int phaseAtr;
	public String jobGCD;
	public String employeeId;
	public int approverOrder;
	public int approvalAtr;
	public int confirmPerson;
	public String specWkpID;
}
