package nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm;

/**
 * The Enum DivergenceInputRequired.
 */
//乖離理由の入力必須区分
public enum DivergenceInputRequired {

	/** The Require. */
	//必須とする
	REQUIRE(0, "必須とする"),

	/** The Not require. */
	//必須としない
	NOT_REQUIRE(1, "必須としない");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static DivergenceInputRequired[] values = DivergenceInputRequired.values();

	/**
	 * Instantiates a new divergence input required.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 */
	private DivergenceInputRequired(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the divergence input required
	 */
	public static DivergenceInputRequired valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (DivergenceInputRequired val : DivergenceInputRequired.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
