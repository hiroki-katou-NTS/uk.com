package nts.uk.ctx.at.shared.dom.adapter.dailyperformance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
/**
 * Import承認ルート状況
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
	 * 0: 未承認
	 * 1: 承認中
	 * 2. 承認済
	 */
	private int approvalStatus;
}
