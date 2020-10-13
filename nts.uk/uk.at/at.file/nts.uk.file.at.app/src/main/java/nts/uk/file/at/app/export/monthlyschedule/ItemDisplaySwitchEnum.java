package nts.uk.file.at.app.export.monthlyschedule;

public enum ItemDisplaySwitchEnum {
	// 称名
	NAME(0),
	
	// コード
	CODE(1);
	
	/** The indicator. */
	public int indicator;

	/**
	 * Instantiates a new page break indicator.
	 *
	 * @param indicator the indicator
	 */
	private ItemDisplaySwitchEnum(int indicator) {
		this.indicator = indicator;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the page break indicator
	 */
	public static ItemDisplaySwitchEnum valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ItemDisplaySwitchEnum val : ItemDisplaySwitchEnum.values()) {
			if (val.indicator == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
