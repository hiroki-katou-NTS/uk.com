package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReasonNotReflectRecord {

	/**
	 *  問題なし
	 */
	NOT_PROBLEM(0, "問題なし"),
	
	/**
	 *  勤務スケジュール確定済
	 */
	WORK_FIXED(1, "勤務スケジュール確定済"),
	
	/**
	 *  作業スケジュール確定済
	 */
	WORK_CONFIRMED(2, "作業スケジュール確定済");
	
	public final Integer value;
	
	public final String name;
}
