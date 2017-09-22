/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

import java.util.List;

import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalcFormulaSetting;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalculationAtr;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaId;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaName;
import nts.uk.ctx.at.record.dom.optitem.calculation.Rounding;
import nts.uk.ctx.at.record.dom.optitem.calculation.Symbol;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtCalcItemSelection;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtCalcItemSelectionPK;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtFormulaSetting;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtOptItemFormula;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtOptItemFormulaPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaFormulaGetMemento.
 */
public class JpaFormulaGetMemento implements FormulaGetMemento {

	/** The entity. */
	private KrcmtOptItemFormula entity;
	
	/** The setting. */
	private KrcmtFormulaSetting setting;
	
	/** The selection. */
	private List<KrcmtCalcItemSelection> selections;

	
	
	
	/**
	 * Instantiates a new jpa formula get memento.
	 *
	 * @param entity the entity
	 * @param setting the setting
	 * @param selections the selections
	 */
	public JpaFormulaGetMemento(KrcmtOptItemFormula entity, KrcmtFormulaSetting setting,
			List<KrcmtCalcItemSelection> selections) {
		if (entity.getKrcmtOptItemFormulaPK() == null) {
			entity.setKrcmtOptItemFormulaPK(new KrcmtOptItemFormulaPK());
		}
		selections.forEach(selection -> {
			if (selection.getKrcmtCalcItemSelectionPK() == null) {
				selection.setKrcmtCalcItemSelectionPK(new KrcmtCalcItemSelectionPK());
			}
		});
		this.entity = entity;
		this.setting = setting;
		this.selections = selections;
	}


	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKrcmtOptItemFormulaPK().getCid());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#getFormulaId()
	 */
	@Override
	public FormulaId getFormulaId() {
		return new FormulaId(this.entity.getKrcmtOptItemFormulaPK().getFormulaId());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#getOptionalItemNo()
	 */
	@Override
	public OptionalItemNo getOptionalItemNo() {
		return new OptionalItemNo(this.entity.getKrcmtOptItemFormulaPK().getOptionalItemNo());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#getFormulaName()
	 */
	@Override
	public FormulaName getFormulaName() {
		return new FormulaName(this.entity.getFormulaName());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#getCalcFormulaSetting()
	 */
	@Override
	public CalcFormulaSetting getCalcFormulaSetting() {
		if (this.entity.getCalcAtr() == CalculationAtr.FORMULA_SETTING.value) {
			return new CalcFormulaSetting(new JpaCalcFormulaSettingGetMemento(setting, selections,
					CalculationAtr.FORMULA_SETTING.value));
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#getCalcFormulaAtr()
	 */
	@Override
	public OptionalItemAtr getCalcFormulaAtr() {
		return OptionalItemAtr.valueOf(this.entity.getFormulaAtr());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#getSymbol()
	 */
	@Override
	public Symbol getSymbol() {
		return new Symbol(this.entity.getSymbol());
	}

	/**
	 * Gets the monthly rounding.
	 *
	 * @return the monthly rounding
	 */
	@Override
	public Rounding getMonthlyRounding() {
		return null;
	}

	/**
	 * Gets the daily rounding.
	 *
	 * @return the daily rounding
	 */
	@Override
	public Rounding getDailyRounding() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
