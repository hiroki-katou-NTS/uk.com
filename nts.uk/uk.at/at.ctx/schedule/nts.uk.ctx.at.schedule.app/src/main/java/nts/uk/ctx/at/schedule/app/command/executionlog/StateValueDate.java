package nts.uk.ctx.at.schedule.app.command.executionlog;

import lombok.AllArgsConstructor;
/**
 * 
 * @author phongtq
 *
 */
@AllArgsConstructor
public enum StateValueDate {
	
	// 対象期間なし
	NO_TARGET_PERIOD(0),
	
	// 雇用履歴なし
	NO_EMPLOYMENT_HIST(1),
	
	// 対象期間あり
	TARGET_PERIOD(2);
	
	public final int value;

}
