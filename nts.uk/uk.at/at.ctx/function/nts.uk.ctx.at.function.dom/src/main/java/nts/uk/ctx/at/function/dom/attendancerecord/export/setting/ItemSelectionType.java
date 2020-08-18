package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

//	項目選択種類
/**
 * @author nws-ducnt
 *
 */
public enum ItemSelectionType {

	// 	定型選択
	/**
	 * ENUM_FIXED_FORM_SELECTION
	 */
	FIXED_FORM_SELECTION(0, "定型選択"),

	
	// 	自由選択
	/**
	 * ENUM_FREE_CHOICE
	 */
	FREE_CHOICE(1, "自由選択");
	

	/** The value. */
	public final int value;

	/** The name. */
	public final String name;

	/** The Constant values. */
	private final static ItemSelectionType[] values = ItemSelectionType.values();

	/**
	 * Instantiates a new ItemSelectionType.
	 *
	 * @param value
	 *            the value
	 * @param name
	 *            the name
	 */
	private ItemSelectionType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return ItemSelectionType
	 */
	public static ItemSelectionType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ItemSelectionType val : ItemSelectionType.values) {
			if (val.value == value) {

				return val;
			}
		}

		// Not found.
		return null;
	}
}
