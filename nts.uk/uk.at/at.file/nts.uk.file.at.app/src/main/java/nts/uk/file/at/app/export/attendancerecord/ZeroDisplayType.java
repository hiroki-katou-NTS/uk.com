package nts.uk.file.at.app.export.attendancerecord;

/**
 * UKDesign.UniversalK.就業.KWR_帳表.KWR002₌出勤簿 (Phiếu chấm công).ユーザ固有情報.ゼロ表示区分
 * 	ゼロ表示区分
 * The Enum ZeroDisplayType.
 */
public enum ZeroDisplayType {
	/** 表示 */
	DISPLAY(0),
	/** 非表示 */
	NON_DISPLAY(1);
	
	private final int zeroDisplayType;

	private ZeroDisplayType(int zeroDisplayType) {
		this.zeroDisplayType = zeroDisplayType;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the zero display type
	 */
	public static ZeroDisplayType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ZeroDisplayType val : ZeroDisplayType.values()) {
			if (val.zeroDisplayType == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
