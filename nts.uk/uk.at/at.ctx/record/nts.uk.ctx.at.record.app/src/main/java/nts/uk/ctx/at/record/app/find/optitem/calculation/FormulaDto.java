/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.calculation;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
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
 * The Class FormulaDto.
 */
@Getter
@Setter
public class FormulaDto implements FormulaSetMemento {

	/** The formula id. */
	private String formulaId;

	/** The optional item no. */
	private int optionalItemNo;

	/** The symbol value. */
	private String symbolValue;

	/** The order no. */
	private int orderNo;

	/** The formula atr. */
	private int formulaAtr;

	/** The formula name. */
	private String formulaName;

	/** The calculation atr. */
	private int calcAtr;

	// ===================== Optional ======================= //
	/** The monthly rounding. */
	private RoundingDto monthlyRounding;

	/** The daily rounding. */
	private RoundingDto dailyRounding;

	/** The formula setting. */
	private FormulaSettingDto formulaSetting;

	/** The item selection. */
	private ItemSelectionDto itemSelection;

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
		if (this.calcAtr == CalculationAtr.ITEM_SELECTION.value) {
			this.itemSelection = new ItemSelectionDto();
			setting.saveToMemento(this.itemSelection);
		} else {
			this.formulaSetting = new FormulaSettingDto();
			setting.saveToMemento(this.formulaSetting);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setCalculationFormulaAtr(nts.uk.ctx.at.record.dom.optitem.calculation.
	 * FormulaAtr)
	 */
	@Override
	public void setFormulaAtr(OptionalItemAtr atr) {
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
	public void setMonthlyRounding(Optional<Rounding> rounding) {
		this.monthlyRounding = new RoundingDto();
		rounding.ifPresent(item -> item.saveToMemento(this.monthlyRounding));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSetMemento#
	 * setDailyRounding(nts.uk.ctx.at.record.dom.optitem.calculation.Rounding)
	 */
	@Override
	public void setDailyRounding(Optional<Rounding> rounding) {
		this.dailyRounding = new RoundingDto();
		rounding.ifPresent(item -> item.saveToMemento(this.dailyRounding));
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
		this.calcAtr = calcAtr.value;
	}
}
