package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults;

import lombok.AllArgsConstructor;

/**
 * 実行タイプ
 * @author tutk
 *
 */
@AllArgsConstructor
public enum ProcessState {
	/* 中断 */
	INTERRUPTION(0),

	/* 正常終了 */
	SUCCESS(1);

	public final int value;
}
