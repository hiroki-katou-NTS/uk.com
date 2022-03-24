/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;
import nts.arc.time.GeneralDate;

/**
 * The Class RetentionYearsAmount.積立年休保持年数
 */
@IntegerMaxValue(99)
@IntegerMinValue(1)
public class RetentionYearsAmount extends IntegerPrimitiveValue<RetentionYearsAmount> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new retention years amount.
	 *
	 * @param rawValue the raw value
	 */
	public RetentionYearsAmount(Integer rawValue) {
		super(rawValue);
	}
	
	/**
	 * 付与日から期限日を計算
	 * @param grantDate
	 * @return
	 */
	public GeneralDate calcDeadlineByGrantDate(GeneralDate grantDate){
		return grantDate.addYears(this.v()).addDays(-1);
	}

}
