package nts.uk.ctx.at.function.dom.dailyattendanceitem;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤怠項目.日次の勤怠項目.勤怠項目が利用できる帳票
 *
 * @author LienPTK
 */
public enum FormCanUsedForTime {
	// 日別勤務表
	DAILY_WORK_SCHEDULE(1),
	// 出勤簿
	ATTENDANCE_BOOK(2),
	// 月別勤務集計表
	MONTHLY_WORK_SCHEDULE(3),
	// 年間勤務表
	ANNUAL_WORK_SCHEDULE(4),
	// 任意期間集計表
	OPTIONAL_PERIOD_SCHEDULE(5),
	// 勤務状況表
	WORK_SITUATION_TABLE(6),
	// 年間勤務台帳
	ANNUAL_WORK_LEDGER(7),
	// 勤務台帳
	WORKBOOK(8),
	// 年間勤務表（36協定チェックリスト）
	ANNUAL_ROSTER_36_AGREEMENT(9),
	// 応援勤務一覧表
	WORK_SUPPORT(10);

	public final int value;
	
	private FormCanUsedForTime(int value) {
		this.value = value;
	}

	public static FormCanUsedForTime valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (FormCanUsedForTime val : FormCanUsedForTime.values()) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
