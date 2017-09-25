/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.calculation;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalcFormulaSetting;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaId;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaName;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento;
import nts.uk.ctx.at.record.dom.optitem.calculation.Rounding;
import nts.uk.ctx.at.record.dom.optitem.calculation.Symbol;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class FormulaDto.
 */
@Getter
@Setter
public class FormulaDto implements FormulaSetMemento {

	/** The formula id. */
	private String formulaId;

	/** The optional item no. */
	private String optionalItemNo;

	/** The symbol value. */
	private String symbolValue;

	/** The order no. */
	private int orderNo;

	/** The formula atr. */
	private int formulaAtr;

	/** The formula name. */
	private String formulaName;

	/** The formula setting. */
	private CalcFormulaSettingDto calcFormulaSetting;

	// ===================== Optional ======================= //
	/** The monthly rounding. */
	private RoundingDto monthlyRounding;

	/** The daily rounding. */
	private RoundingDto dailyRounding;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId cId) {
		// Not used.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setOptionalItemFormulaId(nts.uk.ctx.at.record.dom.optitem.calculation.
	 * FormulaId)
	 */
	@Override
	public void setFormulaId(FormulaId id) {
		this.formulaId = id.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setOptionalItemNo(nts.uk.ctx.at.record.dom.optitem.OptionalItemNo)
	 */
	@Override
	public void setOptionalItemNo(OptionalItemNo optItemNo) {
		this.optionalItemNo = optItemNo.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setOptionalItemFormulaName(nts.uk.ctx.at.record.dom.optitem.calculation.
	 * FormulaName)
	 */
	@Override
	public void setFormulaName(FormulaName name) {
		this.formulaName = name.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setOptionalItemFormulaSetting(nts.uk.ctx.at.record.dom.optitem.
	 * calculation.CalcFormulaSetting)
	 */
	@Override
	public void setCalcFormulaSetting(CalcFormulaSetting setting) {
		setting.saveToMemento(this.calcFormulaSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setCalculationFormulaAtr(nts.uk.ctx.at.record.dom.optitem.calculation.
	 * FormulaAtr)
	 */
	@Override
	public void setCalcFormulaAtr(OptionalItemAtr atr) {
		this.formulaAtr = atr.value;
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
		this.symbolValue = symbol.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setMonthlyRounding(nts.uk.ctx.at.record.dom.optitem.calculation.Rounding)
	 */
	@Override
	public void setMonthlyRounding(Rounding rounding) {
		rounding.saveToMemento(this.monthlyRounding);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setDailyRounding(nts.uk.ctx.at.record.dom.optitem.calculation.Rounding)
	 */
	@Override
	public void setDailyRounding(Rounding rounding) {
		rounding.saveToMemento(this.dailyRounding);
	}
}
