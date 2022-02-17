package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import lombok.Value;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.param.ApproverInformation;

@Value
public class ApproverInformationCommand {

	/**
	 * フェーズの順序
	 */
	private int phaseOrder;
	
	/**
	 * 承認者ID
	 */
	private String approverId;
	
	public ApproverInformation toDomain() {
		return new ApproverInformation(phaseOrder, approverId);
	}
}
