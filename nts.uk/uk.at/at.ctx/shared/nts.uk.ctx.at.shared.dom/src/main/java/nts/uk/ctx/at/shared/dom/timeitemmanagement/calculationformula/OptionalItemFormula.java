/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.timeitemmanagement.calculationformula;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

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
	private String formulaId;

	/** The optional item no. */
	// 任意項目NO
	private String optionalItemNo;

	/** The formula name. */
	// 計算式名称
	private OptionalItemFormulaName formulaName;

	/** The formula setting. */
	// 計算式設定
	private String formulaSetting;

	/** The optional item calculation formula atr. */
	// 属性
	private CalculationFormulaAttribute formulaAtr;

	/** The monthly rounding. */
	// 月別端数処理
	private String monthlyRounding;

	/** The daily rounding. */
	// 日別端数処理
	private String dailyRounding; // Optional //

	/** The symbol. */
	// 記号
	private String symbol;

}
