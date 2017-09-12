/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common.amountrounding;

import lombok.AllArgsConstructor;

/**
 * The Enum Unit.
 */
// 端数処理位置
@AllArgsConstructor
public enum Unit {

	/** The one yen. */
	// 1円
	ONE_YEN(1),

	/** The ten yen. */
	// 10円
	TEN_YEN(10),

	/** The one hundred yen. */
	// 100円
	ONE_HUNDRED_YEN(100),

	/** The one thousand yen. */
	// 1000円
	ONE_THOUSAND_YEN(1000);

	/** The value. */
	public final int value;
}
