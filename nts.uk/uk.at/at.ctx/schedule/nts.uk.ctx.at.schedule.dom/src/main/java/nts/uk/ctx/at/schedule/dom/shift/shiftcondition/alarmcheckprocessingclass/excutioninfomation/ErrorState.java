package nts.uk.ctx.at.schedule.dom.shift.shiftcondition.alarmcheckprocessingclass.excutioninfomation;

import lombok.AllArgsConstructor;

/**
 * エラー有無
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
public enum ErrorState {
	//エラーなし
	NO_ERROR(0),
	//エラーあり
	ERROR(1);

	public final int value;
}
