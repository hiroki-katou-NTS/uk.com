/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem.calculation;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class OptionalItemFormula.
 */
// 任意項目計算式
// 責務 : 計算式を作成する
// Responsibility: Create calculation formulas
@Getter
public class Formula extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The formula id. */
	// 計算式ID
	private FormulaId formulaId;

	/** The optional item no. */
	// 任意項目NO
	private OptionalItemNo optionalItemNo;

	/** The symbol. */
	// 記号
	private Symbol symbol;

	/** The formula atr. */
	// 属性
	private FormulaAtr formulaAtr;

	/** The formula name. */
	// 計算式名称
	private FormulaName formulaName;

	/** The formula setting. */
	// 計算式設定
	private CalcFormulaSetting formulaSetting;

	// ===================== Optional ======================= //
	/** The monthly rounding. */
	// 月別端数処理
	private Rounding monthlyRounding;

	/** The daily rounding. */
	// 日別端数処理
	private Rounding dailyRounding;

	/**
	 * Instantiates a new optional item formula.
	 *
	 * @param memento the memento
	 */
	public Formula(FormulaGetMemento memento) {
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
	public void saveToMemento(FormulaSetMemento memento) {
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((formulaId == null) ? 0 : formulaId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Formula other = (Formula) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (formulaId == null) {
			if (other.formulaId != null)
				return false;
		} else if (!formulaId.equals(other.formulaId))
			return false;
		return true;
	}

}
