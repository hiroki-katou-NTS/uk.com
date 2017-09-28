/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

import java.util.List;

import nts.uk.ctx.at.record.dom.optitem.calculation.CalcFormulaSettingGetMemento;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalculationAtr;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetting;
import nts.uk.ctx.at.record.dom.optitem.calculation.ItemSelection;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtCalcItemSelection;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtCalcItemSelectionPK;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtFormulaSetting;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtFormulaSettingPK;

/**
 * The Class JpaCalcFormulaSettingGetMemento.
 */
public class JpaCalcFormulaSettingGetMemento implements CalcFormulaSettingGetMemento {
	
	/** The setting. */
	private KrcmtFormulaSetting setting;
	
	/** The selection. */
	private List<KrcmtCalcItemSelection> selections;
	
	/** The calc atr. */
	private int calcAtr;
	
	

	/**
	 * Instantiates a new jpa calc formula setting get memento.
	 *
	 * @param setting the setting
	 * @param selections the selections
	 * @param calcAtr the calc atr
	 */
	public JpaCalcFormulaSettingGetMemento(KrcmtFormulaSetting setting,
			List<KrcmtCalcItemSelection> selections, int calcAtr) {
		if (setting.getKrcmtFormulaSettingPK() == null) {
			setting.setKrcmtFormulaSettingPK(new KrcmtFormulaSettingPK());
		}
		selections.forEach(selection->{
			if(selection.getKrcmtCalcItemSelectionPK() == null){
				selection.setKrcmtCalcItemSelectionPK(new KrcmtCalcItemSelectionPK());
			}
		});
		this.setting = setting;
		this.selections = selections;
		this.calcAtr = calcAtr;
	}

	/**
	 * Gets the calculation atr.
	 *
	 * @return the calculation atr
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.CalcFormulaSettingGetMemento
	 * #getCalculationAtr()
	 */
	@Override
	public CalculationAtr getCalculationAtr() {
		return CalculationAtr.valueOf(this.calcAtr);
	}

	/**
	 * Gets the formula setting.
	 *
	 * @return the formula setting
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.CalcFormulaSettingGetMemento
	 * #getFormulaSetting()
	 */
	@Override
	public FormulaSetting getFormulaSetting() {
		if(CalculationAtr.FORMULA_SETTING.value == this.calcAtr){
			return new FormulaSetting(new JpaFormulaSettingGetMemento(setting));
		}
		return null;
	}

	/**
	 * Gets the item selection.
	 *
	 * @return the item selection
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.CalcFormulaSettingGetMemento
	 * #getItemSelection()
	 */
	@Override
	public ItemSelection getItemSelection() {
		if (CalculationAtr.ITEM_SELECTION.value == this.calcAtr) {
			return new ItemSelection(new JpaItemSelectionGetMemento(this.selections));
		}
		return null;
	}

}
