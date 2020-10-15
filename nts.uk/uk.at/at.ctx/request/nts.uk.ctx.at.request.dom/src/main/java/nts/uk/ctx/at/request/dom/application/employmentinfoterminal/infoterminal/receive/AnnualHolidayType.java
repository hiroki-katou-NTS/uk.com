package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive;

/**
 * @author ThanhNX
 * 
 *         年休種類
 */
public enum AnnualHolidayType {

	LATE1("1", "遅刻１（出勤前１）"),

	LATE2("2", "遅刻２（出勤前２）"),

	EARLY1("3", "早退１（退勤前１）"),

	EARLY2("4", "早退２（退勤前２）"),

	OUT1("5", "私用外出"),

	OUT2("6", "組合外出"),

	VERY_LATE1("A", "超休遅刻1"),

	VERY_LATE2("B", "超休遅刻2"),

	LEAVE_EARLY1("C", "超休早退1"),

	LEAVE_EARLY2("D", "超休早退2"),

	HOLIDAY_PRIV("E", "超休私用外出"),

	HOLIDAY_GOOUT("F", "超休組合外出");

	public final String value;

	public final String nameType;

	private AnnualHolidayType(String value, String nameType) {
		this.value = value;
		this.nameType = nameType;
	}

	private static final AnnualHolidayType values[] = AnnualHolidayType.values();

	public static AnnualHolidayType valueStringOf(String value) {
		if (value == null) {
			return null;
		}

		for (AnnualHolidayType val : AnnualHolidayType.values) {
			if (val.value.equals(value)) {
				return val;
			}
		}

		return null;
	}
}
