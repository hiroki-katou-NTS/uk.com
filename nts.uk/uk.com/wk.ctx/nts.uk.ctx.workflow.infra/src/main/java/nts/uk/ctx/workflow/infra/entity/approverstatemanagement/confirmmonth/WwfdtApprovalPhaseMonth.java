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
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalForm;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="WWFDT_APPROVAL_PHASE_MONTH")
@Builder
public class WwfdtApprovalPhaseMonth extends ContractUkJpaEntity {
	
	@EmbeddedId
	public WwfdpApprovalPhaseMonthPK wwfdpApprovalPhaseMonthPK;
	
	@Column(name="APPROVAL_ATR")
	public Integer approvalAtr;
	
	@Column(name="APPROVAL_FORM")
	public Integer approvalForm;
	
	@ManyToOne
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="ROOT_STATE_ID",referencedColumnName="ROOT_STATE_ID")
	})
	private WwfdtApprovalRootMonth wwfdtApprovalRootMonth;
	
	@OneToMany(targetEntity=WwfdtApprovalFrameMonth.class, cascade = CascadeType.ALL, mappedBy = "wwfdtApprovalPhaseMonth", orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "WWFDT_APPROVAL_FRAME_MONTH")
	public List<WwfdtApprovalFrameMonth> listWwfdtApprovalFrameMonth;

	@Override
	protected Object getKey() {
		return wwfdpApprovalPhaseMonthPK;
	}
	
	public static WwfdtApprovalPhaseMonth fromDomain(String companyID, String rootId, ApprovalPhaseState approvalPhaseState){
		return WwfdtApprovalPhaseMonth.builder()
				.wwfdpApprovalPhaseMonthPK(
						new WwfdpApprovalPhaseMonthPK(
								rootId, 
								approvalPhaseState.getPhaseOrder()))
				.approvalAtr(approvalPhaseState.getApprovalAtr().value)
				.approvalForm(approvalPhaseState.getApprovalForm().value)
				.listWwfdtApprovalFrameMonth(
						approvalPhaseState.getListApprovalFrame().stream()
						.map(x -> WwfdtApprovalFrameMonth.fromDomain(companyID, rootId, approvalPhaseState.getPhaseOrder(), x)).collect(Collectors.toList()))
				.build();
	}
	
	public ApprovalPhaseState toDomain(){
		return ApprovalPhaseState.builder()
				.phaseOrder(this.wwfdpApprovalPhaseMonthPK.phaseOrder)
				.approvalAtr(EnumAdaptor.valueOf(this.approvalAtr, ApprovalBehaviorAtr.class))
				.approvalForm(EnumAdaptor.valueOf(this.approvalForm, ApprovalForm.class))
				.listApprovalFrame(this.listWwfdtApprovalFrameMonth.stream()
									.map(x -> x.toDomain()).collect(Collectors.toList()))
				.build();
	}
}
