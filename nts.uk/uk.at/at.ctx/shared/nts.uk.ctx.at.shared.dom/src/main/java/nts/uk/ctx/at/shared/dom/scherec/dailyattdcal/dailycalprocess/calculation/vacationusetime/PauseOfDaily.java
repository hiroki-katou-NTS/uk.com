package nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.vacationusetime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/*
 * 日別実績の振休
 * 
 */
@Getter
public class PauseOfDaily {

	//使用時間
	private AttendanceTime usetime;
	/*
	 * Constructor
	 */
	public PauseOfDaily(AttendanceTime usetime) {
		super();
		this.usetime =usetime;
	}
}
