package nts.uk.file.at.app.export.attendancerecord;


/**
 * UKDesign.UniversalK.就業.KWR_帳表.KWR002₌出勤簿 (Phiếu chấm công).ユーザ固有情報.項目選択区分
 * The Enum ItemSelectionType.
 */
public enum ItemSelectionType {
	/**	定型選択 */
	STANDARD_SELECTION(0),
	/**	目由設定 */
	FREE_SETTING(1);
	
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
