/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common.amountrounding;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * The Enum Unit.
 */
// 端数処理位置
public enum AmountUnit {

	/** The one yen. */
	// 1円
	ONE_YEN(1, "ENUM_UNIT_ONE_YEN"),

	/** The ten yen. */
	// 10円
	TEN_YEN(10, "ENUM_UNIT_TEN_YEN"),

	/** The one hundred yen. */
	// 100円
	ONE_HUNDRED_YEN(100, "ENUM_UNIT_ONE_HUNDRED_YEN"),

	/** The one thousand yen. */
	// 1000円
	ONE_THOUSAND_YEN(1000, "ENUM_UNIT_ONE_THOUSAND_YEN"),;

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static AmountUnit[] values = AmountUnit.values();

	/**
	 * Instantiates a new unit.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 */
	private AmountUnit(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the unit
	 */
	public static AmountUnit valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (AmountUnit val : AmountUnit.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * 丸めを行う金額位置を取得する
	 * @return
	 */
	public int asAmount() {
		switch (this) {
		case ONE_YEN: return 0;
		case TEN_YEN: return -1;
		case ONE_HUNDRED_YEN: return -2;
		case ONE_THOUSAND_YEN: return -3;
		default: throw new RuntimeException("invalid value: " + this);
		}
	}
	

	public double asRoundingSet() {
		switch (this) {
		case ONE_YEN: return 0.1;
		case TEN_YEN: return 1;
		case ONE_HUNDRED_YEN: return 10;
		case ONE_THOUSAND_YEN: return 100;
		default: throw new RuntimeException("invalid value: " + this);
		}
	}
	
}
