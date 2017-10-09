package nts.uk.ctx.at.schedule.dom.shift.shiftcondition.alarmcheckprocessingclass.excutioninfomation;

import lombok.AllArgsConstructor;

/**
 * 実行状態
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
public enum ExcutionState {
	// 実行前
	BEFORE_EXCUTION(0),
	// 実行中
	DURING_EXCUTION(1),
	// 完了
	DONE(2);

	public final int value;
}
