/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.timeitemmanagement.calculationformula;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.timeitemmanagement.OptionalItemNo;

/**
 * The Class OptionalItemCalculationFormula.
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

	/** The optional item calculation formula atr. */
	// 属性
	private CalculationFormulaAttribute formulaAtr;

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

}
