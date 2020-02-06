package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset;

import java.util.Optional;

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
	private Optional<YearPeriod> yearPeriod;

	public ValidityPeriodSet(int periodAtr, Integer startYear, Integer endYear) {
		super();
		this.periodAtr = EnumAdaptor.valueOf(periodAtr, PeriodAtr.class);
		this.yearPeriod = periodAtr == PeriodAtr.NOT_SETUP.value ? Optional.empty()
				: Optional.of(new YearPeriod(new Year(startYear), new Year(endYear)));
	}
}
