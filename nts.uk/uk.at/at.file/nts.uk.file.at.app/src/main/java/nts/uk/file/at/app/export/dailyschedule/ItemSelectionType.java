package nts.uk.file.at.app.export.dailyschedule;

/**
 * 項目選択区分
 * @author LienPTK_NWS
 *
 */
public enum ItemSelectionType {
	/** 定型選択 */
	STANDARD_SELECTION(1),
	/** 目由設定 */
	FREE_SETTING(0);
	
	private final int selectionValue;

	private ItemSelectionType(int selectionValue) {
		this.selectionValue = selectionValue;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the page break indicator
	 */
	public static ItemSelectionType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ItemSelectionType val : ItemSelectionType.values()) {
			if (val.selectionValue == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
