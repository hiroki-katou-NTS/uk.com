package nts.uk.ctx.workflow.infra.entity.approverstatemanagement;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmPerson;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalFrame;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="WWFDT_APPROVAL_FRAME")
@Builder
public class WwfdtApprovalFrameNew extends UkJpaEntity {
	@EmbeddedId
	public WwfdpApprovalFramePK wwfdpApprovalFramePK;
	
	@Column(name="APPROVAL_ATR")
	public Integer approvalAtr;
	
	@Column(name="CONFIRM_ATR")
	public Integer confirmAtr;
	
	@Column(name="APPROVER_ID")
	public String approverID;
	
	@Column(name="REPRESENTER_ID")
	public String representerID;
	
	@Column(name="APPROVAL_DATE")
	public GeneralDate approvalDate;
	
	@Column(name="APPROVAL_REASON")
	public String approvalReason;

	@Override
	protected Object getKey() {
		return wwfdpApprovalFramePK;
	}
	
	public static WwfdtApprovalFrame fromDomain(ApprovalFrame approvalFrame){
		return WwfdtApprovalFrame.builder()
				.wwfdpApprovalFramePK(
						new WwfdpApprovalFramePK(
								approvalFrame.getRootStateID(), 
								approvalFrame.getPhaseOrder(), 
								approvalFrame.getFrameOrder()))
				.approvalAtr(approvalFrame.getApprovalAtr().value)
				.confirmAtr(approvalFrame.getConfirmAtr().value)
				.approverID(approvalFrame.getApproverID())
				.representerID(approvalFrame.getRepresenterID())
				.approvalDate(approvalFrame.getApprovalDate())
				.approvalReason(approvalFrame.getApprovalReason())
				.build();
	}
	
	public ApprovalFrame toDomain(){
		return ApprovalFrame.builder()
				.rootStateID(this.wwfdpApprovalFramePK.rootStateID)
				.phaseOrder(this.wwfdpApprovalFramePK.phaseOrder)
				.frameOrder(this.wwfdpApprovalFramePK.frameOrder)
				.approvalAtr(EnumAdaptor.valueOf(this.approvalAtr, ApprovalBehaviorAtr.class))
				.confirmAtr(EnumAdaptor.valueOf(this.confirmAtr, ConfirmPerson.class))
				.approverID(this.approverID)
				.representerID(this.representerID)
				.approvalDate(this.approvalDate)
				.approvalReason(this.approvalReason)
				.build();
	}
}