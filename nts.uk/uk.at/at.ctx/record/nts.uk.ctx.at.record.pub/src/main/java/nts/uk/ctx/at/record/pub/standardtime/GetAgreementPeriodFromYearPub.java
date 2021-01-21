package nts.uk.ctx.at.record.pub.standardtime;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 年度から集計期間を取得
 * @author shuichu_ishida
 */
public interface GetAgreementPeriodFromYearPub {

	/**
	 * 年度から集計期間を取得
	 * @param year 年度
	 * @return 集計期間
	 */
	// RequestList459
	Optional<DatePeriod> algorithm(Year year);
}
