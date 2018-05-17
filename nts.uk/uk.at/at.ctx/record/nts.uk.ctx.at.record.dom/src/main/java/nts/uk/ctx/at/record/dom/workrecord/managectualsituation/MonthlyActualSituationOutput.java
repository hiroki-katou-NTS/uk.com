package nts.uk.ctx.at.record.dom.workrecord.managectualsituation;

import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;
/**
 * «Output» 月の実績の状況
 */
@Data
public class MonthlyActualSituationOutput {
	/**
	 * 社員の締め情報
	 */
	EmployeeClosingInfo employeeClosingInfo;
	/**
	 * 月別実績のロック状態
	 */
	LockStatus monthlyLockStatus;
	/**
	 * 就業確定状態
	 */
	EmploymentFixedStatus employmentFixedStatus;
	/**
	 * 承認状況
	 */
	ApprovalStatus approvalStatus;
	/**
	 * 日の実績の状況
	 */
	DailyActualSituation dailyActualSituation;
}
