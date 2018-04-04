package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * @author dat.lh
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
	private ApprovalStatusForEmployee_New approvalStatus;
}
