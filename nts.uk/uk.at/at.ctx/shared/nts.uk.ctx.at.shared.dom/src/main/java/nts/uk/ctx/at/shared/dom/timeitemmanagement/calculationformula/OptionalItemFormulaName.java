/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.timeitemmanagement.calculationformula;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class DispOrder.
 */
// 任意項目計算式の並び順
// 計算式で使用する順番を管理する
// Manage the order of use in calculation formulas
@Getter
public class OptionalItemFormulaName extends AggregateRoot {

	/** The disp order. */
	// 並び順
	private int dispOrder;

}
