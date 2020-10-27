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
@Table(name="WWFDT_APPROVAL_PHASE_DAY")
@Builder
public class WwfdtApprovalPhaseDay extends ContractUkJpaEntity {
	
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
	
	@OneToMany(targetEntity=WwfdtAppInstFrameDay.class, cascade = CascadeType.ALL, mappedBy = "wwfdtApprovalPhaseDay", orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "WWFDT_APP_INST_FRAME_DAY")
	public List<WwfdtAppInstFrameDay> listWwfdtAppInstFrameDay;

	@Override
	protected Object getKey() {
		return wwfdpApprovalPhaseDayPK;
	}
	
	public static WwfdtApprovalPhaseDay fromDomain(String companyID, String rootId, ApprovalPhaseState phase){
		return WwfdtApprovalPhaseDay.builder()
				.wwfdpApprovalPhaseDayPK(
						new WwfdpApprovalPhaseDayPK(
								rootId, 
								phase.getPhaseOrder()))
				.approvalAtr(phase.getApprovalAtr().value)
				.approvalForm(phase.getApprovalForm().value)
				.listWwfdtAppInstFrameDay(
						phase.getListApprovalFrame().stream()
						.map(x -> WwfdtAppInstFrameDay.fromDomain(companyID, rootId, phase.getPhaseOrder(), x)).collect(Collectors.toList()))
				.build();
	}
	
	public ApprovalPhaseState toDomain(){
		return ApprovalPhaseState.builder()
				.phaseOrder(this.wwfdpApprovalPhaseDayPK.phaseOrder)
				.approvalAtr(EnumAdaptor.valueOf(this.approvalAtr, ApprovalBehaviorAtr.class))
				.approvalForm(EnumAdaptor.valueOf(this.approvalForm, ApprovalForm.class))
				.listApprovalFrame(this.listWwfdtAppInstFrameDay.stream()
									.map(x -> x.toDomain()).collect(Collectors.toList()))
				.build();
	}
}
