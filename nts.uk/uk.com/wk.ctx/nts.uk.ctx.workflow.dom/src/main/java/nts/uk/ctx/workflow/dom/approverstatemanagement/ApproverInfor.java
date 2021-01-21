package nts.uk.ctx.workflow.dom.approverstatemanagement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDateTime;
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
	private GeneralDateTime approvalDate;
	/**理由*/
	private String approvalReason;
	
	private Integer approverInListOrder;
	
	public static ApproverInfor createFromFirst(ApproverInfor approverInfo){
			return ApproverInfor.builder()
					.approverID(approverInfo.getApproverID())
					.approvalAtr(approverInfo.getApprovalAtr())
					.agentID(approverInfo.getAgentID())
					.approvalDate(approverInfo.getApprovalDate())
					.approvalReason(approverInfo.getApprovalReason())
					.approverInListOrder(approverInfo.getApproverInListOrder())
					.build();
	}
	public static ApproverInfor convert(String approverID,
			int approvalAtr, String agentID, GeneralDateTime approvalDate, String approvalReason, Integer approverInListOrder){
		return new  ApproverInfor (approverID,
				EnumAdaptor.valueOf(approvalAtr, ApprovalBehaviorAtr.class),
				agentID, approvalDate, approvalReason, approverInListOrder);
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
