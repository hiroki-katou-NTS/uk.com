/**
 * 5:00:46 PM Mar 9, 2018
 */
package nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApprovalStatusForEmployee;

/**
 * @author hungnm
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApproveRootStatusForEmpImport {
	/**
	 * 承認対象者
	 */
	private String employeeID;
	/**
	 * 年月日
	 */
	private GeneralDate appDate;
	/**
	 * 承認状況
	 */
	private ApprovalStatusForEmployee approvalStatus;
}
