package nts.uk.ctx.at.function.dom.adapter.standardtime;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author dat.lh
 *
 */
public interface GetAgreementPeriodFromYearAdapter {
	/**
	 * 年度から集計期間を取得
	 * @param year 年度
	 * @param closure 締め
	 * @return 集計期間
	 */
	Optional<DatePeriod> algorithm(Year year, Closure closure);
}
