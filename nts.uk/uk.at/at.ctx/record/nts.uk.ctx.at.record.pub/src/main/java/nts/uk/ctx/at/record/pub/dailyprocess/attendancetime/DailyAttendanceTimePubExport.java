package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * RequestList No23
 * Output Class
 * @author keisuke_hoshina
 *
 */
@Getter
public class DailyAttendanceTimePubExport {

	//残業時間
	private TimeWithCalculation overTime;
	
	//残業枠No
	private OverTimeFrameNo overTimeFrameNo;
	
	//休出時間
	private TimeWithCalculation holidayWorkTime;
	
	//休出枠
	private HolidayWorkFrameNo holidayWorkTimeNo;
	
	//加給時間
	private TimeWithCalculation bonusPayTime;
	
	//加給Ｎｏ
	private Integer bonusPayNo;
	
	//特定日加給時間
	private TimeWithCalculation specBonusPayTime;
	
	//特定日加給No
	private Integer specBonusPayNo;
	
	//フレックス時間
	private TimeWithCalculation flexTime;
	
	//所定外深夜時間
	private TimeWithCalculation midNightTime;

	/**
	 * Constructor
	 * @param overTime
	 * @param overTimeFrameNo
	 * @param holidayWorkTime
	 * @param holidayWorkTimeNo
	 * @param bonusPayTime
	 * @param bonusPayNo
	 * @param specBonusPayTime
	 * @param specBonusPayNo
	 * @param flexTime
	 * @param midNightTime
	 */
	public DailyAttendanceTimePubExport(TimeWithCalculation overTime, OverTimeFrameNo overTimeFrameNo,
			TimeWithCalculation holidayWorkTime, HolidayWorkFrameNo holidayWorkTimeNo, TimeWithCalculation bonusPayTime,
			Integer bonusPayNo, TimeWithCalculation specBonusPayTime, Integer specBonusPayNo,
			TimeWithCalculation flexTime, TimeWithCalculation midNightTime) {
		super();
		this.overTime = overTime;
		this.overTimeFrameNo = overTimeFrameNo;
		this.holidayWorkTime = holidayWorkTime;
		this.holidayWorkTimeNo = holidayWorkTimeNo;
		this.bonusPayTime = bonusPayTime;
		this.bonusPayNo = bonusPayNo;
		this.specBonusPayTime = specBonusPayTime;
		this.specBonusPayNo = specBonusPayNo;
		this.flexTime = flexTime;
		this.midNightTime = midNightTime;
	}
	
	
}
