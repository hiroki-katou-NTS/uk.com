package nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmmonth;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
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
public class WwfdtAppRootMonthSimple extends ContractUkJpaEntity {
	
	@EmbeddedId
	public WwfdpApprovalRootMonthPK wwfdpApprovalRootMonthPK;
	
	@Column(name="HIST_ID")
	public String historyID;
	
	@Column(name="EMPLOYEE_ID")
	public String employeeID;
	
	@Column(name="APPROVAL_RECORD_DATE")
	public GeneralDate recordDate;

	@Override
	protected Object getKey() {
		return wwfdpApprovalRootMonthPK; 
	}
	
}
