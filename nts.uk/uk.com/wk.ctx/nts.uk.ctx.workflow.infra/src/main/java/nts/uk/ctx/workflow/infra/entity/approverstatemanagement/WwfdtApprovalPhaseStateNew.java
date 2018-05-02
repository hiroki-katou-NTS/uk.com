package nts.uk.ctx.workflow.infra.entity.approverstatemanagement;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalForm;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="WWFDT_APPROVAL_PHASE_ST")
@Builder
public class WwfdtApprovalPhaseStateNew extends UkJpaEntity{
	@EmbeddedId
	public WwfdpApprovalPhaseStatePK wwfdpApprovalPhaseStatePK;
	
	@Column(name="APPROVAL_ATR")
	public Integer approvalAtr;
	
	@Column(name="APPROVAL_FORM")
	public Integer approvalForm;
	
	@Override
	protected Object getKey() {
		return wwfdpApprovalPhaseStatePK;
	}
	
	public static WwfdtApprovalPhaseState fromDomain(ApprovalPhaseState approvalPhaseState){
		return WwfdtApprovalPhaseState.builder()
				.wwfdpApprovalPhaseStatePK(
						new WwfdpApprovalPhaseStatePK(
								approvalPhaseState.getRootStateID(), 
								approvalPhaseState.getPhaseOrder()))
				.approvalAtr(approvalPhaseState.getApprovalAtr().value)
				.approvalForm(approvalPhaseState.getApprovalForm().value)
				.build();
	}
	
	public ApprovalPhaseState toDomain(){
		return ApprovalPhaseState.builder()
				.rootStateID(this.wwfdpApprovalPhaseStatePK.rootStateID)
				.phaseOrder(this.wwfdpApprovalPhaseStatePK.phaseOrder)
				.approvalAtr(EnumAdaptor.valueOf(this.approvalAtr, ApprovalBehaviorAtr.class))
				.approvalForm(EnumAdaptor.valueOf(this.approvalForm, ApprovalForm.class))
				.build();
	}
}
