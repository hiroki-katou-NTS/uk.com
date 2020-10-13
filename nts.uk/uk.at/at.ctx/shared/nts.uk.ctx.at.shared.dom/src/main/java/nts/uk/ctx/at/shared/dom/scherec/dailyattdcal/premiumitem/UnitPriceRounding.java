package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem;


/**
 * 人件費単価端数処理
 * @author daiki_ichioka
 *
 */
public enum UnitPriceRounding {
	// 切り捨て
	TRUNCATION(0, "ENUM_ROUNDING_TRUNCATION"),

	/** The round up. */
	// 切り上げ
	ROUND_UP(1, "ENUM_ROUNDING_ROUND_UP"),

	/** The down 4 up 5. */
	// 四捨五入
	DOWN_4_UP_5(2, "ENUM_ROUNDING_DOWN_4_UP_5");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static UnitPriceRounding[] values = UnitPriceRounding.values();

	/**
	 * Instantiates a new rounding.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 */
	private UnitPriceRounding(int value, String nameId) {
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
	public static UnitPriceRounding valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (UnitPriceRounding val : UnitPriceRounding.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
