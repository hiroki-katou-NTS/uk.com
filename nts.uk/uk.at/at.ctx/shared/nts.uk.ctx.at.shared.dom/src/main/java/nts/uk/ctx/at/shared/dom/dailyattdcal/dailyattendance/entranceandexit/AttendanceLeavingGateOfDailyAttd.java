package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.entranceandexit;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainObject;

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
}
