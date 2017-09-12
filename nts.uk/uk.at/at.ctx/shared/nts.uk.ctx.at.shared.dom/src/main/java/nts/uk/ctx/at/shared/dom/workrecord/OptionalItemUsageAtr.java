/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrecord;

import lombok.AllArgsConstructor;

/**
 * The Enum OptionalItemUsageClassification.
 */
// 任意項目利用区分
@AllArgsConstructor
public enum OptionalItemUsageAtr {

	/** The not use. */
	// 利用する
	NOT_USE(0),

	/** The use. */
	// 利用しない
	USE(1);

	/** The value. */
	public final int value;
}
