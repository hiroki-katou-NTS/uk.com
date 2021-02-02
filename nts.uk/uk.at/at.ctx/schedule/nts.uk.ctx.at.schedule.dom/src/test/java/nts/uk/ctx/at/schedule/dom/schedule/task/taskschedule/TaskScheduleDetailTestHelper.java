package nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule;

import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class TaskScheduleDetailTestHelper {
	
	/**
	 * @param taskCode 作業コード
	 * @param startHour 開始時
	 * @param startMinute 開始分
	 * @param endHour 終了時
	 * @param endMinute 終了分
	 * @return
	 */
	public static TaskScheduleDetail create(String taskCode, int startHour, int startMinute, int endHour, int endMinute) {
		return new TaskScheduleDetail( new TaskCode(taskCode), 
				new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(startHour, startMinute), 
				TimeWithDayAttr.hourMinute(endHour, endMinute)) );
	}

}
