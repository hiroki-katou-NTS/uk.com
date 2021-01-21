package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 日別実績の時間消化休暇
 * @author keisuke_hoshina
 *
 */
@Getter
public class TimeDigestOfDaily {
	//使用時間
	private AttendanceTime useTime;
	//不足時間
	private AttendanceTime leakageTime;

	/**
	 * Constructor 
	 */
	public TimeDigestOfDaily(AttendanceTime useTime, AttendanceTime leakageTime) {
		super();
		this.useTime = useTime;
		this.leakageTime = leakageTime;
	}
}
