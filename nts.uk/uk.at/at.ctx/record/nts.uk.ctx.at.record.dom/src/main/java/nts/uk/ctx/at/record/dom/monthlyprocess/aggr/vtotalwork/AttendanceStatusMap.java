package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.vtotalwork;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;

/**
 * 出勤状態マップ
 * @author shuichu_ishida
 */
public class AttendanceStatusMap {

	/** 出勤状態マップ */
	private Map<GeneralDate, AttendanceStatus> map;
	
	/**
	 * コンストラクタ
	 * @param attendanceTimeOfDailys 日別実績の勤怠時間リスト
	 * @param timeLeavingOfDailys 日別実績の出退勤リスト
	 */
	public AttendanceStatusMap(
			List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys,
			List<TimeLeavingOfDailyPerformance> timeLeavingOfDailys){
		
		this.map = new HashMap<>();
		for (val attendanceTime : attendanceTimeOfDailys){
			val ymd = attendanceTime.getYmd();
			this.map.putIfAbsent(ymd, new AttendanceStatus(ymd));
			
			// 総労働時間の確認
			val totalWorkingTime = attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime();
			this.map.get(ymd).setTotalTime(totalWorkingTime.getTotalTime());
		}
		for (val timeLeaving : timeLeavingOfDailys){
			val ymd = timeLeaving.getYmd();
			this.map.putIfAbsent(ymd, new AttendanceStatus(ymd));
		
			// 出退勤の確認
			val timeLeavingWorks = timeLeaving.getTimeLeavingWorks();
			this.map.get(ymd).getTimeLeavingWorks().addAll(timeLeavingWorks);
		}
	}
	
	/**
	 * 出勤状態を判断する
	 * @param ymd 年月日
	 * @return true：出勤している、false：出勤していない
	 */
	public boolean isAttendanceDay(GeneralDate ymd){

		boolean returnStatus = false;
		if (this.map.containsKey(ymd)){
			val targetYmd = this.map.get(ymd);
			// 総労働時間＞0 なら、出勤あり
			if (targetYmd.getTotalTime().greaterThan(0)) returnStatus = true;
			// 出退勤時刻がある時、出勤あり
			if (!targetYmd.getTimeLeavingWorks().isEmpty()) returnStatus = true;
		}
		return returnStatus;
	}
	
	/**
	 * 2回目の打刻が存在するか確認
	 * @param ymd 年月日
	 * @return true：存在する、false：存在しない
	 */
	public boolean isTwoTimesStampExists(GeneralDate ymd){
		
		boolean returnStatus = false;
		if (this.map.containsKey(ymd)){
			val targetYmd = this.map.get(ymd);
			returnStatus = targetYmd.isTwoTimesStampExists();
		}
		return returnStatus;
	}
}
