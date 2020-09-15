package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday;

import lombok.AllArgsConstructor;

/**
 * 打刻実行フラグ
 * @author tutk
 *
 */
@AllArgsConstructor
public enum EmbossingExecutionFlag {
	/* 未反映のみ */
	NOT_REFECT_ONLY(0),

	/* 全部 */
	ALL(1);

	public final int value;
}
