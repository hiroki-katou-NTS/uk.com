package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ScheAndRecordSameChangeFlg {
	/**
	 * 自動変更しない
	 */
	DO_NOT_CHANGE_AUTO(0),
	/**
	 * 常に自動変更する
	 */
	ALWAYS_CHANGE_AUTO(1),
	/**
	 * 流動勤務のみ自動変更する
	 */
	AUTO_CHANGE_ONLY_WORK(2);
	public final Integer value;
	
}
