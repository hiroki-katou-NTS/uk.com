/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common.numberrounding;

import lombok.AllArgsConstructor;

/**
 * The Enum Unit.
 */
// 端数処理
@AllArgsConstructor
public enum Unit {

	/** The none. */
	// 無し
	NONE(0),

	/** The int 1 digits. */
	// 整数1桁
	INT_1_DIGITS(1),

	/** The int 2 digits. */
	// 整数2桁
	INT_2_DIGITS(2),

	/** The int 3 digits. */
	// 整数3桁
	INT_3_DIGITS(3),

	/** The int 4 digits. */
	// 整数4桁
	INT_4_DIGITS(4),

	/** The int 5 digits. */
	// 整数5桁
	INT_5_DIGITS(5),

	/** The int 6 digits. */
	// 整数6桁
	INT_6_DIGITS(6),

	/** The int 7 digits. */
	// 整数7桁
	INT_7_DIGITS(7),

	/** The int 8 digits. */
	// 整数8桁
	INT_8_DIGITS(8),

	/** The int 9 digits. */
	// 整数9桁
	INT_9_DIGITS(9),

	/** The int 10 digits. */
	// 整数10桁
	INT_10_DIGITS(10),

	/** The int 11 digits. */
	// 整数11桁
	INT_11_DIGITS(11),

	/** The decimal 1st. */
	// 小数第1位
	DECIMAL_1ST(12),

	/** The decimal 2st. */
	// 小数第2位
	DECIMAL_2ST(13),

	/** The decimal 3st. */
	// 小数第3位
	DECIMAL_3ST(14);

	/** The value. */
	public final int value;
}
