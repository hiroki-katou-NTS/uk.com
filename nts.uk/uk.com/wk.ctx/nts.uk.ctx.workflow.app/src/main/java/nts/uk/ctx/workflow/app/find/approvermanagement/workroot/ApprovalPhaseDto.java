package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.List;

import lombok.Value;
@Value
public class ApprovalPhaseDto {

	private List<ApproverDto> approver;
	/**分岐ID*/
	private String branchId;
	/**承認フェーズID*/
	private String approvalPhaseId;
	/**承認形態*/
	private int approvalForm;
	/**閲覧フェーズ*/
	private int browsingPhase;
	/**順序*/
	private int orderNumber;
}
