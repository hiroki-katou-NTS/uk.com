package nts.uk.ctx.at.request.pubimp.application.infoterminal;

/**
 * @author ThanhNX
 *
 *         申請区分
 */
public enum ApplicationCategoryPub {

	STAMP("1", "打刻申請"),

	OVERTIME("2", "残業申請"),

	VACATION("3", "休暇申請"),

	WORK_CHANGE("4", "勤務変更申請"),

	WORK_HOLIDAY("8", "休日出勤時間申請"),

	LATE("9", "遅刻早退取消申請"),

	ANNUAL("A", "時間年休申請");

	public final String value;

	public final String nameType;

	private ApplicationCategoryPub(String value, String nameType) {
		this.value = value;
		this.nameType = nameType;
	}

	private static final ApplicationCategoryPub values[] = ApplicationCategoryPub.values();

	public static ApplicationCategoryPub valueStringOf(String value) {
		if (value == null) {
			return null;
		}

		for (ApplicationCategoryPub val : ApplicationCategoryPub.values) {
			if (val.value.equals(value)) {
				return val;
			}
		}

		return null;
	}

}
