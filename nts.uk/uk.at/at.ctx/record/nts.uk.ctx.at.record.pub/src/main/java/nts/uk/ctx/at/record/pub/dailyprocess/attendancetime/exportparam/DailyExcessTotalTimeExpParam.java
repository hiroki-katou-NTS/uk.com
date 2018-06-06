package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.exportparam;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * RequestList No.193
 * @author keisuke_hoshina
 *
 */
public class DailyExcessTotalTimeExpParam {
	//残業時間
	AttendanceTime overTime;
	//休出時間
	AttendanceTime holidayWorkTime;
	//フレックス超過時間
	AttendanceTime flexOverTime;
	//所定外深夜時間
	AttendanceTime excessMidNightTime;
	
	/**
	 * Constructor 
	 */
	public DailyExcessTotalTimeExpParam(AttendanceTime overTime, AttendanceTime holidayWorkTime,
			AttendanceTime flexOverTime, AttendanceTime excessMidNightTime) {
		super();
		this.overTime = overTime;
		this.holidayWorkTime = holidayWorkTime;
		this.flexOverTime = flexOverTime;
		this.excessMidNightTime = excessMidNightTime;
	}
}
