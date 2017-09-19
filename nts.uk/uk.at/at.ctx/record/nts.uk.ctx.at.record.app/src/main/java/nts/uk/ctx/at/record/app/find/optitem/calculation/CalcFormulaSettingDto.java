/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.calculation;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalcFormulaSettingSetMemento;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalculationAtr;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetting;
import nts.uk.ctx.at.record.dom.optitem.calculation.ItemSelection;

/**
 * The Class CalcFormulaSettingDto.
 */
@Setter
@Getter
public class CalcFormulaSettingDto implements CalcFormulaSettingSetMemento {

	/** The calculation atr. */
	// 計算区分
	private int calcAtr;

	// ===================== Optional ======================= //
	/** The formula setting. */
	// 計算式設定
	private FormulaSettingDto formulaSetting;

	/** The item selection. */
	// 計算項目選択
	private ItemSelectionDto itemSelection;

	/**
	 * Sets the calculation atr.
	 *
	 * @param calcAtr the new calculation atr
	 */
	@Override
	public void setCalculationAtr(CalculationAtr calcAtr) {
		this.calcAtr = calcAtr.value;
	}

	/**
	 * Sets the formula setting.
	 *
	 * @param setting the new formula setting
	 */
	@Override
	public void setFormulaSetting(FormulaSetting setting) {
		setting.saveToMemento(this.formulaSetting);
	}

	/**
	 * Sets the item selection.
	 *
	 * @param selection the new item selection
	 */
	@Override
	public void setItemSelection(ItemSelection selection) {
		selection.saveToMemento(this.itemSelection);
	}
}
