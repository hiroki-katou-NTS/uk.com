package nts.uk.ctx.at.function.ac.employmentinfoterminal.infoterminal;

/**
 * @author ThanhNX
 *
 *         申請区分
 */
public enum ApplicationCategory {

	STAMP("1", "打刻申請"),

	OVERTIME("2", "残業申請"),

	VACATION("3", "休暇申請"),

	WORK_CHANGE("4", "勤務変更申請"),

	WORK_HOLIDAY("8", "休日出勤時間申請"),

	LATE("9", "遅刻早退取消申請"),

	ANNUAL("A", "時間年休申請");

	public final String value;

	public final String nameType;

	private ApplicationCategory(String value, String nameType) {
		this.value = value;
		this.nameType = nameType;
	}

	private static final ApplicationCategory values[] = ApplicationCategory.values();

	public static ApplicationCategory valueStringOf(String value) {
		if (value == null) {
			return null;
		}

		for (ApplicationCategory val : ApplicationCategory.values) {
			if (val.value.equals(value)) {
				return val;
			}
		}

		return null;
	}

}
