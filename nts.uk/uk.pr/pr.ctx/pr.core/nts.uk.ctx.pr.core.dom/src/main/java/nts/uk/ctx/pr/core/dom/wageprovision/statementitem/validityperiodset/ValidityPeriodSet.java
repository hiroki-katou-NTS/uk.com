package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.calendar.Year;
import nts.uk.shr.com.time.calendar.period.YearPeriod;

/**
 * 
 * @author thanh.tq 有効期間設定
 *
 */

@Getter
public class ValidityPeriodSet extends DomainObject {
	/**
	 * 有効期間設定区分
	 */
	private PeriodAtr periodAtr;

	/**
	 * 年期間
	 */
	private YearPeriod yearPeriod;

	public ValidityPeriodSet(int periodAtr, int endYear, int startYear) {
		super();
		this.periodAtr = EnumAdaptor.valueOf(periodAtr, PeriodAtr.class);
		this.yearPeriod = new YearPeriod(new Year(endYear), new Year(startYear));
	}
}
