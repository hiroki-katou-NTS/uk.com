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
 * The Class JpaFormulaDispOrderSetMemento.
 */
public class JpaFormulaSetMemento implements FormulaSetMemento {

	/** The type value. */
	private KrcmtOptItemFormula typeValue;

	/**
	 * Instantiates a new jpa formula disp order set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaFormulaSetMemento(KrcmtOptItemFormula typeValue) {
		KrcmtOptItemFormulaPK krcmtOptItemFormulaPK = typeValue.getKrcmtOptItemFormulaPK();

		// Check PK exist
		if (krcmtOptItemFormulaPK == null) {
			krcmtOptItemFormulaPK = new KrcmtOptItemFormulaPK();
		}

		typeValue.setKrcmtOptItemFormulaPK(krcmtOptItemFormulaPK);

		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionSetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId comId) {
		KrcmtOptItemFormulaPK krcmtOptItemFormulaPK = typeValue.getKrcmtOptItemFormulaPK();
		krcmtOptItemFormulaPK.setCid(comId.v());
		this.typeValue.setKrcmtOptItemFormulaPK(krcmtOptItemFormulaPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionSetMemento#
	 * setOptionalItemNo(nts.uk.ctx.at.record.dom.optitem.OptionalItemNo)
	 */
	@Override
	public void setOptionalItemNo(OptionalItemNo optNo) {
		KrcmtOptItemFormulaPK krcmtOptItemFormulaPK = typeValue.getKrcmtOptItemFormulaPK();
		krcmtOptItemFormulaPK.setOptionalItemNo(optNo.v());
		this.typeValue.setKrcmtOptItemFormulaPK(krcmtOptItemFormulaPK);
	}

	@Override
	public void setFormulaId(FormulaId id) {
		KrcmtOptItemFormulaPK krcmtOptItemFormulaPK = typeValue.getKrcmtOptItemFormulaPK();
		krcmtOptItemFormulaPK.setFormulaId(id.v());
		this.typeValue.setKrcmtOptItemFormulaPK(krcmtOptItemFormulaPK);
	}

	@Override
	public void setFormulaName(FormulaName name) {
		this.typeValue.setFormulaName(name.v());
	}

	@Override
	public void setCalcFormulaSetting(CalcFormulaSetting setting) {
		// this.typeValue.set
	}

	@Override
	public void setCalcFormulaAtr(OptionalItemAtr atr) {
		// TODO: check lại
		this.typeValue.setFormulaAtr(atr.value);
	}

	@Override
	public void setSymbol(Symbol symbol) {
		this.typeValue.setSymbol(symbol.v());
	}

	@Override
	public void setMonthlyRounding(Rounding rounding) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setDailyRounding(Rounding rounding) {
		// TODO Auto-generated method stub

	}

}
