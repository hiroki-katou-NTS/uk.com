/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optionalitem.calculationformula.disporder;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.optionalitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optionalitem.calculationformula.OptionalItemFormulaId;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class FormulaDispOrder.
 */
// 任意項目計算式の並び順
// 責務 : 計算式で使用する順番を管理する
// Responsibility: Manage the order of use in calculation formulas
@Getter
public class FormulaDispOrder extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The optional item no. */
	// 任意項目NO
	private OptionalItemNo optionalItemNo;

	/** The optional item formula id. */
	// 任意項目計算式ID
	private OptionalItemFormulaId optionalItemFormulaId;

	/** The disp order. */
	// 並び順
	private int dispOrder;

}
