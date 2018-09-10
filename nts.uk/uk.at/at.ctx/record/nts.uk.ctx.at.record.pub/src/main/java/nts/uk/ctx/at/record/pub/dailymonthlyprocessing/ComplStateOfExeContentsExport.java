package nts.uk.ctx.at.record.pub.dailymonthlyprocessing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Setter
@Getter
public class ComplStateOfExeContentsExport {
	/** 実行内容 */
	private ExecutionContentExport executionContent;
	
	/** 従業員の実行状況 */
	private EmployeeExecutionStatusExport status;
}
