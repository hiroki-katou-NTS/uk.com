package nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmmonth;

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
@Table(name="WWFDT_APPROVAL_ROOT_MONTH")
@Builder
public class WwfdtApprovalRootMonth extends ContractUkJpaEntity {
	
	@EmbeddedId
	public WwfdpApprovalRootMonthPK wwfdpApprovalRootMonthPK;
	
	@Column(name="HIST_ID")
	public String historyID;
	
	@Column(name="EMPLOYEE_ID")
	public String employeeID;
	
	@Column(name="APPROVAL_RECORD_DATE")
	public GeneralDate recordDate;
	
	@OneToMany(targetEntity=WwfdtApprovalPhaseMonth.class, cascade = CascadeType.ALL, mappedBy = "wwfdtApprovalRootMonth", orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "WWFDT_APPROVAL_PHASE_MONTH")
	public List<WwfdtApprovalPhaseMonth> listWwfdtApprovalPhaseMonth;

	@Override
	protected Object getKey() {
		return wwfdpApprovalRootMonthPK; 
	}
	
	public static WwfdtApprovalRootMonth fromDomain(String companyID, ApprovalRootState approvalRootState){
		return WwfdtApprovalRootMonth.builder()
				.wwfdpApprovalRootMonthPK(new WwfdpApprovalRootMonthPK(approvalRootState.getRootStateID()))
				.employeeID(approvalRootState.getEmployeeID())
				.recordDate(approvalRootState.getApprovalRecordDate())
				.listWwfdtApprovalPhaseMonth(
						approvalRootState.getListApprovalPhaseState().stream()
						.map(x -> WwfdtApprovalPhaseMonth.fromDomain(companyID, approvalRootState.getRootStateID(), x))
						.collect(Collectors.toList()))
				.build();
	}
	
	public ApprovalRootState toDomain(){
		return ApprovalRootState.builder()
				.rootStateID(this.wwfdpApprovalRootMonthPK.rootStateID)
				.rootType(RootType.CONFIRM_WORK_BY_MONTH)
				.approvalRecordDate(this.recordDate)
				.employeeID(this.employeeID)
				.listApprovalPhaseState(this.listWwfdtApprovalPhaseMonth.stream()
											.map(x -> x.toDomain()).collect(Collectors.toList()))
				.build();
	}
}
