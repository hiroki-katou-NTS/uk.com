package nts.uk.ctx.workflow.infra.entity.approverstatemanagement.application;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.RootType;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="WWFDT_APP_INST_ROUTE")
@Builder
public class WwfdtAppInstRoute extends ContractUkJpaEntity {
	
	@Id
	@Column(name="ROOT_STATE_ID")
	public String rootStateID;
	
	@Column(name="EMPLOYEE_ID")
	public String employeeID;
	
	@Column(name="APPROVAL_RECORD_DATE")
	public GeneralDate recordDate;
	
	@OneToMany(targetEntity=WwfdtAppInstPhaseate.class, cascade = CascadeType.ALL, mappedBy = "wwfdtAppInstRoute", orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "WWFDT_APP_INST_PHASE")
	public List<WwfdtAppInstPhaseate> listWwfdtPhase;

	@Override
	protected Object getKey() {
		return rootStateID; 
	}
	
	public static WwfdtAppInstRoute fromDomain(ApprovalRootState approvalRootState){
		return WwfdtAppInstRoute.builder()
				.rootStateID(approvalRootState.getRootStateID())
				.employeeID(approvalRootState.getEmployeeID())
				.recordDate(approvalRootState.getApprovalRecordDate())
				.listWwfdtPhase(
						approvalRootState.getListApprovalPhaseState().stream()
						.map(x -> WwfdtAppInstPhaseate.fromDomain(approvalRootState.getRootStateID(), x))
						.collect(Collectors.toList()))
				.build();
	}
	
	public ApprovalRootState toDomain(){
		return ApprovalRootState.builder()
				.rootStateID(this.rootStateID)
				.rootType(RootType.EMPLOYMENT_APPLICATION)
				.approvalRecordDate(this.recordDate)
				.employeeID(this.employeeID)
				.listApprovalPhaseState(this.listWwfdtPhase.stream()
											.map(x -> x.toDomain()).collect(Collectors.toList()))
				.build();
	}
}
