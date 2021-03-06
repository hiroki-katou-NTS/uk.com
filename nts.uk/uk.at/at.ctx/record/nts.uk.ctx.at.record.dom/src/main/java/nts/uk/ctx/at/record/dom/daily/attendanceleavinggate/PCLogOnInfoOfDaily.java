package nts.uk.ctx.at.record.dom.daily.attendanceleavinggate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.LogOnInfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnInfoOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnNo;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/** 日別実績のPCログオン情報*/
@Getter
@NoArgsConstructor
public class PCLogOnInfoOfDaily {
	
	/** 社員ID: 社員ID */
	private String employeeId;
	
	/** 年月日: 年月日 */
	private GeneralDate ymd;

	/** 時間帯 */
	private PCLogOnInfoOfDailyAttd timeZone;
	
	
	
	//出勤ログイン乖離時間の計算
	public AttendanceTimeOfExistMinus calcPCLogOnCalc(Optional<TimeLeavingOfDailyPerformance> attendanceLeave,GoLeavingWorkAtr goLeavingWorkAtr) {
		if(!attendanceLeave.isPresent()) return new AttendanceTimeOfExistMinus(0);
		List<AttendanceTimeOfExistMinus> resultList = new ArrayList<>();
		for(LogOnInfo logOn : this.timeZone.getLogOnInfo()) {
			if(!logOn.getLogOnLogOffTime(goLeavingWorkAtr).isPresent()) {
				continue;
			}
			TimeWithDayAttr pclogon = logOn.getLogOnLogOffTime(goLeavingWorkAtr).get();
			if(pclogon==null) continue;

			
			//出勤または退勤時間の取得
			if(!attendanceLeave.isPresent()) continue;
			if(!attendanceLeave.get().getAttendanceLeavingWork(new WorkNo(logOn.getWorkNo().v())).isPresent()) continue;
					
			Optional<TimeActualStamp> timeActualstamp = goLeavingWorkAtr.isGO_WORK()?attendanceLeave.get().getAttendanceLeavingWork(new WorkNo(logOn.getWorkNo().v())).get().getAttendanceStamp():
			 	 																	 attendanceLeave.get().getAttendanceLeavingWork(new WorkNo(logOn.getWorkNo().v())).get().getLeaveStamp();
			if(!timeActualstamp.isPresent()) continue;
			if(!timeActualstamp.get().getStamp().isPresent()) continue;
			Optional<WorkStamp> workStamp = timeActualstamp.get().getStamp();
			if(!workStamp.isPresent()) continue;
			if(!workStamp.get().getTimeDay().getTimeWithDay().isPresent()) continue;
			
			//PCログオン
			int pcLogOn = pclogon.valueAsMinutes();
			//出勤時刻
			int stamp = workStamp.get().getTimeDay().getTimeWithDay().get().valueAsMinutes();
			
			//出勤なら「出勤-ログオン」、退勤なら「ログオフ-退勤」
			int calcResult = goLeavingWorkAtr.isGO_WORK()?stamp-pcLogOn:pcLogOn-stamp;
			resultList.add(new AttendanceTimeOfExistMinus(calcResult));
		}
		AttendanceTimeOfExistMinus result = new AttendanceTimeOfExistMinus(resultList.stream().filter(t -> t != null).mapToInt(t -> t.valueAsMinutes()).sum());
//		if(result==null||result.lessThan(0)) {
//			return new AttendanceTime(0);
//		}
		return result;
	}
	
	
	/**
	 * PCLogOnNoに一致するログオン情報を取得する
	 * @param workNo
	 * @return　Optional<LogOnInfo>
	 */
	public Optional<LogOnInfo> getLogOnInfo(PCLogOnNo workNo) {
		return this.timeZone.getLogOnInfo().stream().filter(t->t.getWorkNo().equals(workNo)).findFirst();
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


	public PCLogOnInfoOfDaily(String employeeId, GeneralDate ymd, List<LogOnInfo> logOnInfo) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.timeZone = new PCLogOnInfoOfDailyAttd(logOnInfo);
	}


	public PCLogOnInfoOfDaily(String employeeId, GeneralDate ymd, PCLogOnInfoOfDailyAttd timeZone) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.timeZone = timeZone;
	}
	
	
}
