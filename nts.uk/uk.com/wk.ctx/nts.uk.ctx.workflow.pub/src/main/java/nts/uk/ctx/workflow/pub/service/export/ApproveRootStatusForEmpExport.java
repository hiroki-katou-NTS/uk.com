package nts.uk.ctx.workflow.pub.service.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * @author loivt
 * 承認ルート状況
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApproveRootStatusForEmpExport {
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
