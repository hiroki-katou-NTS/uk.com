package nts.uk.ctx.workflow.pub.resultrecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
/**
 * 対象社員
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class EmployeePerformParam {
	
	/**
	 * 対象日
	 */
	private GeneralDate date;
	
	/**
	 * 社員ID
	 */
	private String employeeID;
	
}
