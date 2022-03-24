package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 所定労働時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class StatutoryWorkingTime {
	/** 実働就業時間用 */
	private AttendanceTime forActualWorkTime;
	/** 割増含む就業時間用 */
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
