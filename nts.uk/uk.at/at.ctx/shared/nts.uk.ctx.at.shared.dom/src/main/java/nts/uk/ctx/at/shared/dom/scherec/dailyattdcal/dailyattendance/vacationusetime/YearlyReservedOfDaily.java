package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 日別実績の積立年休
 * @author keisuke_hoshina
 *
 */
@Getter
public class YearlyReservedOfDaily {
	//使用時間
	private AttendanceTime useTime;

	/**
	 * Constructor
	 */
	public YearlyReservedOfDaily(AttendanceTime useTime) {
		super();
		this.useTime = useTime;
	}
}
