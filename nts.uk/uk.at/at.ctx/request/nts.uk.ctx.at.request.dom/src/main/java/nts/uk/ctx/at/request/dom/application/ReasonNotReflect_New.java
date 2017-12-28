package nts.uk.ctx.at.request.dom.application;

import lombok.AllArgsConstructor;

/**
 * 予定反映不可理由
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum ReasonNotReflect_New {
	
	// 問題なし
	NOT_PROBLEM(0, "問題なし"),
	
	// 勤務スケジュール確定済
	WORK_FIXED(1, "勤務スケジュール確定済"),
	
	// 作業スケジュール確定済
	WORK_CONFIRMED(2, "作業スケジュール確定済");
	
	public final Integer value;
	
	public final String name;
	
}
