/**
 * 9:08:20 AM Mar 12, 2018
 */
package nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApprovalActionByEmpl;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ReleasedProprietyDivision;

/**
 * @author hungnm
 *
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

