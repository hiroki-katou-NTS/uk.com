package nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule;


// TODO: Auto-generated Javadoc
/**
 * The Enum Settingclassification.
 */
public enum Category {
	
	/** The Setting. */
	Setting(1, "設定する"),
	
	/** The No setting. */
	NoSetting(0, "設定しない");
	
	/** The value. */
	public int value;

	/** The description. */
	public String description;
	
	/** The Constant values. */
	private final static Category[] values = Category.values();
	
	/**
	 * Instantiates a new settingclassification.
	 *
	 * @param value the value
	 * @param description the description
	 */
	private Category(int value, String description) {
		this.value = value;
		this.description = description;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the settingclassification
	 */
	public static Category valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (Category val : Category.values()) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
