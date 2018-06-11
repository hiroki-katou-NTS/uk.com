package nts.uk.ctx.at.record.dom.standardtime.export;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 年度から集計期間を取得
 * @author shuichu_ishida
 */
public interface GetAgreementPeriodFromYear {

	/**
	 * 年度から集計期間を取得
	 * @param year 年度
	 * @param closure 締め
	 * @return 集計期間
	 */
	Optional<DatePeriod> algorithm(Year year, Closure closure);
}
