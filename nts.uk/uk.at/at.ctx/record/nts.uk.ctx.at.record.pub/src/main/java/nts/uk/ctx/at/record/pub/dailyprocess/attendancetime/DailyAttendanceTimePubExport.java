package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime;

import java.util.List;

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
	private List<TimeWithCalculation> overTime;
	
	//残業枠No
	private List<OverTimeFrameNo> overTimeFrameNo;
	
	//休出時間
	private List<TimeWithCalculation> holidayWorkTime;
	
	//休出枠
	private List<HolidayWorkFrameNo> holidayWorkTimeNo;
	
	//加給時間
	private List<TimeWithCalculation> bonusPayTime;
	
	//加給Ｎｏ
	private List<Integer> bonusPayNo;
	
	//特定日加給時間
	private List<TimeWithCalculation> specBonusPayTime;
	
	//特定日加給No
	private List<Integer> specBonusPayNo;
	
	//フレックス時間
	private TimeWithCalculation flexTime;
	
	//所定外深夜時間
	private TimeWithCalculation midNightTime;

	/**
	 * Constructor 
	 */
	public DailyAttendanceTimePubExport(List<TimeWithCalculation> overTime, List<OverTimeFrameNo> overTimeFrameNo,
			List<TimeWithCalculation> holidayWorkTime, List<HolidayWorkFrameNo> holidayWorkTimeNo,
			List<TimeWithCalculation> bonusPayTime, List<Integer> bonusPayNo,
			List<TimeWithCalculation> specBonusPayTime, List<Integer> specBonusPayNo, TimeWithCalculation flexTime,
			TimeWithCalculation midNightTime) {
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
