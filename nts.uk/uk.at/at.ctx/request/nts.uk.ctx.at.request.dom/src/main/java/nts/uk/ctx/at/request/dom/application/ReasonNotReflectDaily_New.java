package nts.uk.ctx.at.request.dom.application;

import lombok.AllArgsConstructor;

/**
 * 日別実績反映不可理由
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum ReasonNotReflectDaily_New {
	
	// 問題なし
	NOT_PROBLEM(0, "問題なし"),
	
	// 実績確定済
	ACTUAL_CONFIRMED(1, "実績確定済");
	
	public final Integer value;
	
	public final String name;
	
}
