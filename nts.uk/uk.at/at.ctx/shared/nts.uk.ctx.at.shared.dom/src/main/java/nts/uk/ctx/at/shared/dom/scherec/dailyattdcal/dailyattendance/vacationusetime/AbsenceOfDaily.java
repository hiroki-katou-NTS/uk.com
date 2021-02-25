package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 日別実績の欠勤
 * @author keisuke_hoshina
 *
 */

@Getter
public class AbsenceOfDaily {
	//使用時間
	private AttendanceTime useTime;

	/**
	 * Constructor
	 */
	public AbsenceOfDaily(AttendanceTime useTime) {
		super();
		this.useTime = useTime;
	}
}
