/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.calculation;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.numberrounding.NumberRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.RoundingSetMemento;

/**
 * The Class RoundingDto.
 */
@Getter
@Setter
public class RoundingDto implements RoundingSetMemento {

	/** The number rounding. */
	private int numberRounding;

	/** The number unit. */
	private int numberUnit;

	/** The time rounding. */
	private int timeRounding;

	/** The time unit. */
	private int timeUnit;

	/** The amount rounding. */
	private int amountRounding;

	/** The amount unit. */
	private int amountUnit;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.RoundingSetMemento#
	 * setNumberRounding(nts.uk.ctx.at.shared.dom.common.numberrounding.
	 * NumberRounding)
	 */
	@Override
	public void setNumberRounding(NumberRoundingSetting rounding) {
		this.numberRounding = rounding.getRounding().value;
		this.numberUnit = rounding.getUnit().value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.RoundingSetMemento#
	 * setTimeRoundingSetting(nts.uk.ctx.at.shared.dom.common.timerounding.
	 * TimeRoundingSetting)
	 */
	@Override
	public void setTimeRoundingSetting(TimeRoundingSetting rounding) {
		this.timeRounding = rounding.getRounding().value;
		this.timeUnit = rounding.getRoundingTime().value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.RoundingSetMemento#
	 * setAmountRounding(nts.uk.ctx.at.shared.dom.common.amountrounding.
	 * AmountRounding)
	 */
	@Override
	public void setAmountRounding(AmountRoundingSetting rounding) {
		this.amountRounding = rounding.getRounding().value;
		this.amountUnit = rounding.getUnit().value;
	}
}
