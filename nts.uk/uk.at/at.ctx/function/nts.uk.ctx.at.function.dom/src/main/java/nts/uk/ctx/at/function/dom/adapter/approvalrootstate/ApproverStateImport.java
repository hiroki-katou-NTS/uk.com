package nts.uk.ctx.at.function.dom.adapter.approvalrootstate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Getter
@NoArgsConstructor
public class ApproverStateImport {

	/** 申請ID */
	private String rootStateId;
	
	private String approverId;
	
	private GeneralDate appDate;

	public ApproverStateImport(String rootStateId, String approverId, GeneralDate appDate) {
		this.rootStateId = rootStateId;
		this.approverId = approverId;
		this.appDate = appDate;
	}
}
