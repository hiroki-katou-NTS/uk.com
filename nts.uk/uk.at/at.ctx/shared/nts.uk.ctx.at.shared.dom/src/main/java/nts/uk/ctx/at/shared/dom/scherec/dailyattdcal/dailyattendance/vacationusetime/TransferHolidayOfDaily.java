package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 日別実績の振休
 */
@Getter
public class TransferHolidayOfDaily {
	//使用時間
	private AttendanceTime useTime;

	/**
	 * Constructor 
	 */
	public TransferHolidayOfDaily(AttendanceTime useTime) {
		super();
		this.useTime = useTime;
	}
}
