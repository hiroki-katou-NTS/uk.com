package nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmday;

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
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalFrame;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApproverInfor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="WWFDT_APPROVER_DAY")
@Builder
public class WwfdtApproverDay extends ContractUkJpaEntity {
	
	@EmbeddedId
	public WwfdpApproverDayPK wwfdpApproverDayPK;
	
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
	private WwfdtAppInstFrameDay wwfdtAppInstFrameDay;

	@Override
	protected Object getKey() {
		return wwfdpApproverDayPK;
	}
	
	public static WwfdtApproverDay fromDomain(String companyID, String rootId, int phaseOrder, ApprovalFrame frame, ApproverInfor approverState){
		return WwfdtApproverDay.builder()
				.wwfdpApproverDayPK(
						new WwfdpApproverDayPK(
								rootId, 
								phaseOrder, 
								frame.getFrameOrder(), 
								approverState.getApproverID()))
				.companyID(companyID)
				.recordDate(frame.getAppDate())
				.build();
	}
	
	public ApproverInfor toDomain(){
		return ApproverInfor.builder()
//				.rootStateID(this.wwfdpApproverDayPK.rootStateID)
//				.phaseOrder(this.wwfdpApproverDayPK.phaseOrder)
//				.frameOrder(this.wwfdpApproverDayPK.frameOrder)
				.approverID(this.wwfdpApproverDayPK.approverID)
				.build();
	}
	
}
