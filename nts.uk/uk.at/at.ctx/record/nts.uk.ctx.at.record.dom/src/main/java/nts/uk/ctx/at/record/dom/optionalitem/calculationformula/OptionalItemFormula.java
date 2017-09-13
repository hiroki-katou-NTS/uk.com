/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optionalitem.calculationformula;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.optionalitem.OptionalItemNo;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class OptionalItemFormula.
 */
// 任意項目計算式
// 責務 : 計算式を作成する
// Responsibility: Create calculation formulas
@Getter
public class OptionalItemFormula extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The formula id. */
	// 計算式ID
	private OptionalItemFormulaId formulaId;

	/** The optional item no. */
	// 任意項目NO
	private OptionalItemNo optionalItemNo;

	/** The formula name. */
	// 計算式名称
	private OptionalItemFormulaName formulaName;

	/** The formula setting. */
	// 計算式設定
	private OptionalItemFormulaSetting formulaSetting;

	/** The formula atr. */
	// 属性
	private CalculationFormulaAtr formulaAtr;

	/** The symbol. */
	// 記号
	private Symbol symbol;

	// ===================== Optional ======================= //
	/** The monthly rounding. */
	// 月別端数処理
	private MonthlyRounding monthlyRounding;

	/** The daily rounding. */
	// 日別端数処理
	private DailyRounding dailyRounding;

	/**
	 * Instantiates a new optional item formula.
	 *
	 * @param memento the memento
	 */
	public OptionalItemFormula(OptionalItemFormulaGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.formulaId = memento.getOptionalItemFormulaId();
		this.optionalItemNo = memento.getOptionalItemNo();
		this.formulaName = memento.getOptionalItemFormulaName();
		this.formulaSetting = memento.getOptionalItemFormulaSetting();
		this.formulaAtr = memento.getCalculationFormulaAtr();
		this.symbol = memento.getSymbol();
		this.monthlyRounding = memento.getMonthlyRounding();
		this.dailyRounding = memento.getDailyRounding();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OptionalItemFormulaSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setOptionalItemFormulaId(this.formulaId);
		memento.setOptionalItemNo(this.optionalItemNo);
		memento.setOptionalItemFormulaName(this.formulaName);
		memento.setOptionalItemFormulaSetting(this.formulaSetting);
		memento.setCalculationFormulaAtr(this.formulaAtr);
		memento.setSymbol(this.symbol);
		memento.setMonthlyRounding(this.monthlyRounding);
		memento.setDailyRounding(this.dailyRounding);
	}

}
