package nts.uk.ctx.workflow.infra.entity.approverstatemanagement.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalForm;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmPerson;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;

@AllArgsConstructor
@Getter
public class WwfdtFullJoinState {
	private String rootStateID;
	private String historyID;
	private String employeeID;
	private GeneralDate recordDate;
	private Integer phaseOrder;
	private ApprovalForm approvalForm;
	private ApprovalBehaviorAtr appPhaseAtr;
	private Integer frameOrder;
	private ApprovalBehaviorAtr appFrameAtr;
	private ConfirmPerson confirmAtr;
	private String approverID;
	private String representerID;
	private GeneralDate approvalDate;
	private String approvalReason;
	private String approverChildID;
	private String companyID;
}
