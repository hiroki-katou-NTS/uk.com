/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem;

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
public class NumberRange extends DomainObject {

    // 日別実績の回数範囲
	private Optional<DailyTimesRange> dailyTimesRange;
	
	// 月別実績の回数範囲
	private Optional<MonthlyTimesRange> monthlyTimesRange;

}
