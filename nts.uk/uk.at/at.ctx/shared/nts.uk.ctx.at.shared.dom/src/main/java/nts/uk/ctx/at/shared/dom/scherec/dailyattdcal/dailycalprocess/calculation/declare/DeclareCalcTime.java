package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 申告計算時間
 * @author shuichi_ishida
 */
@Getter
@Setter
public class DeclareCalcTime {

	/** 普通残業 */
	private AttendanceTime overtime;
	/** 普通残業深夜 */
	private AttendanceTime overtimeMn;
	/** 早出残業 */
	private AttendanceTime earlyOvertime;
	/** 早出残業深夜 */
	private AttendanceTime earlyOvertimeMn;
	/** 休出 */
	private AttendanceTime holidayWork;
	/** 休出深夜 */
	private AttendanceTime holidayWorkMn;

	/**
	 * コンストラクタ
	 */
	public DeclareCalcTime(){
		this.overtime = new AttendanceTime(0);
		this.overtimeMn = new AttendanceTime(0);
		this.earlyOvertime = new AttendanceTime(0);
		this.earlyOvertimeMn = new AttendanceTime(0);
		this.holidayWork = new AttendanceTime(0);
		this.holidayWorkMn = new AttendanceTime(0);
	}
}
