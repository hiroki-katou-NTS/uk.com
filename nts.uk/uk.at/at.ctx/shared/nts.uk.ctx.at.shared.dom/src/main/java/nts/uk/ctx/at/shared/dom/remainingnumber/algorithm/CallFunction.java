package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import lombok.AllArgsConstructor;

/**
 * 
 * @author sonnlb
 *
 *         呼び出し元機能（予定／実績／申請）
 */

@AllArgsConstructor
public enum CallFunction {
	// 予定
	SCHEDULE(0),
	// 実績
	RECORD(1),
	// 申請
	APPLICATION(2);

	/** The value. */
	public final int value;

}
