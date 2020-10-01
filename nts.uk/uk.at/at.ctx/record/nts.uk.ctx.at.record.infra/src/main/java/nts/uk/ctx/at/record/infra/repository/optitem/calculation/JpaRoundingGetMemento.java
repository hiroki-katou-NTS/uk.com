/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtFormulaRounding;
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
	public NumberRoundingSetting getNumberRounding() {
		this.entity.getNumberRounding();
		NumberUnit unit = NumberUnit.valueOf(this.entity.getNumberRoundingUnit());
		NumberRounding rounding = NumberRounding.valueOf(this.entity.getNumberRounding());
		return new NumberRoundingSetting(unit, rounding);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.RoundingGetMemento#
	 * getTimeRoundingSetting()
	 */
	@Override
	public TimeRoundingSetting getTimeRoundingSetting() {
		Unit unit = Unit.valueOf(this.entity.getTimeRoundingUnit());
		Rounding rounding = Rounding.valueOf(this.entity.getTimeRounding());
		return new TimeRoundingSetting(unit, rounding);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.RoundingGetMemento#
	 * getAmountRounding()
	 */
	@Override
	public AmountRoundingSetting getAmountRounding() {
		AmountUnit unit = AmountUnit.valueOf(this.entity.getAmountRoundingUnit());
		AmountRounding rounding = AmountRounding.valueOf(this.entity.getAmountRounding());
		return new AmountRoundingSetting(unit, rounding);
	}

}
