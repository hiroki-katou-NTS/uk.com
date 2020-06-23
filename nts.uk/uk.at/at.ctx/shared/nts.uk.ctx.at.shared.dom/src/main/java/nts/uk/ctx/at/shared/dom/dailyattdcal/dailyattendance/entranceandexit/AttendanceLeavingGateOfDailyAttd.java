package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.entranceandexit;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 日別勤怠の入退門
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.入退門.日別勤怠の入退門
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class AttendanceLeavingGateOfDailyAttd implements DomainObject {
	/** 入退門: 入退門 */
	private List<AttendanceLeavingGate> attendanceLeavingGates;

	public AttendanceLeavingGateOfDailyAttd(List<AttendanceLeavingGate> attendanceLeavingGates) {
		super();
		this.attendanceLeavingGates = attendanceLeavingGates;
	}
	/**
	 * workNoに一致する入門または退門時間を取得する
	 * @param workNo
	 * @param goLeavingWorkAtr
	 * @return
	 */
	public TimeWithDayAttr getAttendanceLeavingGateTime(WorkNo workNo,GoLeavingWorkAtr goLeavingWorkAtr) {
		TimeWithDayAttr result = new TimeWithDayAttr(0);
		Optional<AttendanceLeavingGate> data = getAttendanceLeavingGate(workNo);
		if(goLeavingWorkAtr.isGO_WORK()) {
			if(data.isPresent()) {
				if(data.get().getAttendance().isPresent()) {
					if(data.get().getAttendance().get().getTimeDay().getTimeWithDay().isPresent()) {
						result = data.get().getAttendance().get().getTimeDay().getTimeWithDay().get();
					}
				}
			}
		}else {
			if(data.isPresent()) {
				if(data.get().getLeaving().isPresent()) {
					if(data.get().getLeaving().get().getTimeDay().getTimeWithDay().isPresent()) {
						result = data.get().getLeaving().get().getTimeDay().getTimeWithDay().get();
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * workNoに一致する出退勤を取得する
	 * @param workNo
	 * @return
	 */
	public Optional<AttendanceLeavingGate> getAttendanceLeavingGate(WorkNo workNo) {
		if(this.attendanceLeavingGates != null) {
			return this.attendanceLeavingGates.stream().filter(t -> t.getWorkNo().equals(workNo)).findFirst();
		}
		return Optional.empty();
	}
}
