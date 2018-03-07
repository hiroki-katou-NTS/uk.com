package nts.uk.ctx.workflow.pub.service.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author loivt
 * 承認状況
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalStatus {
	/**
	 * 基準社員の承認アクション
	 */
	private ApprovalActionByEmpl approvalActionByEmpl;
	/**
	 * 解除可否区分
	 */
	private ReleasedProprietyDivision releaseDivision;
}
