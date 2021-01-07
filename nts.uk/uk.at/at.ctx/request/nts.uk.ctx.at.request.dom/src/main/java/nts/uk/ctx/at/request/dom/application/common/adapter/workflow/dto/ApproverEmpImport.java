package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApproverEmpImport {
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
