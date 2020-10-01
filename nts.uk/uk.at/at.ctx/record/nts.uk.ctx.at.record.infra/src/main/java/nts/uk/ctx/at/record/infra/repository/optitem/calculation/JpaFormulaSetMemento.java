/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

import java.util.ArrayList;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtFormulaRounding;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtFormulaRoundingPK;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtOptItemFormula;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtOptItemFormulaPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNo;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.CalcFormulaSetting;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.CalculationAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaName;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.Rounding;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.Symbol;

/**
 * The Class JpaFormulaSetMemento.
 */
@Getter
public class JpaFormulaSetMemento implements FormulaSetMemento {

	/** The entity. */
	private KrcmtOptItemFormula entity;

	/**
	 * Instantiates a new jpa formula set memento.
	 */
	public JpaFormulaSetMemento() {
		this.entity = new KrcmtOptItemFormula(new KrcmtOptItemFormulaPK());
		this.entity.setKrcmtFormulaRoundings(new ArrayList<>());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId cId) {
		this.entity.getKrcmtOptItemFormulaPK().setCid(cId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setFormulaId(nts.uk.ctx.at.record.dom.optitem.calculation.FormulaId)
	 */
	@Override
	public void setFormulaId(FormulaId id) {
		this.entity.getKrcmtOptItemFormulaPK().setFormulaId(id.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setOptionalItemNo(nts.uk.ctx.at.record.dom.optitem.OptionalItemNo)
	 */
	@Override
	public void setOptionalItemNo(OptionalItemNo optItemNo) {
		this.entity.getKrcmtOptItemFormulaPK().setOptionalItemNo(optItemNo.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setFormulaName(nts.uk.ctx.at.record.dom.optitem.calculation.FormulaName)
	 */
	@Override
	public void setFormulaName(FormulaName name) {
		this.entity.setFormulaName(name.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setCalcFormulaSetting(nts.uk.ctx.at.record.dom.optitem.calculation.
	 * CalcFormulaSetting)
	 */
	@Override
	public void setCalcFormulaSetting(CalcFormulaSetting setting) {

		if (this.entity.getCalcAtr() == CalculationAtr.ITEM_SELECTION.value) {
			// Save to memento
			JpaItemSelectionSetMemento memento = new JpaItemSelectionSetMemento(this.entity.getKrcmtOptItemFormulaPK());
			setting.getItemSelection().get().saveToMemento(memento);

			// Set to entity.
			this.entity.setKrcmtCalcItemSelections(memento.getItemSelections());

		} else {
			// Save to memento
			JpaFormulaSettingSetMemento memento = new JpaFormulaSettingSetMemento(
					this.entity.getKrcmtOptItemFormulaPK());
			setting.getFormulaSetting().get().saveToMemento(memento);

			// Set to entity.
			this.entity.setKrcmtFormulaSetting(memento.getSetting());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setCalcFormulaAtr(nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr)
	 */
	@Override
	public void setFormulaAtr(OptionalItemAtr atr) {
		this.entity.setFormulaAtr(atr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#setSymbol(
	 * nts.uk.ctx.at.record.dom.optitem.calculation.Symbol)
	 */
	@Override
	public void setSymbol(Symbol symbol) {
		this.entity.setSymbol(symbol.v());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setMonthlyRounding(nts.uk.ctx.at.record.dom.optitem.calculation.Rounding)
	 */
	@Override
	public void setMonthlyRounding(Optional<Rounding> rounding) {

		KrcmtFormulaRounding monthly = new KrcmtFormulaRounding(this.entity.getKrcmtOptItemFormulaPK(),
				KrcmtFormulaRoundingPK.MONTHLY_ATR);

		// save to memento
		rounding.ifPresent(item -> item.saveToMemento(new JpaRoundingSetMemento(monthly)));

		// save to entity.
		this.entity.getKrcmtFormulaRoundings().add(monthly);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setDailyRounding(nts.uk.ctx.at.record.dom.optitem.calculation.Rounding)
	 */
	@Override
	public void setDailyRounding(Optional<Rounding> rounding) {

		KrcmtFormulaRounding daily = new KrcmtFormulaRounding(this.entity.getKrcmtOptItemFormulaPK(),
				KrcmtFormulaRoundingPK.DAILY_ATR);

		// save to memento
		rounding.ifPresent(item -> item.saveToMemento(new JpaRoundingSetMemento(daily)));

		// save to entity.
		this.entity.getKrcmtFormulaRoundings().add(daily);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setCalculationAtr(nts.uk.ctx.at.record.dom.optitem.calculation.
	 * CalculationAtr)
	 */
	@Override
	public void setCalcAtr(CalculationAtr calcAtr) {
		this.entity.setCalcAtr(calcAtr.value);
	}

}
