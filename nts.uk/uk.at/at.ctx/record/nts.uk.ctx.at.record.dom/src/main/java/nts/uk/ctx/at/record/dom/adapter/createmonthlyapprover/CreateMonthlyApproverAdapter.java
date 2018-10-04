package nts.uk.ctx.at.record.dom.adapter.createmonthlyapprover;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 承認状態の作成（月次）
 * @author shuichi_ishida
 */
public interface CreateMonthlyApproverAdapter {

	/**
	 * RequestList 527
	 * [No.527]承認状態を作成する（月別）
	 * @param employeeID 社員ID
	 * @param date 年月日
	 * @param yearMonth 年月
	 * @param closureID 締めID
	 * @param closureDate 締め日
	 */
	public void createApprovalStatusMonth(String employeeID, GeneralDate date, YearMonth yearMonth, Integer closureID, ClosureDate closureDate);
}
