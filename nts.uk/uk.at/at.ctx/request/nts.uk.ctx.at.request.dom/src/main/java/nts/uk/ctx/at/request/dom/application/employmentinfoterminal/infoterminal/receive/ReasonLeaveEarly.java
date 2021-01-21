package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive;

/**
 * @author ThanhNX
 *
 *         遅刻早退理由
 */
public enum ReasonLeaveEarly {

	LATE("1", "遅刻取消"),

	EARLY("2", "早退取消");

	public final String value;

	public final String nameType;

	private ReasonLeaveEarly(String value, String nameType) {
		this.value = value;
		this.nameType = nameType;
	}

	private static final  ReasonLeaveEarly values[] = ReasonLeaveEarly.values();

	public static ReasonLeaveEarly valueStringOf(String value) {
		if (value == null) {
			return null;
		}

		for (ReasonLeaveEarly val : ReasonLeaveEarly.values) {
			if (val.value.equals(value)) {
				return val;
			}
		}

		return null;
	}
}
