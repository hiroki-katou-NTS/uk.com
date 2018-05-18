package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.attdstatus;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.attdstatus.AttendanceStatus;
import nts.uk.ctx.at.shared.dom.scherec.attdstatus.GetAttendanceStatus;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実装：出勤状態を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetAttendanceStatusImpl implements GetAttendanceStatus {

	/** 日別実績の勤怠時間 */
	@Inject
	public AttendanceTimeRepository attendanceTimeOfDaily;
	/** 日別実績の出退勤の取得 */
	@Inject
	public TimeLeavingOfDailyPerformanceRepository timeLeavingOfDaily;

	/** 出勤状態マップ */
	private Map<GeneralDate, AttendanceStatus> map;

	/** コンストラクタ */
	private GetAttendanceStatusImpl(){
		this.map = new HashMap<>();
	}
	
	/** データ設定 */
	@Override
	public GetAttendanceStatus setData(String employeeId, DatePeriod period) {
		
		for (val attendanceTime : this.attendanceTimeOfDaily.findByPeriodOrderByYmd(employeeId, period)){
			val ymd = attendanceTime.getYmd();
			this.map.putIfAbsent(ymd, new AttendanceStatus(ymd));
			
			// 総労働時間の確認
			val totalTime = attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getTotalTime();
			this.map.get(ymd).setTotalTime(new AttendanceTime(totalTime.v()));
			
			// 総労働時間＞0 の時、出勤あり
			if (totalTime.greaterThan(0)) this.map.get(ymd).setExistAttendance(true);
		}
		for (val timeLeaving : this.timeLeavingOfDaily.findbyPeriodOrderByYmd(employeeId, period)){
			val ymd = timeLeaving.getYmd();
			this.map.putIfAbsent(ymd, new AttendanceStatus(ymd));
		
			// 出退勤の確認
			val timeLeavingWorks = timeLeaving.getTimeLeavingWorks();
			
			// 出退勤時刻がある時、出勤あり
			if (!timeLeavingWorks.isEmpty()) this.map.get(ymd).setExistAttendance(true);
			
			// 2回目の打刻があるか確認する
			for (val timeLeavingWork : timeLeavingWorks){
				if (timeLeavingWork.getWorkNo().v() != 2) continue;
				if (timeLeavingWork.getAttendanceStamp().isPresent()){
					this.map.get(ymd).setExistTwoTimesStamp(true);
				}
				if (timeLeavingWork.getLeaveStamp().isPresent()){
					this.map.get(ymd).setExistTwoTimesStamp(true);
				}
			}
		}
		return this;
	}
	
	/** 出勤状態を判断する */
	@Override
	public boolean isAttendanceDay(GeneralDate ymd){

		if (this.map.containsKey(ymd)) return this.map.get(ymd).isExistAttendance();
		return false;
	}
	
	/** 2回目の打刻が存在するか確認 */
	@Override
	public boolean isTwoTimesStampExists(GeneralDate ymd){
		
		if (this.map.containsKey(ymd)) return this.map.get(ymd).isExistTwoTimesStamp();
		return false;
	}
	
	/** 総労働時間を取得する */
	@Override
	public AttendanceTime getTotalTime(GeneralDate ymd) {
		
		if (this.map.containsKey(ymd)) return this.map.get(ymd).getTotalTime();
		return new AttendanceTime(0);
	}
}
