/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.optitem.calculation;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRounding;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountUnit;
import nts.uk.ctx.at.shared.dom.common.numberrounding.NumberRounding;
import nts.uk.ctx.at.shared.dom.common.numberrounding.NumberRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.numberrounding.NumberUnit;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.RoundingGetMemento;

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
	public NumberRoundingSetting getNumberRounding() {
		return new NumberRoundingSetting(EnumAdaptor.valueOf(this.numberUnit, NumberUnit.class),
				EnumAdaptor.valueOf(this.numberRounding, NumberRounding.class));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.RoundingGetMemento#
	 * getTimeRoundingSetting()
	 */
	@Override
	public TimeRoundingSetting getTimeRoundingSetting() {
		return new TimeRoundingSetting(EnumAdaptor.valueOf(this.timeUnit, Unit.class),
				EnumAdaptor.valueOf(this.timeRounding, Rounding.class));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.RoundingGetMemento#
	 * getAmountRounding()
	 */
	@Override
	public AmountRoundingSetting getAmountRounding() {
		return new AmountRoundingSetting(EnumAdaptor.valueOf(this.amountUnit, AmountUnit.class),
				EnumAdaptor.valueOf(this.amountRounding, AmountRounding.class));
	}
}
