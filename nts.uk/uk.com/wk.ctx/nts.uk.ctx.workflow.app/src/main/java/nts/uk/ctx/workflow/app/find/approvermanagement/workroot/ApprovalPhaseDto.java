package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
@Value
public class ApprovalPhaseDto {

	private List<Approver> aa;
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
