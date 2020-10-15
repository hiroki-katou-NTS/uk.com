package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.exportparam;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;

/**
 * RequestListNo197 parameter
 * @author keisuke_hoshina
 *
 */
@Getter
public class LateLeaveEarlyAtr {
	//勤務回数
	WorkNo workNo;
	//遅刻
	boolean isLate;
	//早退
	@Setter
	boolean isLeaveEarly;
	
	/**
	 * Constructor 
	 */
	public LateLeaveEarlyAtr(WorkNo workNo, boolean isLate, boolean isLeaveEarly) {
		super();
		this.workNo = workNo;
		this.isLate = isLate;
		this.isLeaveEarly = isLeaveEarly;
	}
	
}
