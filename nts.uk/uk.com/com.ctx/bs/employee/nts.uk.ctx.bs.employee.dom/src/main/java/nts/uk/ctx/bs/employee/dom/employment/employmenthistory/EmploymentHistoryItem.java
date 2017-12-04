package nts.uk.ctx.bs.employee.dom.employment.employmenthistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentCode;


/**
 * The Class EmploymentHistoryItem.
 */
// 雇用履歴項目
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmploymentHistoryItem extends AggregateRoot{

	/** The history Id. */
	// 履歴ID
	private String historyId;
	
	/** The Employee Id. */
	// 社員ID
	private String employeeId;
	
	/** The SalarySegment */
	// 給与区分
	private SalarySegment salarySegment;
	
	/** The employment code. */
	// 雇用コード. 
	private EmploymentCode employmentCode;
	
	

	
	
}
