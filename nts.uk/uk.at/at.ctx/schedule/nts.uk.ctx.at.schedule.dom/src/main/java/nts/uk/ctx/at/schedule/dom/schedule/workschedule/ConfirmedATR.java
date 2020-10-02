package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import lombok.RequiredArgsConstructor;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.勤務予定.勤務予定
 * @author HieuLT
 *
 */
@RequiredArgsConstructor
public enum ConfirmedATR {
	
	// 0:未確定
	UNSETTLED(0, "未確定"),

	// 1:確定済み
	CONFIRMED(1, "確定済み");


	public final int value;

	public final String name;

	/** The Constant values. */
	private final static ConfirmedATR[] values = ConfirmedATR.values();

	public static ConfirmedATR valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ConfirmedATR val : ConfirmedATR.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
