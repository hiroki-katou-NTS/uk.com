package nts.uk.file.at.app.export.dailyschedule;

/**
 * UKDesign.UniversalK.就業.KWR_帳表.KWR001_日別勤務表(daily work schedule).ユーザ固有情報(User
 * specific information).確認済みのデータ
 * 
 * @author phith
 *
 */
public enum ConfirmedData {
	// 抽出する
	EXTRACT(0),

	// 抽出しない
	NOT_EXTRACT(1);

	/** The value. */
	public int value;

	private ConfirmedData(int value) {
		this.value = value;
	}

	public static ConfirmedData valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ConfirmedData val : ConfirmedData.values()) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
