/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalcFormulaSetting;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaId;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaName;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento;
import nts.uk.ctx.at.record.dom.optitem.calculation.Rounding;
import nts.uk.ctx.at.record.dom.optitem.calculation.Symbol;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtOptItemFormula;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtOptItemFormulaPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaFormulaSetMemento.
 */
public class JpaFormulaSetMemento implements FormulaSetMemento{
	
	/** The entity. */
	private KrcmtOptItemFormula entity;
	
	/**
	 * Instantiates a new jpa formula set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFormulaSetMemento(KrcmtOptItemFormula entity) {
		if (entity.getKrcmtOptItemFormulaPK() == null) {
			entity.setKrcmtOptItemFormulaPK(new KrcmtOptItemFormulaPK());
		}
		this.entity = entity;
	}

	/**
	 * Sets the company id.
	 *
	 * @param cId the new company id
	 */
	@Override
	public void setCompanyId(CompanyId cId) {
		this.entity.getKrcmtOptItemFormulaPK().setCid(cId.v());
	}

	/**
	 * Sets the formula id.
	 *
	 * @param id the new formula id
	 */
	@Override
	public void setFormulaId(FormulaId id) {
		this.entity.getKrcmtOptItemFormulaPK().setFormulaId(id.v());
	}

	/**
	 * Sets the optional item no.
	 *
	 * @param optItemNo the new optional item no
	 */
	@Override
	public void setOptionalItemNo(OptionalItemNo optItemNo) {
		this.entity.getKrcmtOptItemFormulaPK().setOptionalItemNo(optItemNo.v());
	}

	/**
	 * Sets the formula name.
	 *
	 * @param name the new formula name
	 */
	@Override
	public void setFormulaName(FormulaName name) {
		this.entity.setFormulaName(name.v());
	}

	/**
	 * Sets the calc formula setting.
	 *
	 * @param setting the new calc formula setting
	 */
	@Override
	public void setCalcFormulaSetting(CalcFormulaSetting setting) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Sets the calc formula atr.
	 *
	 * @param atr the new calc formula atr
	 */
	@Override
	public void setCalcFormulaAtr(OptionalItemAtr atr) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Sets the symbol.
	 *
	 * @param symbol the new symbol
	 */
	@Override
	public void setSymbol(Symbol symbol) {
		this.entity.setSymbol(symbol.v());
		
	}

	/**
	 * Sets the monthly rounding.
	 *
	 * @param rounding the new monthly rounding
	 */
	@Override
	public void setMonthlyRounding(Rounding rounding) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Sets the daily rounding.
	 *
	 * @param rounding the new daily rounding
	 */
	@Override
	public void setDailyRounding(Rounding rounding) {
		// TODO Auto-generated method stub
		
	}

}
