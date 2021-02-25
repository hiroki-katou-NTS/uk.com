package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 日別実績の年休
 * @author keisuke_hoshina
 *
 */
@Getter
public class AnnualOfDaily {
	//使用時間
	private AttendanceTime useTime;
	//時間消化休暇時間
	private AttendanceTime digestionUseTime;
	
	/**
	 * Constructor 
	 */
	public AnnualOfDaily(AttendanceTime useTime, AttendanceTime digestionUseTime) {
		super();
		this.useTime = useTime;
		this.digestionUseTime = digestionUseTime;
	}
	
	
}
