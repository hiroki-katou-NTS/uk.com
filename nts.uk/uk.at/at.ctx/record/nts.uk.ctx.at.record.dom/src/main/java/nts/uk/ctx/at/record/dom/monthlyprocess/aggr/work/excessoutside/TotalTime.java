package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.excessoutside;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * 合計時間
 * @author shuichu_ishida
 */
@Getter
public class TotalTime {

	/** フレックス超過時間 */
	private AttendanceTimeMonth flexExcessTime;
	/** 週割増合計時間 */
	private AttendanceTimeMonth weeklyTotalPremiumTime;
	/** 月割増合計時間 */
	private AttendanceTimeMonth monthlyTotalPremiumTime;
	/** 就業時間 */
	private AttendanceTimeMonth workTime;
	/** 所定内割増時間 */
	private AttendanceTimeMonth prescribedPremiumTime;
	/** 残業時間 */
	private Map<OverTimeFrameNo, OverTimeFrameTotalTime> overTime;
	/** 休出時間 */
	private Map<HolidayWorkFrameNo, HolidayWorkFrameTotalTime> holidayWorkTime;
	
	/**
	 * コンストラクタ
	 */
	public TotalTime(){
		
		this.flexExcessTime = new AttendanceTimeMonth(0);
		this.weeklyTotalPremiumTime = new AttendanceTimeMonth(0);
		this.monthlyTotalPremiumTime = new AttendanceTimeMonth(0);
		this.workTime = new AttendanceTimeMonth(0);
		this.prescribedPremiumTime = new AttendanceTimeMonth(0);
		this.overTime = new HashMap<>();
		this.holidayWorkTime = new HashMap<>();
	}
}
