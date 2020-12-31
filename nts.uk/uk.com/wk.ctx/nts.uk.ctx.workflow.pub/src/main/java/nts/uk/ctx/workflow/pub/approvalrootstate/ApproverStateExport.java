package nts.uk.ctx.workflow.pub.approvalrootstate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter @Setter
@NoArgsConstructor
public class ApproverStateExport {
	
	/** 申請ID */
	private String rootStateId;
	
	private String approverId;
	
	private GeneralDate appDate;

	public ApproverStateExport(String rootStateId, String approverId, GeneralDate appDate) {
		this.rootStateId = rootStateId;
		this.approverId = approverId;
		this.appDate = appDate;
	}
}
