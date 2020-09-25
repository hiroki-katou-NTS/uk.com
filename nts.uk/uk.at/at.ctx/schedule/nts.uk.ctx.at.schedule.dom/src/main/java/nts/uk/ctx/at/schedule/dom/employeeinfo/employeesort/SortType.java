package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import lombok.RequiredArgsConstructor;

/**
 * 並び替え種類
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.社員並び替え
 * @author HieuLT
 *
 */
@RequiredArgsConstructor
public enum SortType {
		// 0:スケジュールチーム
		SCHEDULE_TEAM(0, "スケジュールチーム"),

		// 1:ランク
		RANK(1, "ランク"),

		// 2:免許区分
		LISENCE_ATR(2, "免許区分"),

		// 3:職位
		POSITION(3, "職位"),
		// 4: 分類
		CLASSIFY(4, "分類");

		public final int value;

		public final String name;

		/** The Constant values. */
		private final static SortType[] values = SortType.values();

		public static SortType valueOf(Integer value) {
			// Invalid object.
			if (value == null) {
				return null;
			}

			// Find value.
			for (SortType val : SortType.values) {
				if (val.value == value) {
					return val;
				}
			}
			// Not found.
			return null;
		}
}
