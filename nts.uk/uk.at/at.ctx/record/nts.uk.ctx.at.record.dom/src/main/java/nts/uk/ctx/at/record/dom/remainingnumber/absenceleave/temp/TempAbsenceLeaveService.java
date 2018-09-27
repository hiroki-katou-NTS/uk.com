package nts.uk.ctx.at.record.dom.remainingnumber.absenceleave.temp;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AbsenceLeaveRemainData;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * （仮対応用）振休
 * @author shuichu_ishida
 */
public interface TempAbsenceLeaveService {

	/**
	 * （仮対応用）振休
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param period 期間
	 * @param closureId 締めID
	 * @param closureDate 締め日
	 * @return 振休月別残数データ 
	 */
	AbsenceLeaveRemainData algorithm(String companyId, String employeeId, YearMonth yearMonth,
			DatePeriod period, ClosureId closureId, ClosureDate closureDate);
}
