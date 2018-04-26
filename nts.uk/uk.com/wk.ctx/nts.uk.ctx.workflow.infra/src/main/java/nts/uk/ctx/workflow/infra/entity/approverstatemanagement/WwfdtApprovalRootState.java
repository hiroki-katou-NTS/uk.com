package nts.uk.ctx.workflow.infra.entity.approverstatemanagement;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.RootType;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="WWFDT_APPROVAL_ROOT_STATE")
@Builder
public class WwfdtApprovalRootState extends UkJpaEntity {
	
	@EmbeddedId
	public WwfdpApprovalRootStatePK wwfdpApprovalRootStatePK;
	
	@Column(name="ROOT_TYPE")
	public Integer rootType;
	
	@Column(name="HIS_ID")
	public String historyID;
	
	@Column(name="EMPLOYEE_ID")
	public String employeeID;
	
	@Column(name="APPROVAL_RECORD_DATE")
	public GeneralDate recordDate;
	
	@OneToMany(targetEntity=WwfdtApprovalPhaseState.class, cascade = CascadeType.ALL, mappedBy = "wwfdtApprovalRootState", orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "WWFDT_APPROVAL_PHASE_ST")
	public List<WwfdtApprovalPhaseState> listWwfdtApprovalPhaseState;

	@Override
	protected Object getKey() {
		return wwfdpApprovalRootStatePK; 
	}
	
	public static WwfdtApprovalRootState fromDomain(ApprovalRootState approvalRootState){
		return WwfdtApprovalRootState.builder()
				.wwfdpApprovalRootStatePK(new WwfdpApprovalRootStatePK(approvalRootState.getRootStateID()))
				.rootType(approvalRootState.getRootType().value)
				.historyID(approvalRootState.getHistoryID())
				.employeeID(approvalRootState.getEmployeeID())
				.recordDate(approvalRootState.getApprovalRecordDate())
				.listWwfdtApprovalPhaseState(
						approvalRootState.getListApprovalPhaseState().stream()
						.map(x -> WwfdtApprovalPhaseState.fromDomain(x)).collect(Collectors.toList()))
				.build();
	}
	
	public ApprovalRootState toDomain(){
		return ApprovalRootState.builder()
				.rootStateID(this.wwfdpApprovalRootStatePK.rootStateID)
				.rootType(EnumAdaptor.valueOf(this.rootType, RootType.class))
				.historyID(this.historyID)
				.approvalRecordDate(this.recordDate)
				.employeeID(this.employeeID)
				.listApprovalPhaseState(this.listWwfdtApprovalPhaseState.stream()
											.map(x -> x.toDomain()).collect(Collectors.toList()))
				.build();
	}
}
