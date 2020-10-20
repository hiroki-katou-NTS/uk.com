package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.toppage;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/** 月別実績のトップページ表示用時間 */
@Getter
public class TopPageDisplayOfMonthly implements Serializable{

	/***/
	private static final long serialVersionUID = 1L;

	/** 残業合計時間 */
	private AttendanceTimeMonth overtime;
	
	/** 休日出勤合計時間 */
	private AttendanceTimeMonth holidayWork;
	
	/** フレックス合計時間 */
	private AttendanceTimeMonth flex;
	
	private TopPageDisplayOfMonthly(AttendanceTimeMonth overtime, AttendanceTimeMonth holidayWork, AttendanceTimeMonth flex) {
		this.overtime = overtime;
		this.holidayWork = holidayWork;
		this.flex = flex;
	}
	
	public static TopPageDisplayOfMonthly empty() {
		
		return new TopPageDisplayOfMonthly(new AttendanceTimeMonth(0), new AttendanceTimeMonth(0), new AttendanceTimeMonth(0));
	}
	
	public static TopPageDisplayOfMonthly of(AttendanceTimeMonth overtime, 
			AttendanceTimeMonth holidayWork, AttendanceTimeMonth flex) {
		
		return new TopPageDisplayOfMonthly(overtime, holidayWork, flex);
	}
	
	public void sum(TopPageDisplayOfMonthly target) {
		this.overtime = this.overtime.addMinutes(target.overtime.valueAsMinutes());
		this.holidayWork = this.holidayWork.addMinutes(target.holidayWork.valueAsMinutes());
		this.flex = this.flex.addMinutes(target.flex.valueAsMinutes());
	}

	/** ○トップページ表示用時間 */
	public void aggregate(AttendanceTimeOfDailyAttendance attendanceTime) {
		if (attendanceTime == null) {
			return;
		}
		
		attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
			.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().ifPresent(ot -> {
			
			val otTime = ot.getOverTimeWorkFrameTime().stream().mapToInt(otf -> 
				otf.getOverTimeWork().getTime().valueAsMinutes()
				+ otf.getTransferTime().getTime().valueAsMinutes()).sum();
			
			addOverTime(otTime);
			
			val flexTime = ot.getFlexTime().getFlexTime().getTime().valueAsMinutes();
			
			addFlexTime(flexTime);
		});
		
		attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
			.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().ifPresent(ot -> {
			
			val holWorkTime = ot.getHolidayWorkFrameTime().stream().mapToInt(otf -> 
				otf.getHolidayWorkTime().get().getTime().valueAsMinutes()
				+ otf.getTransferTime().get().getTime().valueAsMinutes()).sum();
			
			addHolidayWorkTime(holWorkTime);
		});
	}
	
	public void addOverTime(int minutes) {
		this.overtime = this.overtime.addMinutes(minutes);
	}
	
	public void addHolidayWorkTime(int minutes) {
		this.holidayWork = this.holidayWork.addMinutes(minutes);
	}
	
	public void addFlexTime(int minutes) {
		this.flex = this.flex.addMinutes(minutes);
	}
}