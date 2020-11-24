package nts.uk.ctx.at.shared.dom.scherec.optitem;

/**
 * 利用条件（任意項目）
 * @author shuichi_ishida
 */
public enum TermsOfUseForOptItem {

	/** The not use. */
	// 利用しない
	NOT_USE(0, "Enum_TermsOfUseForOptItem_NOT_USE", "利用しない"),

	/** The use. */
	// 利用する
	USE(1, "Enum_TermsOfUseForOptItem_USE", "利用する"),

	/** The virtical total for daily. */
	// 日別縦計する
	DAILY_VTOTAL(2, "Enum_TermsOfUseForOptItem_DAILY_VTOTAL", "日別縦計する");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static TermsOfUseForOptItem[] values = TermsOfUseForOptItem.values();

	/**
	 * Instantiates a new optional item usage atr.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private TermsOfUseForOptItem(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the optional item usage atr
	 */
	public static TermsOfUseForOptItem valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (TermsOfUseForOptItem val : TermsOfUseForOptItem.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
