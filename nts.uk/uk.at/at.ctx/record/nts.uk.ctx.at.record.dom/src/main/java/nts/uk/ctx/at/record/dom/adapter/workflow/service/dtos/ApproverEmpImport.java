package nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
