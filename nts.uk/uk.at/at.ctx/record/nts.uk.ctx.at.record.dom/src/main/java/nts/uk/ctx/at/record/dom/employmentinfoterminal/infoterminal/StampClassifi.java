package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

/**
 * @author thanh_nx
 *
 *         打刻分類
 */
public enum StampClassifi {

	ATTENDANCE(0, "出勤"),

	LEAVING(1, "退勤"),

	GO_OUT(2, "外出"),

	GO_BACK(3, "戻り");

	public final int value;

	public final String nameType;

	private static final StampClassifi[] values = StampClassifi.values();

	private StampClassifi(int value, String nameType) {
		this.value = value;
		this.nameType = nameType;
	}

	public static StampClassifi valueOf(Integer value) {
		if (value == null) {
			return null;
		}

		for (StampClassifi val : StampClassifi.values) {
			if (val.value == value) {
				return val;
			}
		}

		return null;
	}
}
