package nts.uk.ctx.workflow.pub.resultrecord;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 社員
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApproverEmpExport {
	
	/**
	 * 社員ID
	 */
	private String employeeID;
	
	/**
	 * 社員コード
	 */
	private String employeeCD;
	
	/**
	 * 社員名
	 */
	private String employeeName;
	
}
