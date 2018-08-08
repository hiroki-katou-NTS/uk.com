package nts.uk.ctx.at.record.pub.dailyperform.appreflect;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PrePostRecordAtr {
	/**
	 * 0: 事前の受付制限
	 */
	PREDICT(0),
	/**
	 * 1: 事後の受付制限
	 */
	POSTERIOR(1),
	
	/**
	 * 2: 選択なし
	 */
	NONE(2);
	
	public int value;

	
}
