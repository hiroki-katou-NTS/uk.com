/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtFormulaRounding;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.numberrounding.NumberRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.RoundingSetMemento;

/**
 * The Class JpaRoundingSetMemento.
 */
public class JpaRoundingSetMemento implements RoundingSetMemento {

	/** The rounding. */
	private KrcmtFormulaRounding rounding;

	/**
	 * Instantiates a new jpa rounding set memento.
	 *
	 * @param rd the rd
	 */
	public JpaRoundingSetMemento(KrcmtFormulaRounding rd) {
		this.rounding = rd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.RoundingSetMemento#
	 * setNumberRounding(nts.uk.ctx.at.shared.dom.common.numberrounding.
	 * NumberRounding)
	 */
	@Override
	public void setNumberRounding(NumberRoundingSetting rounding) {
		this.rounding.setNumberRounding(rounding.getRounding().value);
		this.rounding.setNumberRoundingUnit(rounding.getUnit().value);
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
		this.rounding.setTimeRounding(rounding.getRounding().value);
		this.rounding.setTimeRoundingUnit(rounding.getRoundingTime().value);
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
		this.rounding.setAmountRounding(rounding.getRounding().value);
		this.rounding.setAmountRoundingUnit(rounding.getUnit().value);
	}

}
