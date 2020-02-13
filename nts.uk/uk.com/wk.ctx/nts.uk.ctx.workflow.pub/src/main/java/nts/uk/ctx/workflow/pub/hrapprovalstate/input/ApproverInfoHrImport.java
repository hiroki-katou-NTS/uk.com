package nts.uk.ctx.workflow.pub.hrapprovalstate.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
@Getter
@AllArgsConstructor
public class ApproverInfoHrImport {
	/**承認者*/
	private String approverID;
	/**承認区分*/
	private int approvalAtr;
	/**代行者*/
	private String agentID;
	/**承認日*/
	private GeneralDate approvalDate;
	/**コメント*/
	private String approvalReason;
}
