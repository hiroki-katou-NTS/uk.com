package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * VO : 時間外の申告
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.出退勤時刻.時間外の申告
 * @author tutk
 *
 */
public class OvertimeDeclaration implements DomainValue {
	/**
	 * 時間外時間
	 * 就業時間帯コード old
	 */
	@Getter
	private final AttendanceTime overTime;
	
	/**
	 * 時間外深夜時間
	 */
	@Getter
	private final AttendanceTime overLateNightTime;

	public OvertimeDeclaration(AttendanceTime overTime, AttendanceTime overLateNightTime) {
		super();
		this.overTime = overTime;
		this.overLateNightTime = overLateNightTime;
	}
	
	
}
