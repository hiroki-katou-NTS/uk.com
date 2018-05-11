package nts.uk.ctx.workflow.infra.entity.approverstatemanagement;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApproverState;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="WWFDT_APPROVER_STATE")
@Builder
public class WwfdtApproverStateNew extends UkJpaEntity {
	@EmbeddedId
	public WwfdpApproverStatePK wwfdpApproverStatePK;
	
	@Override
	protected Object getKey() {
		return wwfdpApproverStatePK;
	}
	
	public static WwfdtApproverState fromDomain(ApproverState approverState){
		return WwfdtApproverState.builder()
				.wwfdpApproverStatePK(
						new WwfdpApproverStatePK(
								approverState.getRootStateID(), 
								approverState.getPhaseOrder(), 
								approverState.getFrameOrder(), 
								approverState.getApproverID()))
				.build();
	}
	
	public ApproverState toDomain(){
		return ApproverState.builder()
				.rootStateID(this.wwfdpApproverStatePK.rootStateID)
				.phaseOrder(this.wwfdpApproverStatePK.phaseOrder)
				.frameOrder(this.wwfdpApproverStatePK.frameOrder)
				.approverID(this.wwfdpApproverStatePK.approverID)
				.build();
	}
	
}