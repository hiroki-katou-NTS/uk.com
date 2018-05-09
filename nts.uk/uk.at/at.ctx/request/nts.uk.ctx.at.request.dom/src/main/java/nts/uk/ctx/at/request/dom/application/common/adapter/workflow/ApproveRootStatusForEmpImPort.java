package nts.uk.ctx.at.request.dom.application.common.adapter.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApproveRootStatusForEmpImPort {
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
	private ApprovalStatusForEmployeeImport approvalStatus;
}
