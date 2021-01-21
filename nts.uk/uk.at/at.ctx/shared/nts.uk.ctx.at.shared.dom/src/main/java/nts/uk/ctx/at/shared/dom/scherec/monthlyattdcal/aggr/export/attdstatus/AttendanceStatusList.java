package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.export.attdstatus;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.attdstatus.AttendanceStatus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
 * 出勤状態リスト
 * @author shuichu_ishida
 */
public class AttendanceStatusList {

	/** 日別実績の勤怠時間 */
	private Map<GeneralDate, AttendanceTimeOfDailyAttendance> attendanceTimeOfDailys;
	/** 日別実績の出退勤 */
	private Map<GeneralDate, TimeLeavingOfDailyAttd> timeLeavingOfDailys;
	/** 出勤状態マップ */
	@Getter
	private Map<GeneralDate, AttendanceStatus> map;
	
	/**
	 * コンストラクタ
	 * @param employeeId 社員ID
	 * @param period 期間
	 */
	public AttendanceStatusList(RequireM1 require, String employeeId, DatePeriod period){

		this.attendanceTimeOfDailys = require.dailyAttendanceTime(employeeId, period);
		this.timeLeavingOfDailys = require.dailyTimeLeaving(employeeId, period);
		this.setData();
	}
	
	public static interface RequireM1 {
		
		Map<GeneralDate, AttendanceTimeOfDailyAttendance> dailyAttendanceTime(String employeeId, DatePeriod datePeriod);
		
		Map<GeneralDate, TimeLeavingOfDailyAttd> dailyTimeLeaving(String employeeId, DatePeriod datePeriod);
	}
	
	/**
	 * コンストラクタ
	 * @param attendanceTimeOfDailys 日別実績の勤怠時間リスト
	 * @param timeLeavingOfDailys 日別実績の出退勤リスト
	 */
	public AttendanceStatusList(
			Map<GeneralDate, AttendanceTimeOfDailyAttendance> attendanceTimeOfDailys,
			Map<GeneralDate, TimeLeavingOfDailyAttd> timeLeavingOfDailys){
		
		this.attendanceTimeOfDailys = attendanceTimeOfDailys;
		this.timeLeavingOfDailys = timeLeavingOfDailys;
		this.setData();
	}
	
	/**
	 * データ設定
	 */
	private void setData() {
		
		this.map = new HashMap<>();
		for (val attendanceTime : this.attendanceTimeOfDailys.entrySet()){
			val ymd = attendanceTime.getKey();
			this.map.putIfAbsent(ymd, new AttendanceStatus(ymd));
			
			// 総労働時間の確認
			val totalTime = attendanceTime.getValue().getActualWorkingTimeOfDaily().getTotalWorkingTime().getTotalTime();
			this.map.get(ymd).setTotalTime(new AttendanceTime(totalTime.v()));
			
			// 総労働時間＞0 の時、出勤あり
			if (totalTime.greaterThan(0)) this.map.get(ymd).setExistAttendance(true);
		}
		for (val timeLeaving : this.timeLeavingOfDailys.entrySet()){
			val ymd = timeLeaving.getKey();
			this.map.putIfAbsent(ymd, new AttendanceStatus(ymd));
		
			// 出退勤の確認
			val timeLeavingWorks = timeLeaving.getValue().getTimeLeavingWorks();
			
			// 出勤あり・2回目の打刻があるか確認する
			for (val timeLeavingWork : timeLeavingWorks){
				int workNo = timeLeavingWork.getWorkNo().v();
				if (timeLeavingWork.getAttendanceStamp().isPresent()){
					TimeActualStamp attendanceStamp = timeLeavingWork.getAttendanceStamp().get();
					if (attendanceStamp.getStamp().isPresent()){
						WorkStamp stamp = attendanceStamp.getStamp().get();
						if (stamp.getTimeDay().getTimeWithDay().isPresent()){
							this.map.get(ymd).setExistAttendance(true);
							if (workNo == 2) this.map.get(ymd).setExistTwoTimesStamp(true);
						}
					}
				}
				if (timeLeavingWork.getLeaveStamp().isPresent()){
					TimeActualStamp leaveStamp = timeLeavingWork.getLeaveStamp().get();
					if (leaveStamp.getStamp().isPresent()){
						WorkStamp stamp = leaveStamp.getStamp().get();
						if (stamp.getTimeDay().getTimeWithDay().isPresent()){
							this.map.get(ymd).setExistAttendance(true);
							if (workNo == 2) this.map.get(ymd).setExistTwoTimesStamp(true);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 出勤状態を判断する
	 * @param ymd 年月日
	 * @return true：出勤している、false：出勤していない
	 */
	public boolean isAttendanceDay(GeneralDate ymd){

		if (this.map.containsKey(ymd)) 
			return this.map.get(ymd).isExistAttendance();
		return false;
	}
	
	/**
	 * 2回目の打刻が存在するか確認
	 * @param ymd 年月日
	 * @return true：存在する、false：存在しない
	 */
	public boolean isTwoTimesStampExists(GeneralDate ymd){
		
		if (this.map.containsKey(ymd)) return this.map.get(ymd).isExistTwoTimesStamp();
		return false;
	}
	
	/**
	 * 総労働時間を取得する
	 * @param ymd 年月日
	 * @return 総労働時間
	 */
	public AttendanceTime getTotalTime(GeneralDate ymd) {
		
		if (this.map.containsKey(ymd)) return this.map.get(ymd).getTotalTime();
		return new AttendanceTime(0);
	}
}
