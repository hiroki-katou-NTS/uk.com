package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

/** 
 * 初期表示の月
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.表示設定
 * @author HieuLt
 */
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum InitDispMonth {
	// 0:当月
	CURRENT_MONTH(0, "当月"),

	// 1:翌月
	NEXT_MONTH(1, "翌月");


	public final int value;

	public final String name;

	/** The Constant values. */
	private final static InitDispMonth[] values = InitDispMonth.values();

	public static InitDispMonth valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (InitDispMonth val : InitDispMonth.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}

}
