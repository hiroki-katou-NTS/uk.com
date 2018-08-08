package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;

public interface AfterOvertimeReflectProcess {
	/**
	 * 予定勤種・就時の反映
	 * @param employeeId
	 * @param baseDate
	 * @param scheAndRecordSameChangeFlg
	 * @param workTimeCode
	 * @return
	 */
	public WorkInfoOfDailyPerformance checkScheReflect(OvertimeParameter overtimePara, WorkInfoOfDailyPerformance dailyInfo);
	/**
	 * 予定開始終了時刻の反映
	 * @param overtimePara
	 * @param workReflect
	 * @return
	 */
	public WorkInfoOfDailyPerformance checkScheWorkStarEndReflect(OvertimeParameter overtimePara, 
			boolean workReflect, WorkTimeTypeOutput workTimeType, WorkInfoOfDailyPerformance dailyInfor);
	/**
	 * 設定による予定開始終了時刻を反映できるかチェックする
	 * @param overtimePara
	 * @param workReflect
	 * @return
	 */
	public boolean checkReflectStartEndForSetting(OvertimeParameter overtimePara, boolean workReflect);
	/**
	 * 開始終了時刻の反映(事後申請)
	 * @param overtimePara
	 * @param workTimeType
	 */
	public void recordStartEndReflect(OvertimeParameter overtimePara, WorkTimeTypeOutput workTimeType);
	/**
	 * 開始終了時刻の反映(事後)
	 * @param para
	 * @param timeTypeData
	 * @return
	 */
	public void reflectStartEndtime(OvertimeParameter para, WorkTimeTypeOutput timeTypeData);
	/**
	 * 残業時間の反映
	 * @param para
	 */
	public AttendanceTimeOfDailyPerformance reflectOvertimeFrame(OvertimeParameter para, AttendanceTimeOfDailyPerformance attendanceTimeData);
	/**
	 * 所定外深夜時間の反映
	 * @param employeeId
	 * @param baseDate
	 * @param timeNight
	 */
	public AttendanceTimeOfDailyPerformance reflectTimeShiftNight(String employeeId, GeneralDate baseDate, Integer timeNight, AttendanceTimeOfDailyPerformance attendanceTimeData);

}
