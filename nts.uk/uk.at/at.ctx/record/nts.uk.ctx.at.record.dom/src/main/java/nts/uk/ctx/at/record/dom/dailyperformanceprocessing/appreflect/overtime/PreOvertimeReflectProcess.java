package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;

/**
 * 事前申請の反映処理
 * @author do_dt
 *
 */
public interface PreOvertimeReflectProcess {
	/**
	 * 予定勤種・就時の反映
	 * @param para
	 */
	public WorkInfoOfDailyPerformance workTimeWorkTimeUpdate(OvertimeParameter para, WorkInfoOfDailyPerformance dailyInfo);
	/**
	 * 勤種・就時の反映
	 * @param para
	 * @return True: 反映前後勤就の変更する
	 * False: 反映前後勤就の変更したい
	 */
	public AppReflectRecordWork changeFlg(OvertimeParameter para, WorkInfoOfDailyPerformance dailyInfo);
	/**
	 * 予定勤種・就時反映後の予定勤種・就時を取得する
	 * @param employeeId
	 * @param dataData
	 * @return
	 */
	public WorkTimeTypeOutput getScheWorkTimeType(String employeeId, GeneralDate dataData);
	/**
	 * 勤種・就時反映後の実績勤種・就時を取得する
	 * @param employeeId
	 * @param dataData
	 * @return
	 */
	public WorkTimeTypeOutput getRecordWorkTimeType(String employeeId, GeneralDate dataData);
	/**
	 * 予定開始終了時刻の反映
	 * @param para
	 * @param changeFlg
	 * @param dailyData
	 * @return
	 */
	public WorkInfoOfDailyPerformance startAndEndTimeReflectSche(OvertimeParameter para,
			boolean changeFlg, WorkInfoOfDailyPerformance dailyData);
	/**
	 * 設定による予定開始終了時刻を反映できるかチェックする
	 * @param para
	 * @param changeFlg: 反映前後勤就の変更フラグ
	 * @param dailyData: 日別実績の勤務情報
	 * @return
	 */
	public boolean timeReflectCheck(OvertimeParameter para,
			boolean changeFlg,
			WorkInfoOfDailyPerformance dailyData);
	/**
	 * 残業時間の反映
	 * @param para
	 * @return
	 */
	public AttendanceTimeOfDailyPerformance getReflectOfOvertime(OvertimeParameter para, AttendanceTimeOfDailyPerformance attendanceTimeData);
	/**
	 * 所定外深夜時間の反映
	 * @param employeeId
	 * @param dateData
	 * @param timeReflectFlg: 残業時間反映フラグ
	 * @param overShiftNight: 外深夜時間
	 */
	public AttendanceTimeOfDailyPerformance overTimeShiftNight(String employeeId, GeneralDate dateData, boolean timeReflectFlg, 
			Integer overShiftNight,	AttendanceTimeOfDailyPerformance attendanceTimeData);
	/**
	 * フレックス時間の反映
	 * @param employeeId
	 * @param dateDate
	 * @param timeReflectFlg
	 */
	public AttendanceTimeOfDailyPerformance reflectOfFlexTime(String employeeId, GeneralDate dateDate, boolean timeReflectFlg, 
			Integer flexExessTime, AttendanceTimeOfDailyPerformance attendanceTimeData);

}
