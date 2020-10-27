package nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmmonth;

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
@Table(name="WWFDT_APPROVER_MONTH")
@Builder
public class WwfdtApproverMonth extends ContractUkJpaEntity {
	
	@EmbeddedId
	public WwfdpApproverMonthPK wwfdpApproverMonthPK;
	
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
	private WwfdtAppInstFrameMonth wwfdtAppInstFrameMonth;

	@Override
	protected Object getKey() {
		return wwfdpApproverMonthPK;
	}
	
	public static WwfdtApproverMonth fromDomain(String companyID, String rootId, int phaseOrder, ApprovalFrame frame, ApproverInfor approverState){
		return WwfdtApproverMonth.builder()
				.wwfdpApproverMonthPK(
						new WwfdpApproverMonthPK(
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
//				.rootStateID(this.wwfdpApproverMonthPK.rootStateID)
//				.phaseOrder(this.wwfdpApproverMonthPK.phaseOrder)
//				.frameOrder(this.wwfdpApproverMonthPK.frameOrder)
				.approverID(this.wwfdpApproverMonthPK.approverID)
				.build();
	}
	
}
