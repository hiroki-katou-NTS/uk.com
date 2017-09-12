/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common.amountrounding;

import lombok.AllArgsConstructor;

/**
 * The Enum Rounding.
 */
// 端数処理(詳細計算式)
@AllArgsConstructor
public enum Rounding {

	/** The truncation. */
	// 切り捨て
	TRUNCATION(0),

	/** The round up. */
	// 切り上げ
	ROUND_UP(1),

	/** The down 1 up 2. */
	// 一捨二入
	DOWN_1_UP_2(2),

	/** The down 2 up 3. */
	// 二捨三入
	DOWN_2_UP_3(3),

	/** The down 3 up 4. */
	// 三捨四入
	DOWN_3_UP_4(4),

	/** The down 4 up 5. */
	// 四捨五入
	DOWN_4_UP_5(5),

	/** The down 5 up 6. */
	// 五捨六入
	DOWN_5_UP_6(6),

	/** The down 6 up 7. */
	// 六捨七入
	DOWN_6_UP_7(7),

	/** The down 7 up 8. */
	// 七捨八入
	DOWN_7_UP_8(8),

	/** The down 8 up 9. */
	// 八捨九入
	DOWN_8_UP_9(9);

	/** The value. */
	public final int value;
}
