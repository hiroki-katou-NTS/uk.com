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
 * The Class AmountRange.
 */
// 金額範囲
// 事前条件 : 上限値≧下限値
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AmountRange extends DomainObject {

    // 日別実績の金額範囲
	private Optional<DailyAmountRange> dailyAmountRange;

	// 月別実績の金額範囲
	private Optional<MonthlyAmountRange> monthlyAmountRange;

}
