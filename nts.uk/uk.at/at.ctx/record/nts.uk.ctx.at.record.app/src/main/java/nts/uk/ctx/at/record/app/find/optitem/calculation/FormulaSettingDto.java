/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.calculation;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSettingGetMemento;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSettingItem;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSettingSetMemento;
import nts.uk.ctx.at.record.dom.optitem.calculation.MinusSegment;
import nts.uk.ctx.at.record.dom.optitem.calculation.OperatorAtr;

/**
 * The Class FormulaSettingDto.
 */
public class FormulaSettingDto implements FormulaSettingGetMemento, FormulaSettingSetMemento {

	/** The minus segment. */
	// マイナス区分
	private int minusSegment;

	/** The operator. */
	// 演算子
	private int operator;

	/** The formula setting items. */
	// 計算式設定項目
	private List<FormulaSettingItemDto> formulaSettingItems;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSettingGetMemento#
	 * getMinusSegment()
	 */
	@Override
	public MinusSegment getMinusSegment() {
		return EnumAdaptor.valueOf(this.minusSegment, MinusSegment.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSettingGetMemento#
	 * getOperatorAtr()
	 */
	@Override
	public OperatorAtr getOperatorAtr() {
		return EnumAdaptor.valueOf(this.operator, OperatorAtr.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSettingGetMemento#
	 * getFormulaSettingItems()
	 */
	@Override
	public List<FormulaSettingItem> getFormulaSettingItems() {
		return this.formulaSettingItems.stream().map(item -> new FormulaSettingItem(item)).collect(Collectors.toList());
	}

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
	 * setFormulaSettingItems(java.util.List)
	 */
	@Override
	public void setFormulaSettingItems(List<FormulaSettingItem> listItem) {
		this.formulaSettingItems = listItem.stream().map(item -> {
			FormulaSettingItemDto dto = new FormulaSettingItemDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

}
