package nts.uk.ctx.hr.shared.dom.notice.report.registration.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
@Getter
@AllArgsConstructor
public class ApproverInfoHumamImport {
	/**承認者*/
	private String approverID;
	
	/**承認区分*/
	private int approvalAtr;
	
	/**代行者*/
	private String agentID;
	/**承認日*/
	private GeneralDate approvalDate;
	/**理由*/
	private String approvalReason;
}
