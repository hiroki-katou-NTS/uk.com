package nts.uk.ctx.at.shared.dom.employmentrules.workclosuredate;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 月別実績集計期間を算出する
 * @author tutk
 *
 */
public interface CalculateMonthlyPeriodService {
	public DatePeriod calculateMonthlyPeriod(Integer closureId,GeneralDate baseDate);
}
