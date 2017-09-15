/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optionalitem.calculationformula;

import lombok.AllArgsConstructor;

/**
 * The Enum AddSubOperator.
 */
// 加減算演算子
@AllArgsConstructor
public enum AddSubOperator {

	/** The add. */
	ADD(0),

	/** The subtract. */
	SUBTRACT(1);

	/** The value. */
	public final int value;
}
