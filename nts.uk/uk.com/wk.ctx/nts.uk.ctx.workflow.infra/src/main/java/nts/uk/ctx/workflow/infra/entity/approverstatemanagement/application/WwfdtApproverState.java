package nts.uk.ctx.workflow.infra.entity.approverstatemanagement.application;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApproverState;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="WWFDT_APPROVER_STATE")
@Builder
public class WwfdtApproverState extends UkJpaEntity {
	
	@EmbeddedId
	public WwfdpApproverStatePK wwfdpApproverStatePK;
	
	@Column(name="CID")
	public String companyID;
	
	@Column(name="APPROVAL_RECORD_DATE")
	public GeneralDate recordDate;
	
	@ManyToOne
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="ROOT_STATE_ID",referencedColumnName="ROOT_STATE_ID"),
		@PrimaryKeyJoinColumn(name="PHASE_ORDER",referencedColumnName="PHASE_ORDER"),
		@PrimaryKeyJoinColumn(name="FRAME_ORDER",referencedColumnName="FRAME_ORDER")
	})
	private WwfdtApprovalFrame wwfdtApprovalFrame;

	@Override
	protected Object getKey() {
		return wwfdpApproverStatePK;
	}
	
	public static WwfdtApproverState fromDomain(String companyID, GeneralDate date, ApproverState approverState){
		return WwfdtApproverState.builder()
				.wwfdpApproverStatePK(
						new WwfdpApproverStatePK(
								approverState.getRootStateID(), 
								approverState.getPhaseOrder(), 
								approverState.getFrameOrder(), 
								approverState.getApproverID()))
				.companyID(companyID)
				.recordDate(date)
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
