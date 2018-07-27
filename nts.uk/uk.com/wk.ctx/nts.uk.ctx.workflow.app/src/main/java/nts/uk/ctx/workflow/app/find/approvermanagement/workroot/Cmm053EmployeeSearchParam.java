package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class Cmm053EmployeeSearchParam {
	/**
	 * 社員コード
	 */
	private String employeeCode;

	/**
	 * 承認権限
	 */
	private boolean hasAuthority;

	/**
	 * 基準日
	 */
	private GeneralDate baseDate;
}
