package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send;

/**
 * @author ThanhNX
 *
 *         勤務区分番号
 */
public enum NRWorkType {

	ATTENDANCE("0", "出勤"),

	HOLIDAY("1", "休日"),

	HOLIDAY_WORK("2", "休日出勤"),

	ANNUAL_HOLIDAY("3", "年休"),

	SPECIAL_HOLIDAY("4", "特別休暇"),

	ABSENCE("5", "欠勤"),

	SUBSTITUTE_HOLIDAY("6", "代休"),

	SHOOTING("7", "振出"),

	PAUSE("8", "振休"),

	CONTINUOUS_WORK("9", "連続勤務"),

	CLOSURE("10", "休業"),

	TIMEDIGEST_VACATION("11", "時間消化休暇"),

	YEARLY_RESERVED("_0", "積立年休"),

	LEAVE_ABSENCE("_1", "休職");

	public final String value;

	public final String nameType;

	private static final NRWorkType[] values = NRWorkType.values();

	private NRWorkType(String value, String nameType) {
		this.value = value;
		this.nameType = nameType;
	}

	public static NRWorkType valueStringOf(String value) {
		if (value == null) {
			return null;
		}

		for (NRWorkType val : NRWorkType.values) {
			if (val.value.equals(value)) {
				return val;
			}
		}

		return null;
	}
}
