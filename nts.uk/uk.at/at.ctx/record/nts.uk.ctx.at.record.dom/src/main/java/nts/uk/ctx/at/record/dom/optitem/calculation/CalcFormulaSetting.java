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

	/** The calculation atr. */
	// 計算区分
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
	public CalcFormulaSetting(CalcFormulaSettingGetMemento memento) {
		this.calculationAtr = memento.getCalculationAtr();
		this.formulaSetting = memento.getFormulaSetting();
		this.itemSelection = memento.getItemSelection();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(CalcFormulaSettingSetMemento memento) {
		memento.setCalculationAtr(this.calculationAtr);
		memento.setFormulaSetting(this.formulaSetting);
		memento.setItemSelection(this.itemSelection);
	}
}
