package nts.uk.ctx.at.record.app.find.monthly.root.common;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.absenceleave.AttendanceDaysMonthToTal;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.absenceleave.RemainDataDaysMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.dayoff.DayOffDayAndTimes;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.dayoff.DayOffRemainDayAndTimes;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.dayoff.RemainDataTimesMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.ActualSpecialLeaveRemain;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.ActualSpecialLeaveRemainDay;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeavaRemainTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemain;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemainDay;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUnDigestion;

@NoArgsConstructor
@AllArgsConstructor
public class DayAndTimeDto implements ItemConst {
	
	/** 日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_A)
	private Double days;
	
	/** 時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_B)
	private Integer time;
	
	public static DayAndTimeDto from(ActualSpecialLeaveRemain domain){
		return domain == null ? null : new DayAndTimeDto(domain.getDays().v(), domain.getTime().isPresent() ? domain.getTime().get().valueAsMinutes() : null);
	}
	public static DayAndTimeDto from(SpecialLeaveRemain domain){
		return domain == null ? null : new DayAndTimeDto(domain.getDays().v(), domain.getTime().isPresent() ? domain.getTime().get().valueAsMinutes() : null);
	}
	public static DayAndTimeDto from(SpecialLeaveUnDigestion domain){
		return domain == null ? null : new DayAndTimeDto(domain.getDays().v(), domain.getTimes().isPresent() ? domain.getTimes().get().valueAsMinutes() : null);
	}
	
	public SpecialLeaveRemain toSpecial(){
		return new SpecialLeaveRemain(new SpecialLeaveRemainDay(days == null ? 0 : days),
				Optional.ofNullable(time == null ? null : new SpecialLeavaRemainTime(time)));
	}
	public ActualSpecialLeaveRemain toActualSpecial(){
		return new ActualSpecialLeaveRemain(new ActualSpecialLeaveRemainDay(days == null ? 0 : days),
				Optional.ofNullable(time == null ? null : new SpecialLeavaRemainTime(time)));
	}
	public SpecialLeaveUnDigestion toUnDegest(){
		return new SpecialLeaveUnDigestion(new SpecialLeaveRemainDay(days == null ? 0 : days),
				Optional.ofNullable(time == null ? null : new SpecialLeavaRemainTime(time)));
	}
	
	public static DayAndTimeDto from(DayOffDayAndTimes domain){
		return domain == null ? null : new DayAndTimeDto(domain.getDay().v(), domain.getTime().isPresent() ? domain.getTime().get().valueAsMinutes() : null);
	}
	
	public DayOffDayAndTimes toOff(){
		return new DayOffDayAndTimes(new RemainDataDaysMonth(days == null ? 0 : days),
				Optional.ofNullable(time == null ? null : new RemainDataTimesMonth(time)));
	}
	
	public static DayAndTimeDto from(DayOffRemainDayAndTimes domain){
		return domain == null ? null : new DayAndTimeDto(domain.getDays().v(), domain.getTimes().isPresent() ? domain.getTimes().get().valueAsMinutes() : null);
	}
	
	public DayOffRemainDayAndTimes toOffRemain(){
		return new DayOffRemainDayAndTimes(new AttendanceDaysMonthToTal(days == null ? 0 : days),
				Optional.ofNullable(time == null ? null : new RemainingMinutes(time)));
	}
}
