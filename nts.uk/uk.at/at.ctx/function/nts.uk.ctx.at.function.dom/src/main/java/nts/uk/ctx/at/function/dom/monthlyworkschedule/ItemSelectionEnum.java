package nts.uk.ctx.at.function.dom.monthlyworkschedule;

/**
 * The Enum ItemSelectionEnum.
 */
//@author ChienDM
//帳票共通の設定区分

public enum ItemSelectionEnum {
	/** The Free setting . */
	// 定型選択
	FREE_SETTING(1, "Enum_FreeSetting"),
	/** The Standard selection. */
	// 自由設定
	STANDARD_SELECTION(0, "Enum_StandardSelection");

	/** The value. */
	public final Integer value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static ItemSelectionEnum[] values = ItemSelectionEnum.values();

	/**
	 * Instantiates a new Setting classification common To forms.
	 *
	 * @param value  the value
	 * @param nameId the name id
	 */
	private ItemSelectionEnum(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the prints the setting remarks column
	 */
	public static ItemSelectionEnum valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ItemSelectionEnum val : ItemSelectionEnum.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
