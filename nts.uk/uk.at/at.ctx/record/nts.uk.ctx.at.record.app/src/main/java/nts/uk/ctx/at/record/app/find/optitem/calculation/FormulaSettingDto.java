/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.calculation;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaSettingItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaSettingSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.MinusSegment;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.OperatorAtr;

/**
 * The Class FormulaSettingDto.
 */
@Getter
@Setter
public class FormulaSettingDto implements FormulaSettingSetMemento {

	/** The minus segment. */
	private int minusSegment;

	/** The operator. */
	private int operator;

	/** The left item. */
	private FormulaSettingItemDto leftItem;

	/** The right item. */
	private FormulaSettingItemDto rightItem;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSettingSetMemento#
	 * setMinusSegment(nts.uk.ctx.at.record.dom.optitem.calculation.
	 * MinusSegment)
	 */
	@Override
	public void setMinusSegment(MinusSegment segment) {
		this.minusSegment = segment.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSettingSetMemento#
	 * setOperatorAtr(nts.uk.ctx.at.record.dom.optitem.calculation.OperatorAtr)
	 */
	@Override
	public void setOperatorAtr(OperatorAtr operator) {
		this.operator = operator.value;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSettingSetMemento#
	 * setLeftItem(nts.uk.ctx.at.record.dom.optitem.calculation.
	 * FormulaSettingItem)
	 */
	@Override
	public void setLeftItem(FormulaSettingItem item) {
		this.leftItem = new FormulaSettingItemDto();
		item.saveToMemento(this.leftItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSettingSetMemento#
	 * setRightItem(nts.uk.ctx.at.record.dom.optitem.calculation.
	 * FormulaSettingItem)
	 */
	@Override
	public void setRightItem(FormulaSettingItem item) {
		this.rightItem = new FormulaSettingItemDto();
		item.saveToMemento(this.rightItem);
	}

}
