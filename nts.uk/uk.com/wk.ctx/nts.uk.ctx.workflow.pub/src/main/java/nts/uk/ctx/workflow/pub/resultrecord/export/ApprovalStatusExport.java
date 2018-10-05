package nts.uk.ctx.workflow.pub.resultrecord.export;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 承認状況
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprovalStatusExport {
	/**
	 * 解除可否区分
	 */
	private Integer releaseAtr;
	
	/**
	 * 基準社員の承認アクション
	 */
	private Integer approvalAction;
}
