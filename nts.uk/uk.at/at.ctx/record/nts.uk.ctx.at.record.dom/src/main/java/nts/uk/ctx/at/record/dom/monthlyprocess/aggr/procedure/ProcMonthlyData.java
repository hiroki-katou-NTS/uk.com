package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.procedure;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 月別実績データストアドプロシージャ
 * @author shuichu_ishida
 */
public interface ProcMonthlyData {

	/**
	 * 月別実績データストアドプロシージャ
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 */
	void execute(String companyId, String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate);
}
