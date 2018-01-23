package nts.uk.ctx.at.record.dom.worktime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;

/**
 * 
 * @author nampt 日別実績の出退勤 - root
 *
 */
@Setter
@Getter
@NoArgsConstructor
public class TimeLeavingOfDailyPerformance extends AggregateRoot {

	private String employeeId;

	private WorkTimes workTimes;

	// 1 ~ 2
	private List<TimeLeavingWork> timeLeavingWorks;

	private GeneralDate ymd;
	
	/**
	 * 指定した勤怠Noのデータを取得する
	 * @param workNo 勤怠No
	 * @return　出退勤クラス
	 */
	public TimeLeavingWork getAttendanceLeavingWork(WorkNo workNo) {
		
		List<TimeLeavingWork> attendanceLeavingWorkList = this.timeLeavingWorks.stream()
				.filter(ts -> ts.getWorkNo() == workNo).collect(Collectors.toList());
		if(attendanceLeavingWorkList.size()>1) {
			throw new RuntimeException("Exist duplicate workNo : " + workNo);
		}	
		return attendanceLeavingWorkList.get(0);		
	}
	
	/**
	 * 退勤を返す　　　（勤務回数が2回目の場合は2回目の退勤を返す）
	 * @return
	 */
	public TimeActualStamp getLeavingWork() {
		TimeLeavingWork targetAttendanceLeavingWorkTime = this.getAttendanceLeavingWork(new WorkNo(this.workTimes.v()));
		return targetAttendanceLeavingWorkTime.getLeaveStamp().get();
	}
	
	/**
	 * 勤務回数が1回か判定する
	 * @return　true:1回　false:2回目
	 */
	public boolean isFirstTimeWork() {
		if(this.workTimes.v() == 1) {
			return true;
		}
		return false;
	}

	/**
	 * ジャスト遅刻、ジャスト早退の計算区分を見て時刻調整
	 * @param isJustTimeLateAttendance ジャスト遅刻を計算するか 
	 * @param isJustEarlyLeave　ジャスト早退を計算するか
	 * @return 調整後の日別実績の出退勤クラス
	 */
	public TimeLeavingOfDailyPerformance calcJustTime(boolean isJustTimeLateAttendance,boolean isJustEarlyLeave) {
		List<TimeLeavingWork> newAttendanceLeave = new ArrayList<>();
		for(TimeLeavingWork attendanceLeave : timeLeavingWorks) {
			newAttendanceLeave.add(attendanceLeave.correctJustTime(isJustTimeLateAttendance, isJustEarlyLeave));
		}
		timeLeavingWorks.clear();
		timeLeavingWorks.addAll(newAttendanceLeave);
		
		return this;
	}
	public TimeLeavingOfDailyPerformance(String employeeId, WorkTimes workTimes, List<TimeLeavingWork> timeLeavingWorks,
			GeneralDate ymd) {
		super();
		this.employeeId = employeeId;
		this.workTimes = workTimes;
		this.timeLeavingWorks = timeLeavingWorks;
		this.ymd = ymd;
	}

	public void setProperty(String employeeId, WorkTimes workTimes, List<TimeLeavingWork> timeLeavingWorks,
			GeneralDate ymd) {
		this.employeeId = employeeId;
		this.workTimes = workTimes;
		this.timeLeavingWorks = timeLeavingWorks;
		this.ymd = ymd;

	}
}
