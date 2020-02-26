package nts.uk.ctx.workflow.dom.approverstatemanagement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
/**
 * 承認枠 : 承認者
 * @author hoatt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ApproverInfor extends DomainObject {
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
	
	public static ApproverInfor createFromFirst(ApproverInfor approverInfo){
			return ApproverInfor.builder()
					.approverID(approverInfo.getApproverID())
					.approvalAtr(approverInfo.getApprovalAtr())
					.agentID(approverInfo.getAgentID())
					.approvalDate(approverInfo.getApprovalDate())
					.approvalReason(approverInfo.getApprovalReason())
					.build();
	}
	public static ApproverInfor convert(String approverID,
			int approvalAtr, String agentID, GeneralDate approvalDate, String approvalReason){
		return new  ApproverInfor (approverID,
				EnumAdaptor.valueOf(approvalAtr, ApprovalBehaviorAtr.class),
				agentID, approvalDate, approvalReason);
	}
	
	public boolean isNotApproved() {
		return approvalAtr!=ApprovalBehaviorAtr.APPROVED;
	}
	
	public boolean isApproved() {
		return approvalAtr==ApprovalBehaviorAtr.APPROVED;
	}
	
	public boolean isNotUnApproved() {
		return approvalAtr!=ApprovalBehaviorAtr.UNAPPROVED;
	}
}
