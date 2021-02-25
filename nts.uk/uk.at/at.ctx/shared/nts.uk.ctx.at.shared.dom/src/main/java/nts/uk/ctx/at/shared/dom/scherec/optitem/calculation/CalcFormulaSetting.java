/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation;

import java.util.Optional;

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
	private CalculationAtr calculationAtr = CalculationAtr.ITEM_SELECTION;
	
	// ===================== Optional ======================= //
	/** The formula setting. */
	// 計算式設定
	private Optional<FormulaSetting> formulaSetting = Optional.empty();

	/** The item selection. */
	// 計算項目選択
	private Optional<ItemSelection> itemSelection = Optional.empty();

	/**
	 * Instantiates a new calc formula setting.
	 *
	 * @param memento the memento
	 */
	public CalcFormulaSetting(FormulaSettingGetMemento memento) {
		this.formulaSetting = Optional.of(new FormulaSetting(memento));
	}

	/**
	 * Instantiates a new calc formula setting.
	 *
	 * @param memento the memento
	 */
	public CalcFormulaSetting(ItemSelectionGetMemento memento) {
		this.itemSelection = Optional.of(new ItemSelection(memento));
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FormulaSettingSetMemento memento) {
		if(this.formulaSetting.isPresent()) {
			memento.setMinusSegment(this.formulaSetting.get().getMinusSegment());
			memento.setOperatorAtr(this.formulaSetting.get().getOperator());
			memento.setLeftItem(this.formulaSetting.get().getLeftItem());
			memento.setRightItem(this.formulaSetting.get().getRightItem());
		}
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ItemSelectionSetMemento memento) {
		if(this.itemSelection.isPresent()) {
			memento.setMinusSegment(this.itemSelection.get().getMinusSegment());
			memento.setListSelectedAttendanceItem(this.itemSelection.get().getSelectedAttendanceItems());
		}
	}

}
