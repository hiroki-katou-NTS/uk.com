package nts.uk.ctx.at.function.app.nrl;

import lombok.AllArgsConstructor;

/**
 * @author thanh_nx
 *
 *         状態監視
 */
@AllArgsConstructor
public enum ConditionMonitor {

	// 開始(0)
	START(0),

	// 完了(1)
	FINISH(1);

	public final int value;

}
