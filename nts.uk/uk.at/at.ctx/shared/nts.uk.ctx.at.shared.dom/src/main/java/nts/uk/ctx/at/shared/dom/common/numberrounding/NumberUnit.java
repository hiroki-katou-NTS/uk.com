/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common.numberrounding;

import java.util.Optional;

/**
 * The Enum Unit.
 */
// 単位
public enum NumberUnit {

	/** The none. */
	// 無し
	NONE(0, "Enum_Unit_NONE"),

	/** The int 1 digits. */
	// 整数1桁
	INT_1_DIGITS(1, "Enum_Unit_Int_1_Digits"),

	/** The int 2 digits. */
	// 整数2桁
	INT_2_DIGITS(2, "Enum_Unit_Int_2_Digits"),

	/** The int 3 digits. */
	// 整数3桁
	INT_3_DIGITS(3, "Enum_Unit_Int_3_Digits"),

	/** The int 4 digits. */
	// 整数4桁
	INT_4_DIGITS(4, "Enum_Unit_Int_4_Digits"),

	/** The int 5 digits. */
	// 整数5桁
	INT_5_DIGITS(5, "Enum_Unit_Int_5_Digits"),

	/** The int 6 digits. */
	// 整数6桁
	INT_6_DIGITS(6, "Enum_Unit_Int_6_Digits"),

	/** The int 7 digits. */
	// 整数7桁
	INT_7_DIGITS(7, "Enum_Unit_Int_7_Digits"),

	/** The int 8 digits. */
	// 整数8桁
	INT_8_DIGITS(8, "Enum_Unit_Int_8_Digits"),

	/** The int 9 digits. */
	// 整数9桁
	INT_9_DIGITS(9, "Enum_Unit_Int_9_Digits"),

	/** The int 10 digits. */
	// 整数10桁
	INT_10_DIGITS(10, "Enum_Unit_Int_10_Digits"),

	/** The int 11 digits. */
	// 整数11桁
	INT_11_DIGITS(11, "Enum_Unit_Int_11_Digits"),

	/** The decimal 1st. */
	// 小数第1位
	DECIMAL_1ST(12, "Enum_Unit_Decimal_1st"),

	/** The decimal 2nd. */
	// 小数第2位
	DECIMAL_2ND(13, "Enum_Unit_Decimal_2nd"),

	/** The decimal 3rd. */
	// 小数第3位
	DECIMAL_3RD(14, "Enum_Unit_Decimal_3rd");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static NumberUnit[] values = NumberUnit.values();

	/**
	 * Instantiates a new rounding.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 */
	private NumberUnit(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the rounding
	 */
	public static NumberUnit valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (NumberUnit val : NumberUnit.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * 丸めを行う桁数を取得する
	 * 桁数なしの場合はOptional.empty()を返す
	 * @return
	 */
	public Optional<Integer> asNumber() {
		switch (this) {
		case NONE: return Optional.empty();
		case INT_1_DIGITS: return Optional.of(-1);
		case INT_2_DIGITS: return Optional.of(-2);
		case INT_3_DIGITS: return Optional.of(-3);
		case INT_4_DIGITS: return Optional.of(-4);
		case INT_5_DIGITS: return Optional.of(-5);
		case INT_6_DIGITS: return Optional.of(-6);
		case INT_7_DIGITS: return Optional.of(-7);
		case INT_8_DIGITS: return Optional.of(-8);
		case INT_9_DIGITS: return Optional.of(-9);
		case INT_10_DIGITS: return Optional.of(-10);
		case INT_11_DIGITS: return Optional.of(-11);
		case DECIMAL_1ST: return Optional.of(0);
		case DECIMAL_2ND: return Optional.of(1);
		case DECIMAL_3RD: return Optional.of(2);
		
		default: throw new RuntimeException("invalid value: " + this);
		}
	}
}
