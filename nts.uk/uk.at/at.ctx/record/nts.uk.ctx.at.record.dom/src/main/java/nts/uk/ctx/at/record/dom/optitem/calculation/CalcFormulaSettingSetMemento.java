/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem.calculation;

/**
 * The Interface CalcFormulaSettingSetMemento.
 */
public interface CalcFormulaSettingSetMemento {

	/**
	 * Sets the calculation atr.
	 *
	 * @param calcAtr the new calculation atr
	 */
	void setCalculationAtr(CalculationAtr calcAtr);

	/**
	 * Sets the formula setting.
	 *
	 * @param setting the new formula setting
	 */
	void setFormulaSetting(FormulaSetting setting);

	/**
	 * Sets the item selection.
	 *
	 * @param selection the new item selection
	 */
	void setItemSelection(ItemSelection  selection);
}
