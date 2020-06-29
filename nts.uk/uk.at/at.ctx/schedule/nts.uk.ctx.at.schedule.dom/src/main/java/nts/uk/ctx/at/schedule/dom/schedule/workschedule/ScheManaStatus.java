package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import lombok.RequiredArgsConstructor;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.勤務予定.勤務予定
 * 予定管理状態
 * @author HieuLt
 *
 */
@RequiredArgsConstructor
public enum ScheManaStatus {
	// 0:未確定
	UNSETTLED(0, "未確定"),

	// 1:確定済み
	CONFIRMED(1, "確定済み");


	public final int value;

	public final String name;

	/** The Constant values. */
	private final static ScheManaStatus[] values = ScheManaStatus.values();

	public static ScheManaStatus valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ScheManaStatus val : ScheManaStatus.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
