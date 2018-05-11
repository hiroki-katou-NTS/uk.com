package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import lombok.Data;
import lombok.Value;
import nts.arc.time.GeneralDate;

@Data
public class HistoryCmm053Command {
	/** */
	private String employeeId;
	/** 開始日 */
	private GeneralDate startDate;
	/** 終了日 */
	private GeneralDate endDate;
	/** 所属長のコード */
	private String departmentCode;
	/** 所属長のID*/
	private String departmentApproverId;
	/** 日別の承認する人のコード */
	private String dailyApprovalCode;
	/** 日別の承認する人のID */
	private String dailyApproverId;
	/** hasAuthority */
	private boolean hasAuthority;
}
