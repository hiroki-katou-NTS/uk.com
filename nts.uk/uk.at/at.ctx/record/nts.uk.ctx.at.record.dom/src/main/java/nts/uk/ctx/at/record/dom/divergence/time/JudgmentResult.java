package nts.uk.ctx.at.record.dom.divergence.time;

/**
 * The Enum JudgmentResult.
 */
// 判定結果
public enum JudgmentResult {

	/** The normal. */
	// ├正常
	NORMAL(0, "├正常"),

	/** The error. */
	// エラー
	ERROR(1, "エラー"),

	/** The alarm. */
	// アラーム
	ALARM(2, "アラーム");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String display;

	/** The Constant values. */
	private final static JudgmentResult[] values = JudgmentResult.values();

	/**
	 * Instantiates a new rounding.
	 *
	 * @param value
	 *            the value
	 * @param display
	 *            the display
	 */
	private JudgmentResult(int value, String display) {
		this.value = value;
		this.display = display;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the rounding
	 */
	public static JudgmentResult valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (JudgmentResult val : JudgmentResult.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
