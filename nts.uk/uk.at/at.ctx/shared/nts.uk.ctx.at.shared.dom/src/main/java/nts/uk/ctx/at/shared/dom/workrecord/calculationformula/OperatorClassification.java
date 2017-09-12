/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrecord.calculationformula;

import lombok.AllArgsConstructor;

/**
 * The Enum OperatorClassification.
 */
// 演算子区分
@AllArgsConstructor
public enum OperatorClassification {

	/** The add. */
	ADD(0),

	/** The subtract. */
	SUBTRACT(1),

	/** The multiply. */
	MULTIPLY(2),

	/** The divide. */
	DIVIDE(3);

	/** The value. */
	public final int value;
}
