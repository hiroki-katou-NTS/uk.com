/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.optitem.calculation;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.optitem.calculation.RoundingGetMemento;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRounding;
import nts.uk.ctx.at.shared.dom.common.numberrounding.NumberRounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Class RoundingDto.
 */
@Getter
@Setter
public class RoundingDto implements RoundingGetMemento {

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
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.RoundingGetMemento#
	 * getNumberRounding()
	 */
	@Override
	public NumberRounding getNumberRounding() {
		return new NumberRounding(
				EnumAdaptor.valueOf(this.numberUnit, nts.uk.ctx.at.shared.dom.common.numberrounding.Unit.class),
				EnumAdaptor.valueOf(this.numberRounding,
						nts.uk.ctx.at.shared.dom.common.numberrounding.Rounding.class));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.RoundingGetMemento#
	 * getTimeRoundingSetting()
	 */
	@Override
	public TimeRoundingSetting getTimeRoundingSetting() {
		return new TimeRoundingSetting(
				EnumAdaptor.valueOf(this.timeUnit, nts.uk.ctx.at.shared.dom.common.timerounding.Unit.class),
				EnumAdaptor.valueOf(this.timeRounding, nts.uk.ctx.at.shared.dom.common.timerounding.Rounding.class));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.RoundingGetMemento#
	 * getAmountRounding()
	 */
	@Override
	public AmountRounding getAmountRounding() {
		return new AmountRounding(
				EnumAdaptor.valueOf(this.amountUnit, nts.uk.ctx.at.shared.dom.common.amountrounding.Unit.class),
				EnumAdaptor.valueOf(this.amountRounding, nts.uk.ctx.at.shared.dom.common.amountrounding.Rounding.class));
	}
}
