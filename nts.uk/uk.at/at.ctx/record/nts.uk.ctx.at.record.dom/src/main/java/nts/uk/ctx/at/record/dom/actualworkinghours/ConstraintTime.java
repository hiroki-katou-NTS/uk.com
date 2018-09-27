package nts.uk.ctx.at.record.dom.actualworkinghours;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
/**
 * 
 * @author nampt
 * 総拘束時間
 *
 */
@Getter
@AllArgsConstructor
public class ConstraintTime {
	
	//深夜拘束時間
	private AttendanceTime lateNightConstraintTime;
	
	//総拘束時間
	private AttendanceTime totalConstraintTime;

	public static ConstraintTime defaultValue(){
		return new ConstraintTime(AttendanceTime.ZERO, AttendanceTime.ZERO);
	}
}
