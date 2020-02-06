package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.attdstatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.attdstatus.AttendanceStatus;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 出勤状態リスト
 * @author shuichu_ishida
 */
public class AttendanceStatusList {

	/** 日別実績の勤怠時間 */
	private List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys;
	/** 日別実績の出退勤 */
	private List<TimeLeavingOfDailyPerformance> timeLeavingOfDailys;
	/** 出勤状態マップ */
	@Getter
	private Map<GeneralDate, AttendanceStatus> map;
	
	/**
	 * コンストラクタ
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param attendanceTimeOfDailyRepo 日別実績の勤怠時間リポジトリ
	 * @param timeLeavingOfDailyRepo 日別実績の出退勤リポジトリ
	 */
	public AttendanceStatusList(
			String employeeId,
			DatePeriod period,
			AttendanceTimeRepository attendanceTimeOfDailyRepo,
			TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyRepo){

		this.attendanceTimeOfDailys = attendanceTimeOfDailyRepo.findByPeriodOrderByYmd(employeeId, period);
		this.timeLeavingOfDailys = timeLeavingOfDailyRepo.findbyPeriodOrderByYmd(employeeId, period);
		this.setData();
	}
	
	/**
	 * コンストラクタ
	 * @param attendanceTimeOfDailys 日別実績の勤怠時間リスト
	 * @param timeLeavingOfDailys 日別実績の出退勤リスト
	 */
	public AttendanceStatusList(
			List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys,
			List<TimeLeavingOfDailyPerformance> timeLeavingOfDailys){
		
		this.attendanceTimeOfDailys = attendanceTimeOfDailys;
		this.timeLeavingOfDailys = timeLeavingOfDailys;
		this.setData();
	}
	
	/**
	 * データ設定
	 */
	private void setData() {
		
		this.map = new HashMap<>();
		for (val attendanceTime : this.attendanceTimeOfDailys){
			val ymd = attendanceTime.getYmd();
			this.map.putIfAbsent(ymd, new AttendanceStatus(ymd));
			
			// 総労働時間の確認
			val totalTime = attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getTotalTime();
			this.map.get(ymd).setTotalTime(new AttendanceTime(totalTime.v()));
			
			// 総労働時間＞0 の時、出勤あり
			if (totalTime.greaterThan(0)) this.map.get(ymd).setExistAttendance(true);
		}
		for (val timeLeaving : this.timeLeavingOfDailys){
			val ymd = timeLeaving.getYmd();
			this.map.putIfAbsent(ymd, new AttendanceStatus(ymd));
		
			// 出退勤の確認
			val timeLeavingWorks = timeLeaving.getTimeLeavingWorks();
			
			// 出勤あり・2回目の打刻があるか確認する
			for (val timeLeavingWork : timeLeavingWorks){
				int workNo = timeLeavingWork.getWorkNo().v();
				if (timeLeavingWork.getAttendanceStamp().isPresent()){
					TimeActualStamp attendanceStamp = timeLeavingWork.getAttendanceStamp().get();
					if (attendanceStamp.getStamp().isPresent()){
						WorkStamp stamp = attendanceStamp.getStamp().get();
						if (stamp.getTimeWithDay() != null){
							this.map.get(ymd).setExistAttendance(true);
							if (workNo == 2) this.map.get(ymd).setExistTwoTimesStamp(true);
						}
					}
				}
				if (timeLeavingWork.getLeaveStamp().isPresent()){
					TimeActualStamp leaveStamp = timeLeavingWork.getLeaveStamp().get();
					if (leaveStamp.getStamp().isPresent()){
						WorkStamp stamp = leaveStamp.getStamp().get();
						if (stamp.getTimeWithDay() != null){
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

		if (this.map.containsKey(ymd)) return this.map.get(ymd).isExistAttendance();
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
