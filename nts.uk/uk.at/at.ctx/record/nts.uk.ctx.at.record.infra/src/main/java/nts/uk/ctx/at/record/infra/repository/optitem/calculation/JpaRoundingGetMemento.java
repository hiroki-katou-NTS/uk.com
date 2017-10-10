/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

import nts.uk.ctx.at.record.dom.optitem.calculation.RoundingGetMemento;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtFormulaRounding;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRounding;
import nts.uk.ctx.at.shared.dom.common.numberrounding.NumberRounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Class JpaRoundingGetMemento.
 */
public class JpaRoundingGetMemento implements RoundingGetMemento {

	/** The entity. */
	private KrcmtFormulaRounding entity;

	/**
	 * Instantiates a new jpa rounding get memento.
	 *
	 * @param entity the entity
	 */
	public JpaRoundingGetMemento(KrcmtFormulaRounding entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.RoundingGetMemento#
	 * getNumberRounding()
	 */
	@Override
	public NumberRounding getNumberRounding() {
		this.entity.getNumberRounding();
		nts.uk.ctx.at.shared.dom.common.numberrounding.Unit unit = nts.uk.ctx.at.shared.dom.common.numberrounding.Unit
				.valueOf(this.entity.getNumberRoundingUnit());
		nts.uk.ctx.at.shared.dom.common.numberrounding.Rounding rounding = nts.uk.ctx.at.shared.dom.common.numberrounding.Rounding
				.valueOf(this.entity.getNumberRoundingUnit());

		return new NumberRounding(unit, rounding);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.RoundingGetMemento#
	 * getTimeRoundingSetting()
	 */
	@Override
	public TimeRoundingSetting getTimeRoundingSetting() {
		nts.uk.ctx.at.shared.dom.common.timerounding.Unit unit = nts.uk.ctx.at.shared.dom.common.timerounding.Unit
				.valueOf(this.entity.getTimeRoundingUnit());
		nts.uk.ctx.at.shared.dom.common.timerounding.Rounding rounding = nts.uk.ctx.at.shared.dom.common.timerounding.Rounding
				.valueOf(this.entity.getTimeRounding());
		return new TimeRoundingSetting(unit, rounding);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.RoundingGetMemento#
	 * getAmountRounding()
	 */
	@Override
	public AmountRounding getAmountRounding() {
		nts.uk.ctx.at.shared.dom.common.amountrounding.Unit unit = nts.uk.ctx.at.shared.dom.common.amountrounding.Unit
				.valueOf(this.entity.getTimeRoundingUnit());
		nts.uk.ctx.at.shared.dom.common.amountrounding.Rounding rounding = nts.uk.ctx.at.shared.dom.common.amountrounding.Rounding
				.valueOf(this.entity.getTimeRounding());
		return new AmountRounding(unit, rounding);
	}

}
