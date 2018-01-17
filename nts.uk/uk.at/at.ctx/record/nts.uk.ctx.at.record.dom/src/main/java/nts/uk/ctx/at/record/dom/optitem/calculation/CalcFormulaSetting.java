/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem.calculation;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class CalcFormulaSetting.
 */
// 任意項目計算式設定
@Getter
public class CalcFormulaSetting extends DomainObject {

	/** The CalculatoinAtr. */
	//計算区分
	private CalculationAtr calculationAtr;
	
	// ===================== Optional ======================= //
	/** The formula setting. */
	// 計算式設定
	private FormulaSetting formulaSetting;

	/** The item selection. */
	// 計算項目選択
	private ItemSelection itemSelection;

	/**
	 * Instantiates a new calc formula setting.
	 *
	 * @param memento the memento
	 */
	public CalcFormulaSetting(FormulaSettingGetMemento memento) {
		this.formulaSetting = new FormulaSetting(memento);
	}

	/**
	 * Instantiates a new calc formula setting.
	 *
	 * @param memento the memento
	 */
	public CalcFormulaSetting(ItemSelectionGetMemento memento) {
		this.itemSelection = new ItemSelection(memento);
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FormulaSettingSetMemento memento) {
		memento.setMinusSegment(this.formulaSetting.getMinusSegment());
		memento.setOperatorAtr(this.formulaSetting.getOperator());
		memento.setLeftItem(this.formulaSetting.getLeftItem());
		memento.setRightItem(this.formulaSetting.getRightItem());
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ItemSelectionSetMemento memento) {
		memento.setMinusSegment(this.itemSelection.getMinusSegment());
		memento.setListSelectedAttendanceItem(this.itemSelection.getSelectedAttendanceItems());
	}

}
