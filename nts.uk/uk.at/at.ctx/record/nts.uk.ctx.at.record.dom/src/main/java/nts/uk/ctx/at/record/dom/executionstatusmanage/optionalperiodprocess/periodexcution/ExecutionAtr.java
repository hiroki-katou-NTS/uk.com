package nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.periodexcution;

import lombok.AllArgsConstructor;
/**
 * 就業計算と集計の実行区分
 * @author phongtq
 *
 */
@AllArgsConstructor
public enum ExecutionAtr {
	
	// 0:時間
	AUTOMATIC_EXECUTION(0, "自動実行"),
	// 1:人数
	MANUAL_EXECUTION(1, "手動実行");

	public final int value;
	public final String name;
}
