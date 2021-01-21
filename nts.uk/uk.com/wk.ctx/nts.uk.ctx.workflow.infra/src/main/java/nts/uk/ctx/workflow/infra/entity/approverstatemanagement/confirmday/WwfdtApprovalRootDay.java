package nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmday;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="WWFDT_APPROVAL_ROOT_DAY")
@Builder
public class WwfdtApprovalRootDay extends ContractUkJpaEntity {
	
	@EmbeddedId
	public WwfdpApprovalRootDayPK wwfdpApprovalRootDayPK;
	
	@Column(name="HIST_ID")
	public String historyID;
	
	@Column(name="EMPLOYEE_ID")
	public String employeeID;
	
	@Column(name="APPROVAL_RECORD_DATE")
	public GeneralDate recordDate;
	
	@OneToMany(targetEntity=WwfdtApprovalPhaseDay.class, cascade = CascadeType.ALL, mappedBy = "wwfdtApprovalRootDay", orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "WWFDT_APPROVAL_PHASE_DAY")
	public List<WwfdtApprovalPhaseDay> listWwfdtApprovalPhaseDay;

	@Override
	protected Object getKey() {
		return wwfdpApprovalRootDayPK; 
	}
	
	public static WwfdtApprovalRootDay fromDomain(String companyID, ApprovalRootState approvalRootState){
		return WwfdtApprovalRootDay.builder()
				.wwfdpApprovalRootDayPK(new WwfdpApprovalRootDayPK(approvalRootState.getRootStateID()))
				.employeeID(approvalRootState.getEmployeeID())
				.recordDate(approvalRootState.getApprovalRecordDate())
				.listWwfdtApprovalPhaseDay(
						approvalRootState.getListApprovalPhaseState().stream()
						.map(x -> WwfdtApprovalPhaseDay.fromDomain(companyID, approvalRootState.getRootStateID(), x))
						.collect(Collectors.toList()))
				.build();
	}
	
	public ApprovalRootState toDomain(){
		return ApprovalRootState.builder()
				.rootStateID(this.wwfdpApprovalRootDayPK.rootStateID)
				.rootType(RootType.CONFIRM_WORK_BY_DAY)
				.approvalRecordDate(this.recordDate)
				.employeeID(this.employeeID)
				.listApprovalPhaseState(this.listWwfdtApprovalPhaseDay.stream()
											.map(x -> x.toDomain()).collect(Collectors.toList()))
				.build();
	}
}
