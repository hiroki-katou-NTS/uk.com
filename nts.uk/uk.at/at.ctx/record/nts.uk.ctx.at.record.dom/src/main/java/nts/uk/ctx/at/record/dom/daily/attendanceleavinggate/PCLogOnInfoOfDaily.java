package nts.uk.ctx.at.record.dom.daily.attendanceleavinggate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/** 日別実績のPCログオン情報*/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PCLogOnInfoOfDaily {
	
	/** 社員ID: 社員ID */
	private String employeeId;
	
	/** 年月日: 年月日 */
	private GeneralDate ymd;

	/** ログオン情報: ログオン情報 */
	private List<LogOnInfo> logOnInfo;
	
	
	//出勤ログイン乖離時間の計算
	public AttendanceTime calcPCLogOnCalc(Optional<TimeLeavingOfDailyPerformance> attendanceLeave,GoLeavingWorkAtr goLeavingWorkAtr) {
		if(!attendanceLeave.isPresent()) return new AttendanceTime(0);
		List<AttendanceTime> resultList = new ArrayList<>();
		for(LogOnInfo logOn : this.logOnInfo) {
			if(logOn.getLogOnLogOffTime(goLeavingWorkAtr)==null) {
				continue;
			}
			//PCログオン
			int pcLogOn = logOn.getLogOnLogOffTime(goLeavingWorkAtr)!=null?logOn.getLogOnLogOffTime(goLeavingWorkAtr).valueAsMinutes():0;
			//出勤または退勤時間の取得
			int stamp = 0;
			if(attendanceLeave.isPresent()) {
				if(attendanceLeave.get().getAttendanceLeavingWork(new WorkNo(logOn.getWorkNo().v())).isPresent()) {
					Optional<TimeActualStamp> timeActualstamp = goLeavingWorkAtr.isGO_WORK()?attendanceLeave.get().getAttendanceLeavingWork(new WorkNo(logOn.getWorkNo().v())).get().getAttendanceStamp():
																						 	 attendanceLeave.get().getAttendanceLeavingWork(new WorkNo(logOn.getWorkNo().v())).get().getLeaveStamp();
					if(!(timeActualstamp.isPresent()&& timeActualstamp.get().getStamp().isPresent())) {
						continue;
					}
					Optional<WorkStamp> workStamp = timeActualstamp.get().getStamp();
					stamp = workStamp.get().getTimeWithDay().valueAsMinutes();
				}
			}
			//出勤なら「出勤-ログオン」、退勤なら「ログオフ-退勤」
			int calcResult = goLeavingWorkAtr.isGO_WORK()?stamp-pcLogOn:pcLogOn-stamp;
			resultList.add(new AttendanceTime(calcResult));
		}
		AttendanceTime result = new AttendanceTime(resultList.stream().filter(t -> t != null).mapToInt(t -> t.valueAsMinutes()).sum());	
		if(result==null||result.lessThan(0)) {
			return new AttendanceTime(0);
		}
		return result;
	}
	
	
	/**
	 * PCLogOnNoに一致するログオン情報を取得する
	 * @param workNo
	 * @return　Optional<LogOnInfo>
	 */
	public Optional<LogOnInfo> getLogOnInfo(PCLogOnNo workNo) {
	    LogOnInfo logOnInfo = this.logOnInfo.stream().filter(t->t.getWorkNo().equals(workNo)).findFirst().orElse(null);
		return Optional.of(logOnInfo);
	}
	
	/**
	 * PCLogOnNoに一致するログオンまたはログオフを取得する
	 * @param workNo
	 * @param goLeavingWorkAtr
	 * @return　Optional<TimeWithDayAttr>
	 */
	public Optional<TimeWithDayAttr> getLogOnTime(PCLogOnNo workNo,GoLeavingWorkAtr goLeavingWorkAtr) {
		Optional<LogOnInfo> logOnInfo = getLogOnInfo(workNo);
		if(logOnInfo.isPresent()) {
			return goLeavingWorkAtr.isGO_WORK()?logOnInfo.get().getLogOn():logOnInfo.get().getLogOff();
		}
		return Optional.empty();
	}
	
	
}
