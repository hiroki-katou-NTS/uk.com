package nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmmonth;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
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

/**
 * 
 * @author Doan Duy Hung
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="WWFDT_APPROVAL_FRAME_MONTH")
@Builder
public class WwfdtApprovalFrameMonth extends UkJpaEntity {
	
	@EmbeddedId
	public WwfdpApprovalFrameMonthPK wwfdpApprovalFrameMonthPK;
	
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
	
	@ManyToOne
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="ROOT_STATE_ID",referencedColumnName="ROOT_STATE_ID"),
		@PrimaryKeyJoinColumn(name="PHASE_ORDER",referencedColumnName="PHASE_ORDER")
	})
	private WwfdtApprovalPhaseMonth wwfdtApprovalPhaseMonth;
	
	@OneToMany(targetEntity=WwfdtApproverMonth.class, cascade = CascadeType.ALL, mappedBy = "wwfdtApprovalFrameMonth", orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "WWFDT_APPROVER_MONTH")
	public List<WwfdtApproverMonth> listWwfdtApproverMonth;

	@Override
	protected Object getKey() {
		return wwfdpApprovalFrameMonthPK;
	}
	
	public static WwfdtApprovalFrameMonth fromDomain(String companyID, GeneralDate date, ApprovalFrame approvalFrame){
		return WwfdtApprovalFrameMonth.builder()
				.wwfdpApprovalFrameMonthPK(
						new WwfdpApprovalFrameMonthPK(
								approvalFrame.getRootStateID(), 
								approvalFrame.getPhaseOrder(), 
								approvalFrame.getFrameOrder()))
				.approvalAtr(approvalFrame.getApprovalAtr().value)
				.confirmAtr(approvalFrame.getConfirmAtr().value)
				.approverID(approvalFrame.getApproverID())
				.representerID(approvalFrame.getRepresenterID())
				.approvalDate(approvalFrame.getApprovalDate())
				.approvalReason(approvalFrame.getApprovalReason())
				.listWwfdtApproverMonth(
						approvalFrame.getListApproverState().stream()
						.map(x -> WwfdtApproverMonth.fromDomain(companyID, date, x)).collect(Collectors.toList()))
				.build();
	}
	
	public ApprovalFrame toDomain(){
		return ApprovalFrame.builder()
				.rootStateID(this.wwfdpApprovalFrameMonthPK.rootStateID)
				.phaseOrder(this.wwfdpApprovalFrameMonthPK.phaseOrder)
				.frameOrder(this.wwfdpApprovalFrameMonthPK.frameOrder)
				.approvalAtr(EnumAdaptor.valueOf(this.approvalAtr, ApprovalBehaviorAtr.class))
				.confirmAtr(EnumAdaptor.valueOf(this.confirmAtr, ConfirmPerson.class))
				.approverID(this.approverID)
				.representerID(this.representerID)
				.approvalDate(this.approvalDate)
				.approvalReason(this.approvalReason)
				.listApproverState(this.listWwfdtApproverMonth.stream()
									.map(x -> x.toDomain()).collect(Collectors.toList()))
				.build();
	}
}
