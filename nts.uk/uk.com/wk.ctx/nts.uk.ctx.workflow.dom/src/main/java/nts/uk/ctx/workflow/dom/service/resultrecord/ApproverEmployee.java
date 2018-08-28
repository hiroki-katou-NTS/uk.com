package nts.uk.ctx.workflow.dom.service.resultrecord;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 社員
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApproverEmployee {
	
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
