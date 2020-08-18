package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

//	月次確認済表示区分
/**
 * @author nws-ducnt
 *
 */
public enum MonthlyConfirmedDisplay {
	// 	表示
	/**
	 * ENUM_HIDEN
	 */
	HIDEN(0, "非表示"),

	// 	自由選択
	/**
	 * ENUM_DISPLAY
	 */
	DISPLAY(1, "表示");

	/** The value. */
	public final int value;

	/** The name. */
	public final String name;

	/** The Constant values. */
	private final static MonthlyConfirmedDisplay[] values = MonthlyConfirmedDisplay.values();

	/**
	 * Instantiates a new MonthlyConfirmed.
	 *
	 * @param value the value
	 * @param name  the name
	 */
	private MonthlyConfirmedDisplay(int value, String name) {
		this.value = value;
		this.name = name;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return MonthlyConfirmed
	 */
	public static MonthlyConfirmedDisplay valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (MonthlyConfirmedDisplay val : MonthlyConfirmedDisplay.values) {
			if (val.value == value) {

				return val;
			}
		}

		// Not found.
		return null;
	}
}
