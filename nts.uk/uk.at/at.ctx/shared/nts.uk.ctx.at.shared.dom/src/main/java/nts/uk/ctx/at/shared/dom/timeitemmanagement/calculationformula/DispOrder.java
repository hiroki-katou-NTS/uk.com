/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.timeitemmanagement.calculationformula;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class DispOrder.
 */
// 任意項目計算式の並び順
// 責務 : 計算式で使用する順番を管理する
// Responsibility: Manage the order of use in calculation formulas
@Getter
public class DispOrder extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The optional item no. */
	// 任意項目NO
	private String optionalItemNo;

	/** The optional item formula id. */
	// 任意項目計算式ID
	private String optionalItemFormulaId;

	/** The disp order. */
	// 並び順
	private int dispOrder;

}
