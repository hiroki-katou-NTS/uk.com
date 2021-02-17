package nts.uk.ctx.workflow.infra.entity.hrapproverstatemana;

import java.util.ArrayList;
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
import nts.uk.ctx.workflow.dom.hrapproverstatemana.ApprovalFrameHr;
import nts.uk.ctx.workflow.dom.hrapproverstatemana.ApprovalPhaseStateHr;
import nts.uk.ctx.workflow.dom.hrapproverstatemana.ApproverInforHr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 人事承認フェーズインスタンス
 * @author hoatt
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="WWFDT_HR_APPROVAL_PHASE_ST")
@Builder
public class WwfdtHrApprovalPhaseState extends ContractUkJpaEntity {
	/**主キー*/
	@EmbeddedId
	public WwfdtHrApprovalPhaseStatePK wwfdpHrApprovalPhaseStatePK;
	/**承認区分*/
	@Column(name="PHASE_ATR")
	public Integer approvalAtr;
	/**承認形態*/
	@Column(name="APPROVAL_FORM")
	public Integer approvalForm;
	
	@ManyToOne
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="ID",referencedColumnName="ID")
	})
	private WwfdtHrApprovalRootState wwfdtHrApprovalRootState;
	
	@OneToMany(targetEntity=WwfdtHrApproverState.class, cascade = CascadeType.ALL, mappedBy = "wwfdtHrApprovalPhaseState", orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "WWFDT_HR_APPROVER_STATE")
	public List<WwfdtHrApproverState> listWwfdtHrApprover;

	@Override
	protected Object getKey() {
		return wwfdpHrApprovalPhaseStatePK;
	}
	public static WwfdtHrApprovalPhaseState fromDomain(String rootStateID, ApprovalPhaseStateHr phaseState){
		List<WwfdtHrApproverState> lstApprover = new ArrayList<>();
		for(ApprovalFrameHr frame : phaseState.getLstApprovalFrame()){
			List<WwfdtHrApproverState> frameChild = frame.getLstApproverInfo().stream().map(x -> 
					WwfdtHrApproverState.fromDomain(rootStateID, phaseState.getPhaseOrder(), frame, x))
					.collect(Collectors.toList());
			lstApprover.addAll(frameChild);
		}
		return WwfdtHrApprovalPhaseState.builder()
				.wwfdpHrApprovalPhaseStatePK(
						new WwfdtHrApprovalPhaseStatePK(
								rootStateID, 
								phaseState.getPhaseOrder()))
				.approvalAtr(phaseState.getApprovalAtr().value)
				.approvalForm(phaseState.getApprovalForm().value)
				.listWwfdtHrApprover(lstApprover)
				.build();
	}
	public ApprovalPhaseStateHr toDomain(){
		List<ApprovalFrameHr> lstFrame = new ArrayList<>();
		List<WwfdtHrApproverState> lstFrameEntity = this.listWwfdtHrApprover;
		for(int i = 1; i <= 5; i++){
			int order = i;
			List<WwfdtHrApproverState> lstFrameChild = lstFrameEntity.stream()
					.filter(c -> c.wwfdpHrApproverStatePK.approverOrder == order).collect(Collectors.toList());
			if(lstFrameChild.isEmpty()) continue;
			WwfdtHrApproverState entity = lstFrameChild.get(0);
			List<ApproverInforHr> lstApproverInfo = lstFrameChild.stream()
					.map(c -> ApproverInforHr.convert(c.wwfdpHrApproverStatePK.approverID, c.approvalAtr, c.agentID, c.approvalDate, c.approvalReason))
					.collect(Collectors.toList());
			lstFrame.add(ApprovalFrameHr.convert(entity.wwfdpHrApproverStatePK.approverOrder, 
					entity.confirmAtr, entity.appDate, lstApproverInfo));
		}
		return ApprovalPhaseStateHr.builder()
				.phaseOrder(this.wwfdpHrApprovalPhaseStatePK.phaseOrder)
				.approvalAtr(EnumAdaptor.valueOf(this.approvalAtr, ApprovalBehaviorAtr.class))
				.approvalForm(EnumAdaptor.valueOf(this.approvalForm, ApprovalForm.class))
				.lstApprovalFrame(lstFrame)
				.build();
	}
}
