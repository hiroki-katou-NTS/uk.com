package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectwork.startendwork;

import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

/**
 * @author thanh_nx
 *
 *  始業終業時刻の補正
 */
public class CorrectStartEndWorkForWorkInfo {
	
	/** 始業終業時刻の補正 */
	public static IntegrationOfDaily correctStartEndWork(Require require, IntegrationOfDaily dailyRecord) {
		
		/** input.日別勤怠の勤務情報を取得 */
		val workInfo = dailyRecord.getWorkInformation();
		
		/** 勤務情報と始業終業を変更する */
		workInfo.changeWorkSchedule(require, workInfo.getRecordInfo(), true, true);
		
		return dailyRecord;
	}
	
	public static interface Require extends WorkInfoOfDailyAttendance.Require {}
}
