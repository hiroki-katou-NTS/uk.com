package nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmday;

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
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalForm;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="WWFDT_APPROVAL_PHASE_DAY")
@Builder
public class WwfdtApprovalPhaseDay extends UkJpaEntity {
	
	@EmbeddedId
	public WwfdpApprovalPhaseDayPK wwfdpApprovalPhaseDayPK;
	
	@Column(name="APPROVAL_ATR")
	public Integer approvalAtr;
	
	@Column(name="APPROVAL_FORM")
	public Integer approvalForm;
	
	@ManyToOne
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="ROOT_STATE_ID",referencedColumnName="ROOT_STATE_ID")
	})
	private WwfdtApprovalRootDay wwfdtApprovalRootDay;
	
	@OneToMany(targetEntity=WwfdtApprovalFrameDay.class, cascade = CascadeType.ALL, mappedBy = "wwfdtApprovalPhaseDay", orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "WWFDT_APPROVAL_FRAME_DAY")
	public List<WwfdtApprovalFrameDay> listWwfdtApprovalFrameDay;

	@Override
	protected Object getKey() {
		return wwfdpApprovalPhaseDayPK;
	}
	
	public static WwfdtApprovalPhaseDay fromDomain(String companyID, GeneralDate date, ApprovalPhaseState approvalPhaseState){
		return WwfdtApprovalPhaseDay.builder()
				.wwfdpApprovalPhaseDayPK(
						new WwfdpApprovalPhaseDayPK(
								approvalPhaseState.getRootStateID(), 
								approvalPhaseState.getPhaseOrder()))
				.approvalAtr(approvalPhaseState.getApprovalAtr().value)
				.approvalForm(approvalPhaseState.getApprovalForm().value)
				.listWwfdtApprovalFrameDay(
						approvalPhaseState.getListApprovalFrame().stream()
						.map(x -> WwfdtApprovalFrameDay.fromDomain(companyID, date, x)).collect(Collectors.toList()))
				.build();
	}
	
	public ApprovalPhaseState toDomain(){
		return ApprovalPhaseState.builder()
				.rootStateID(this.wwfdpApprovalPhaseDayPK.rootStateID)
				.phaseOrder(this.wwfdpApprovalPhaseDayPK.phaseOrder)
				.approvalAtr(EnumAdaptor.valueOf(this.approvalAtr, ApprovalBehaviorAtr.class))
				.approvalForm(EnumAdaptor.valueOf(this.approvalForm, ApprovalForm.class))
				.listApprovalFrame(this.listWwfdtApprovalFrameDay.stream()
									.map(x -> x.toDomain()).collect(Collectors.toList()))
				.build();
	}
}
