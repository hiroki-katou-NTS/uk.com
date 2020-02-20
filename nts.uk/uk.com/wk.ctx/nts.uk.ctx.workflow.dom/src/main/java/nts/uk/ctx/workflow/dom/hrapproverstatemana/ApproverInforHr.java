package nts.uk.ctx.workflow.dom.hrapproverstatemana;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;

/**
 * 承認者情報
 * @author hoatt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ApproverInforHr {
	/**承認者*/
	private String approverID;
	/**承認区分*/
	private ApprovalBehaviorAtr approvalAtr;
	/**代行者*/
	private String agentID;
	/**承認日*/
	private GeneralDate approvalDate;
	/**理由*/
	private String approvalReason;
	
	public static ApproverInforHr convert(String approverID, int approvalAtr, String agentID,
			GeneralDate approvalDate, String approvalReason){
		return new  ApproverInforHr (approverID,
				EnumAdaptor.valueOf(approvalAtr, ApprovalBehaviorAtr.class),
				agentID, approvalDate, approvalReason);
	}
}
