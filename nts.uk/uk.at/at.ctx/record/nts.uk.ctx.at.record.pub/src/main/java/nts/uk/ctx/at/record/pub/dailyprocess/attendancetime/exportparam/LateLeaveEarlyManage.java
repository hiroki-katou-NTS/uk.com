package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.exportparam;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * RequestListNo197
 * @author keisuke_hoshina
 *
 */
@Getter
@Setter
public class LateLeaveEarlyManage {
	//年月日
	GeneralDate date;
	
	//遅刻１
	boolean late1;
	//遅刻②
	boolean late2;
	//早退1
	boolean leaveEarly1;
	//早退2
	boolean leaveEarly2;
	
	/**
	 * Constructor 
	 */
	public LateLeaveEarlyManage(GeneralDate date, boolean late1, boolean late2, boolean leaveEarly1,
			boolean leaveEarly2) {
		super();
		this.date = date;
		this.late1 = late1;
		this.late2 = late2;
		this.leaveEarly1 = leaveEarly1;
		this.leaveEarly2 = leaveEarly2;
	}
	
	
}

