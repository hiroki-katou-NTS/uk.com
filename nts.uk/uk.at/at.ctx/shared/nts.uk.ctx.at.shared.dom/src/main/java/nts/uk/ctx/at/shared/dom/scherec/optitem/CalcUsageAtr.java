package nts.uk.ctx.at.shared.dom.scherec.optitem;

/**
 * 計算区分
 * @author shuichi_ishida
 */
public enum CalcUsageAtr {

	/** The not calculation. */
	// 計算しない
	NOT_CALC(0, "Enum_CalcUsageAtr_NOT_CALC", "計算しない"),

	/** The calculation. */
	// 計算する
	CALC(1, "Enum_CalcUsageAtr_CALC", "計算する");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static CalcUsageAtr[] values = CalcUsageAtr.values();

	/**
	 * Instantiates a new calculation usage atr.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private CalcUsageAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the calculation usage atr
	 */
	public static CalcUsageAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CalcUsageAtr val : CalcUsageAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * 計算するか判定する
	 * @return するならtrue
	 */
	public boolean isCalc() {
		return CALC.equals(this);
	}
	
	/**
	 * 計算しないか判定する
	 * @return しないならtrue
	 */
	public boolean isNotCalc() {
		return NOT_CALC.equals(this);
	}
}
