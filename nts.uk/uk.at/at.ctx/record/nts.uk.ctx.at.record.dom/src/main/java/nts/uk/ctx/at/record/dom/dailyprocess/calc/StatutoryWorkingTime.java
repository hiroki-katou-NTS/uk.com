package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 法定労働時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class StatutoryWorkingTime {
	private AttendanceTime forActualWorkTime;
	private AttendanceTime forWorkTimeIncludePremium;
	
	/**
	 * Constructor 
	 */
	public StatutoryWorkingTime(AttendanceTime forActualWorkTime, AttendanceTime forWorkTimeIncludePremium) {
		super();
		this.forActualWorkTime = forActualWorkTime;
		this.forWorkTimeIncludePremium = forWorkTimeIncludePremium;
	}
}
