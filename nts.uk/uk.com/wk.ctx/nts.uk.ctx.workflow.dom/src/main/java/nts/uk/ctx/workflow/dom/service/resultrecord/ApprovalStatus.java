package nts.uk.ctx.workflow.dom.service.resultrecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 承認状況
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApprovalStatus {
	
	/**
	 * 解除可否区分
	 */
	private ReleaseDivision releaseAtr;
	
	/**
	 * 基準社員の承認アクション
	 */
	private ApprovalActionByEmp approvalAction;
	
}
