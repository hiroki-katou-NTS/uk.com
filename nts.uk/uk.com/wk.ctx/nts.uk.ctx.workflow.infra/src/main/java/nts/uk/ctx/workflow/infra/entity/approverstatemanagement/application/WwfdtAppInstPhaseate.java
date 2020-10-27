package nts.uk.ctx.workflow.infra.entity.approverstatemanagement.application;

import java.util.ArrayList;
import java.util.Comparator;
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
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalFrame;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
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
@Table(name="WWFDT_APP_INST_PHASE")
@Builder
public class WwfdtAppInstPhaseate extends ContractUkJpaEntity {
	
	@EmbeddedId
	public WwfdpApprovalPhaseStatePK wwfdpApprovalPhaseStatePK;
	
	@Column(name="APP_PHASE_ATR")
	public Integer approvalAtr;
	
	@Column(name="APPROVAL_FORM")
	public Integer approvalForm;
	
	@ManyToOne
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="ROOT_STATE_ID",referencedColumnName="ROOT_STATE_ID")
	})
	private WwfdtAppInstRoute wwfdtAppInstRoute;
	
	@OneToMany(targetEntity=WwfdtAppInstApprover.class, cascade = CascadeType.ALL, mappedBy = "wwfdtAppInstPhaseate", orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "WWFDT_APP_INST_APPROVER")
	public List<WwfdtAppInstApprover> listWwfdtApprover;

	@Override
	protected Object getKey() {
		return wwfdpApprovalPhaseStatePK;
	}
	
	public static WwfdtAppInstPhaseate fromDomain(String rootStateID, ApprovalPhaseState phaseState){
		List<WwfdtAppInstApprover> lstApprover = new ArrayList<>();
		for(ApprovalFrame frame : phaseState.getListApprovalFrame()){
			List<WwfdtAppInstApprover> frameChild = frame.getLstApproverInfo().stream().map(x -> 
					WwfdtAppInstApprover.fromDomain(rootStateID, phaseState.getPhaseOrder(), frame, x))
					.collect(Collectors.toList());
			lstApprover.addAll(frameChild);
		}
		return WwfdtAppInstPhaseate.builder()
				.wwfdpApprovalPhaseStatePK(
						new WwfdpApprovalPhaseStatePK(
								rootStateID, 
								phaseState.getPhaseOrder()))
				.approvalAtr(phaseState.getApprovalAtr().value)
				.approvalForm(phaseState.getApprovalForm().value)
				.listWwfdtApprover(lstApprover)
				.build();
	}
	
	public ApprovalPhaseState toDomain(){
		List<ApprovalFrame> lstFrame = new ArrayList<>();
		List<WwfdtAppInstApprover> lstFrameEntity = this.listWwfdtApprover;
		for(int i = 1; i <= 5; i++){
			int order = i;
			List<WwfdtAppInstApprover> lstFrameChild = lstFrameEntity.stream()
					.filter(c -> c.wwfdpApprovrStatePK.approverOrder == order).collect(Collectors.toList());
			if(lstFrameChild.isEmpty()) continue;
			WwfdtAppInstApprover entity = lstFrameChild.get(0);
			List<ApproverInfor> lstApproverInfo = lstFrameChild.stream()
					.map(c -> ApproverInfor.convert(c.wwfdpApprovrStatePK.approverId, c.approvalAtr, c.agentID, c.approvalDate, c.approvalReason, c.approverInListOrder))
					.sorted(Comparator.comparing(ApproverInfor::getApproverInListOrder)).collect(Collectors.toList());
			lstFrame.add(ApprovalFrame.convert(
					entity.wwfdpApprovrStatePK.approverOrder, 
					entity.confirmAtr, entity.appDate, lstApproverInfo));
		}
		return ApprovalPhaseState.builder()
				.phaseOrder(this.wwfdpApprovalPhaseStatePK.phaseOrder)
				.approvalAtr(EnumAdaptor.valueOf(this.approvalAtr, ApprovalBehaviorAtr.class))
				.approvalForm(EnumAdaptor.valueOf(this.approvalForm, ApprovalForm.class))
				.listApprovalFrame(lstFrame)
				.build();
	}
}
