/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.calculation;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.optitem.calculation.RoundingGetMemento;
import nts.uk.ctx.at.record.dom.optitem.calculation.RoundingSetMemento;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRounding;
import nts.uk.ctx.at.shared.dom.common.numberrounding.NumberRounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Class RoundingDto.
 */
@Getter
@Setter
public class RoundingDto implements RoundingGetMemento, RoundingSetMemento {

	/** The number rounding. */
	// 数値丸め
	private int numberRounding;

	/** The number unit. */
	private int numberUnit;

	/** The time rounding. */
	// 時間丸め
	private int timeRounding;

	/** The time unit. */
	private int timeUnit;

	/** The amount rounding. */
	// 金額丸め
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
				EnumAdaptor.valueOf(this.numberUnit, nts.uk.ctx.at.shared.dom.common.timerounding.Unit.class),
				EnumAdaptor.valueOf(this.numberUnit, nts.uk.ctx.at.shared.dom.common.timerounding.Rounding.class));
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
				EnumAdaptor.valueOf(this.numberUnit, nts.uk.ctx.at.shared.dom.common.amountrounding.Unit.class),
				EnumAdaptor.valueOf(this.numberUnit, nts.uk.ctx.at.shared.dom.common.amountrounding.Rounding.class));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.RoundingSetMemento#
	 * setNumberRounding(nts.uk.ctx.at.shared.dom.common.numberrounding.
	 * NumberRounding)
	 */
	@Override
	public void setNumberRounding(NumberRounding rounding) {
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
	public void setAmountRounding(AmountRounding rounding) {
		this.amountRounding = rounding.getRounding().value;
		this.amountUnit = rounding.getUnit().value;
	}
}
