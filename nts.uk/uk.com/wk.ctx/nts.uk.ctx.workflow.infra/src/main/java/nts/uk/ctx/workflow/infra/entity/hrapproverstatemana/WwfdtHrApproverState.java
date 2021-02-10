package nts.uk.ctx.workflow.infra.entity.hrapproverstatemana;

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
import nts.uk.ctx.workflow.dom.hrapproverstatemana.ApprovalFrameHr;
import nts.uk.ctx.workflow.dom.hrapproverstatemana.ApproverInforHr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 人事承認枠_承認者情報
 * @author hoatt
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="WWFDT_HR_APPROVER_STATE")
@Builder
public class WwfdtHrApproverState extends ContractUkJpaEntity {
	/**主キー*/
	@EmbeddedId
	public WwfdtHrApproverStatePK wwfdpHrApproverStatePK;
	/**承認区分*/
	@Column(name="APPROVAL_ATR")
	public Integer approvalAtr;
	/**確定区分*/
	@Column(name="CONFIRM_ATR")
	public Integer confirmAtr;
	/**代行者*/
	@Column(name="AGENT_ID")
	public String agentID;
	/**承認日*/
	@Column(name="APPROVAL_DATE")
	public GeneralDate approvalDate;
	/**理由*/
	@Column(name="APPROVAL_REASON")
	public String approvalReason;
	/**対象日*/
	@Column(name="APP_DATE")
	public GeneralDate appDate;
	
	@ManyToOne
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="ID",referencedColumnName="ID"),
		@PrimaryKeyJoinColumn(name="PHASE_ORDER",referencedColumnName="PHASE_ORDER")
	})
	private WwfdtHrApprovalPhaseState wwfdtHrApprovalPhaseState;
	
	@Override
	protected Object getKey() {
		return wwfdpHrApproverStatePK;
	}
	
	public static WwfdtHrApproverState fromDomain(String rootStateID, int phaseOrder, ApprovalFrameHr frame , ApproverInforHr approverInfo){
		return WwfdtHrApproverState.builder()
				.wwfdpHrApproverStatePK(
						new WwfdtHrApproverStatePK(
								rootStateID, 
								phaseOrder, 
								frame.getFrameOrder(),
								approverInfo.getApproverID()))
				.approvalAtr(approverInfo.getApprovalAtr().value)
				.confirmAtr(frame.getConfirmAtr().value)
				.agentID(approverInfo.getAgentID())
				.approvalDate(approverInfo.getApprovalDate())
				.approvalReason(approverInfo.getApprovalReason())
				.appDate(frame.getAppDate())
				.build();
	}
	
//	public ApprovalFrameHr toDomain(ApprovalBehaviorAtr approvalAtr, List<String> lstAprroverId){
//		return ApprovalFrameHr.builder()
//				.frameOrder(this.wwfdpHrApprovalFramePK.frameOrder)
//				.approvalAtr(approvalAtr)
//				.confirmAtr(EnumAdaptor.valueOf(this.confirmAtr, ConfirmPerson.class))
//				.lstApproverID(lstAprroverId)
//				.representerID(this.representerID)
//				.approvalDate(this.approvalDate)
//				.approvalReason(this.approvalReason)
//				.appDate(this.appDate)
//				.build();
//	}
}
