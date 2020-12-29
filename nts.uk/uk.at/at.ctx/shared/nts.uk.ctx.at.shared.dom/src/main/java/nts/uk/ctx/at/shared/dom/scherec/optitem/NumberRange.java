/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class NumberRange.
 */
// 金額範囲
// 事前条件 : 上限値≧下限値

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NumberRange extends DomainObject implements RangeGetter {

    // 日別実績の回数範囲
	private Optional<DailyTimesRange> dailyTimesRange;
	
	// 月別実績の回数範囲
	private Optional<MonthlyTimesRange> monthlyTimesRange;

	public Optional<BigDecimal> getUpper(PerformanceAtr performanceAtr) {
		if (performanceAtr == PerformanceAtr.DAILY_PERFORMANCE) {
			return dailyTimesRange.flatMap(c -> c.getUpperLimit()).map(c -> c.v());
		}
		
		return monthlyTimesRange.flatMap(c -> c.getUpperLimit()).map(c -> c.v());
	}
	
	public Optional<BigDecimal> getLower(PerformanceAtr performanceAtr) {
		if (performanceAtr == PerformanceAtr.DAILY_PERFORMANCE) {
			return dailyTimesRange.flatMap(c -> c.getLowerLimit()).map(c -> c.v());
		}
		
		return monthlyTimesRange.flatMap(c -> c.getLowerLimit()).map(c -> c.v());
	}
}
