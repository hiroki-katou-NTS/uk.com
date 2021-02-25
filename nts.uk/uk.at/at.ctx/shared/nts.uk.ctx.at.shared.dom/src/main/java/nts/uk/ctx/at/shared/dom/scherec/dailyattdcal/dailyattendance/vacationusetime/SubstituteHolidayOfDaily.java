package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 日別実績の代休
 * @author keisuke_hoshina
 *
 */
@Getter
public class SubstituteHolidayOfDaily {
	//使用時間
	private AttendanceTime useTime;
	//時間消化使用時間
	private AttendanceTime digestionUseTime;

	/**
	 * Constructor 
	 */
	public SubstituteHolidayOfDaily(AttendanceTime useTime, AttendanceTime digestionUseTime) {
		super();
		this.useTime = useTime;
		this.digestionUseTime = digestionUseTime;
	}
}
