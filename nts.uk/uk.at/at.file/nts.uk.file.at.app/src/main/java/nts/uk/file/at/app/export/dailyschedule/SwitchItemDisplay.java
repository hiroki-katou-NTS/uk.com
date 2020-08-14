package nts.uk.file.at.app.export.dailyschedule;
/**
 * 項目表示切替
 * @author LienPTK
 */
public enum SwitchItemDisplay {
	/** コードで表示 */
	DISPLAY_NAME(0),
	/** 名称で表示 */
	DISPLAY_CODE(1);

	private final int value;

	private SwitchItemDisplay(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the switch item display
	 */
	public static SwitchItemDisplay valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (SwitchItemDisplay val : SwitchItemDisplay.values()) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
