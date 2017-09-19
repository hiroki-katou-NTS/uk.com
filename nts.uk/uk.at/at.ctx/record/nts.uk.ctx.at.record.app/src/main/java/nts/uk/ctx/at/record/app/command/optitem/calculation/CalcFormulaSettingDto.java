/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.optitem.calculation;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalcFormulaSettingGetMemento;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalculationAtr;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetting;
import nts.uk.ctx.at.record.dom.optitem.calculation.ItemSelection;

/**
 * The Class CalcFormulaSettingDto.
 */
@Setter
@Getter
public class CalcFormulaSettingDto implements CalcFormulaSettingGetMemento {

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
	 * Gets the calculation atr.
	 *
	 * @return the calculation atr
	 */
	@Override
	public CalculationAtr getCalculationAtr() {
		return EnumAdaptor.valueOf(this.calcAtr, CalculationAtr.class);
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
}
