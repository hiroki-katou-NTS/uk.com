package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import lombok.AllArgsConstructor;

/**
 * 実行タイプ
 * @author tutk
 *
 */
@AllArgsConstructor
public enum ExecutionTypeDaily {
	
	/* 作成する */
	CREATE(0, "作成する"),
	/* 打刻反映する */
	IMPRINT(1, "打刻反映する"),
	/* 実績削除する */
	DELETE_ACHIEVEMENTS(2, "実績削除する");
	
	public final int value;
	
	/** The name id. */
	public final String nameId;

	private final static ExecutionTypeDaily[] values = ExecutionTypeDaily.values();
	
	public static ExecutionTypeDaily valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ExecutionTypeDaily val : ExecutionTypeDaily.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
