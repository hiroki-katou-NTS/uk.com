package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ComplStateOfExeContentsImport {
	/** 実行内容 */
	private ExecutionContentImport executionContent;
	
	/** 従業員の実行状況 */
	private EmployeeExecutionStatusImport status;
}
