package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class SetInforReflAprResultImport {
	/**	実行内容 */
	private ExecutionContentImport executionContent;
	/**	実行種別 */
	private ExecutionTypeExImport executionType;
	/**	 */
	private String calExecutionSetInfoID;
	/**	確定済みの場合にも強制的に反映する */
	private boolean forciblyReflect;
}
