package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * RequestList No23
 * Output Class
 * @author keisuke_hoshina
 *
 */
@Getter
public class DailyAttendanceTimePubExport {

	//残業枠No,残業時間
	private Map<OverTimeFrameNo,TimeWithCalculation> overTime;
	
	//休出枠,休出時間
	private Map<HolidayWorkFrameNo,TimeWithCalculation> holidayWorkTime;
	
	//加給Ｎｏ,加給時間
	private Map<Integer,AttendanceTime> bonusPayTime;

	//特定日加給No,特定日加給時間
	private Map<Integer,AttendanceTime> specBonusPayTime;
	
	//フレックス時間
	private TimeWithCalculation flexTime;
	
	//所定外深夜時間
	private TimeWithCalculation midNightTime;
	
	//計算合計外深夜時間
	private AttendanceTime timeOutSideMidnight;
	
	//計算残業深夜時間
	private AttendanceTime calOvertimeMidnight;
	
	//計算休出深夜時間 (法定区分,休出深夜時間)
	private Map<StaturoryAtrOfHolidayWork,AttendanceTime> calHolidayMidnight;

	/**
	 * Constructor 
	 */
	public DailyAttendanceTimePubExport(Map<OverTimeFrameNo, TimeWithCalculation> overTime,
			Map<HolidayWorkFrameNo, TimeWithCalculation> holidayWorkTime, Map<Integer, AttendanceTime> bonusPayTime,
			Map<Integer, AttendanceTime> specBonusPayTime, TimeWithCalculation flexTime,
			TimeWithCalculation midNightTime, AttendanceTime timeOutSideMidnight, AttendanceTime calOvertimeMidnight,
			Map<StaturoryAtrOfHolidayWork, AttendanceTime> calHolidayMidnight) {
		super();
		this.overTime = overTime;
		this.holidayWorkTime = holidayWorkTime;
		this.bonusPayTime = bonusPayTime;
		this.specBonusPayTime = specBonusPayTime;
		this.flexTime = flexTime;
		this.midNightTime = midNightTime;
		this.timeOutSideMidnight = timeOutSideMidnight;
		this.calOvertimeMidnight = calOvertimeMidnight;
		this.calHolidayMidnight = calHolidayMidnight;
	}
}
