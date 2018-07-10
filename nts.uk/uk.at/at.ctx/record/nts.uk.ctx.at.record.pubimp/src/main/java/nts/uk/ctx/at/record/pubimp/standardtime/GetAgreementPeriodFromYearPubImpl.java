package nts.uk.ctx.at.record.pubimp.standardtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.standardtime.export.GetAgreementPeriodFromYear;
import nts.uk.ctx.at.record.pub.standardtime.GetAgreementPeriodFromYearPub;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実装：年度から集計期間を取得
 * @author shuichu_ishida
 */
@Stateless
public class GetAgreementPeriodFromYearPubImpl implements GetAgreementPeriodFromYearPub {

	/** 年度から集計期間を取得 */
	@Inject
	private GetAgreementPeriodFromYear getAgreementPeriod;
	
	/** 年度から集計期間を取得 */
	@Override
	public Optional<DatePeriod> algorithm(Year year, Closure closure) {
		return this.getAgreementPeriod.algorithm(year, closure);
	}
}
