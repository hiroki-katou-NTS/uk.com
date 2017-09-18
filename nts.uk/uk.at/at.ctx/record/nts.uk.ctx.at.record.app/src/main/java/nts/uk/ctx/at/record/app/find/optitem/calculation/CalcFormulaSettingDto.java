/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.calculation;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalcFormulaSettingGetMemento;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalcFormulaSettingSetMemento;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalculationAtr;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetting;
import nts.uk.ctx.at.record.dom.optitem.calculation.ItemSelection;

/**
 * The Class CalcFormulaSettingDto.
 */
@Setter
@Getter
public class CalcFormulaSettingDto implements CalcFormulaSettingGetMemento, CalcFormulaSettingSetMemento {

	/** The calculation atr. */
	// 計算区分
	private int calculationAtr;

	// ===================== Optional ======================= //
	/** The formula setting. */
	// 計算式設定
	private FormulaSettingDto formulaSetting;

	/** The item selection. */
	// 計算項目選択
	private ItemSelectionDto itemSelection;

	/**
	 * Gets the calculation atr.
	 *
	 * @return the calculation atr
	 */
	@Override
	public CalculationAtr getCalculationAtr() {
		return EnumAdaptor.valueOf(this.calculationAtr, CalculationAtr.class);
	}

	/**
	 * Gets the formula setting.
	 *
	 * @return the formula setting
	 */
	@Override
	public FormulaSetting getFormulaSetting() {
		return new FormulaSetting(this.formulaSetting);
	}

	/**
	 * Gets the item selection.
	 *
	 * @return the item selection
	 */
	@Override
	public ItemSelection getItemSelection() {
		return new ItemSelection(this.itemSelection);
	}

	/**
	 * Sets the calculation atr.
	 *
	 * @param calcAtr the new calculation atr
	 */
	@Override
	public void setCalculationAtr(CalculationAtr calcAtr) {
		this.calculationAtr = calcAtr.value;
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
