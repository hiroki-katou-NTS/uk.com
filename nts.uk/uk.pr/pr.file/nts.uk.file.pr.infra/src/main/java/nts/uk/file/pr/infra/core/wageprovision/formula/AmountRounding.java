package nts.uk.file.pr.infra.core.wageprovision.formula;

/**
 * The Enum Rounding.
 */
// 端数処理(詳細計算式)
public enum AmountRounding {

	/** The truncation. */
	// 切り捨て
	TRUNCATION(0, "ENUM_ROUNDING_TRUNCATION"),

	/** The round up. */
	// 切り上げ
	ROUND_UP(1, "ENUM_ROUNDING_ROUND_UP"),

	/** The down 1 up 2. */
	// 一捨二入
	DOWN_1_UP_2(2, "ENUM_ROUNDING_DOWN_1_UP_2"),

	/** The down 2 up 3. */
	// 二捨三入
	DOWN_2_UP_3(3, "ENUM_ROUNDING_DOWN_2_UP_3"),

	/** The down 3 up 4. */
	// 三捨四入
	DOWN_3_UP_4(4, "ENUM_ROUNDING_DOWN_3_UP_4"),

	/** The down 4 up 5. */
	// 四捨五入
	DOWN_4_UP_5(5, "ENUM_ROUNDING_DOWN_4_UP_5"),

	/** The down 5 up 6. */
	// 五捨六入
	DOWN_5_UP_6(6, "ENUM_ROUNDING_DOWN_5_UP_6"),

	/** The down 6 up 7. */
	// 六捨七入
	DOWN_6_UP_7(7, "ENUM_ROUNDING_DOWN_6_UP_7"),

	/** The down 7 up 8. */
	// 七捨八入
	DOWN_7_UP_8(8, "ENUM_ROUNDING_DOWN_7_UP_8"),

	/** The down 8 up 9. */
	// 八捨九入
	DOWN_8_UP_9(9, "ENUM_ROUNDING_DOWN_8_UP_9");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static AmountRounding[] values = AmountRounding.values();

	/**
	 * Instantiates a new rounding.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 */
	private AmountRounding(int value, String nameId) {
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
	public static AmountRounding valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (AmountRounding val : AmountRounding.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
