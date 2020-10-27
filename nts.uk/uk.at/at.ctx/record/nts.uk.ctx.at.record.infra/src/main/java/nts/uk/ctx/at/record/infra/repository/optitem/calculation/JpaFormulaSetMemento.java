/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

import java.util.ArrayList;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtAnyfRound;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtAnyfRoundPK;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtAnyf;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtAnyfPK;
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
	private KrcmtAnyf entity;

	/**
	 * Instantiates a new jpa formula set memento.
	 */
	public JpaFormulaSetMemento() {
		this.entity = new KrcmtAnyf(new KrcmtAnyfPK());
		this.entity.setKrcmtAnyfRounds(new ArrayList<>());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId cId) {
		this.entity.getKrcmtAnyfPK().setCid(cId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setFormulaId(nts.uk.ctx.at.record.dom.optitem.calculation.FormulaId)
	 */
	@Override
	public void setFormulaId(FormulaId id) {
		this.entity.getKrcmtAnyfPK().setFormulaId(id.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setOptionalItemNo(nts.uk.ctx.at.record.dom.optitem.OptionalItemNo)
	 */
	@Override
	public void setOptionalItemNo(OptionalItemNo optItemNo) {
		this.entity.getKrcmtAnyfPK().setOptionalItemNo(optItemNo.v());
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
			JpaItemSelectionSetMemento memento = new JpaItemSelectionSetMemento(this.entity.getKrcmtAnyfPK());
			setting.getItemSelection().get().saveToMemento(memento);

			// Set to entity.
			this.entity.setKrcmtAnyfItemSelects(memento.getItemSelections());

		} else {
			// Save to memento
			JpaFormulaSettingSetMemento memento = new JpaFormulaSettingSetMemento(
					this.entity.getKrcmtAnyfPK());
			setting.getFormulaSetting().get().saveToMemento(memento);

			// Set to entity.
			this.entity.setKrcmtAnyfDetail(memento.getSetting());
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

		KrcmtAnyfRound monthly = new KrcmtAnyfRound(this.entity.getKrcmtAnyfPK(),
				KrcmtAnyfRoundPK.MONTHLY_ATR);

		// save to memento
		rounding.ifPresent(item -> item.saveToMemento(new JpaRoundingSetMemento(monthly)));

		// save to entity.
		this.entity.getKrcmtAnyfRounds().add(monthly);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setDailyRounding(nts.uk.ctx.at.record.dom.optitem.calculation.Rounding)
	 */
	@Override
	public void setDailyRounding(Optional<Rounding> rounding) {

		KrcmtAnyfRound daily = new KrcmtAnyfRound(this.entity.getKrcmtAnyfPK(),
				KrcmtAnyfRoundPK.DAILY_ATR);

		// save to memento
		rounding.ifPresent(item -> item.saveToMemento(new JpaRoundingSetMemento(daily)));

		// save to entity.
		this.entity.getKrcmtAnyfRounds().add(daily);

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
