package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive;

/**
 * @author ThanhNX
 *
 *         打刻区分 NR
 */
public enum LeaveCategory {

	WORK("A", "出勤"),

	WORK_HALF("B", "出勤＋半休"),

	WORK_FLEX("D", "出勤＋ﾌﾚｯｸｽ"),

	LEAVE("G", "退勤"),

	LEAVE_HALF("H", "退勤＋半休"),

	LEAVE_OVERTIME("J", "退勤＋残業"),

	LEAVE_FLEX("L", "退勤＋ﾌﾚｯｸｽ"),

	GO_OUT("O", "外出"),

	RETURN("Q", "戻り"),

	EARLY("S", "早出"),

	VACATION("U", "休出"),

	WORK_TEMPORARY("0", "出勤＋臨時"),

	RETURN_START("1", "（入）から戻る／開始"),

	GO_EN("2", "（出）に行く／終了"),

	WORK_ENTRANCE("3", "出勤＋入"),

	WORK_HALF_ENTRANCE("4", "出勤＋半休＋入"),

	WORK_FLEX_ENTRANCE("5", "出勤＋ﾌﾚｯｸｽ＋入"),

	VACATION_ENTRANCE("6", "休出＋入"),

	TEMPORARY_ENTRANCE("7", "臨時＋入"),

	EARLY_ENTRANCE("8", "早出＋入"),

	RETIRED_TEMPORARY("9", "退勤＋臨時");

	public final String value;

	public final String nameType;

	private static final LeaveCategory[] values = LeaveCategory.values();

	private LeaveCategory(String value, String nameType) {
		this.value = value;
		this.nameType = nameType;
	}

	public static LeaveCategory valueStringOf(String value) {
		if (value == null) {
			return null;
		}

		for (LeaveCategory val : LeaveCategory.values) {
			if (val.value.equals(value)) {
				return val;
			}
		}

		return null;
	}

}
