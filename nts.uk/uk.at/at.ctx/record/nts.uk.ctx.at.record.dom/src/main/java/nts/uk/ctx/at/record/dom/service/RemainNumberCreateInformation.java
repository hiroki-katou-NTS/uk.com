package nts.uk.ctx.at.record.dom.service;

import java.util.List;

import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.RecordRemainCreateInfor;

public interface RemainNumberCreateInformation {
	/**
	 * 残数作成元情報を作成する
	 * @param lstAttendanceTimeData 日別実績の勤怠時間
	 * @param lstWorkInfor 日別実績の勤務情報
	 * @return
	 */
	List<RecordRemainCreateInfor> createRemainInfor(List<AttendanceTimeOfDailyPerformance> lstAttendanceTimeData,
			List<WorkInfoOfDailyPerformance> lstWorkInfor);
	/**
	 * 
	 * @param workInfor
	 * @param attendanceInfor
	 * @return
	 */
	RecordRemainCreateInfor remainDataFromRecord(WorkInfoOfDailyPerformance workInfor, AttendanceTimeOfDailyPerformance attendanceInfor);
}
