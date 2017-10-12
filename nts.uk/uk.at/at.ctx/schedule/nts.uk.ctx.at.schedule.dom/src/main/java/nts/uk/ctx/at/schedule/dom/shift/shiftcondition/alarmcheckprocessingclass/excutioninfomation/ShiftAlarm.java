package nts.uk.ctx.at.schedule.dom.shift.shiftcondition.alarmcheckprocessingclass.excutioninfomation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * シフトアラーム
 * 
 * @author sonnh1
 *
 */
@Getter
@AllArgsConstructor
public class ShiftAlarm {
	private String shiftAlarmId;
	private String sId;
	private GeneralDate startDate;
	private GeneralDate endDate;
	private int categoryNo;
	private int conditionNo;
	private String message;
}
