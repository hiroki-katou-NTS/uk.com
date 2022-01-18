package nts.uk.ctx.at.record.pub.dailymonthlyprocessing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class SetInforReflAprResultExport {
	/**	実行内容 */
	private ExecutionContentExport executionContent;
	/**	実行種別 */
	private ExecutionTypeExport executionType;
	/**	 */
	private String calExecutionSetInfoID;
}
