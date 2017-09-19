/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.calculation;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSettingItem;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSettingSetMemento;
import nts.uk.ctx.at.record.dom.optitem.calculation.MinusSegment;
import nts.uk.ctx.at.record.dom.optitem.calculation.OperatorAtr;

/**
 * The Class FormulaSettingDto.
 */
@Getter
@Setter
public class FormulaSettingDto implements FormulaSettingSetMemento {

	/** The minus segment. */
	// マイナス区分
	private int minusSegment;

	/** The operator. */
	// 演算子
	private int operator;

	/** The formula setting items. */
	// 計算式設定項目
	private List<FormulaSettingItemDto> settingItems;

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
		this.settingItems = listItem.stream().map(item -> {
			FormulaSettingItemDto dto = new FormulaSettingItemDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

}
