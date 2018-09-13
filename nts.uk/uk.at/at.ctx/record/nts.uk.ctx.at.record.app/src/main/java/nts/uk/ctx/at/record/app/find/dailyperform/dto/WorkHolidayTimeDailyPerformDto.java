package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayMidnightWork;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTimeSheet;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkMidNightTime;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.shr.com.time.TimeWithDayAttr;

/** 日別実績の休出時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkHolidayTimeDailyPerformDto implements ItemConst {

	/** 休出枠時間帯: 休出枠時間帯 */
//	@AttendanceItemLayout(layout = "A", isList = true, listMaxLength = ?, setFieldWithIndex = "holidayWorkFrameNo")
	private List<HolidayWorkFrameTimeSheetDto> holidyWorkFrameTimeSheet;

	/** 休出深夜: 休出深夜 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = LATE_NIGHT)
	private HolidayMidnightWorkDto holidayMidnightWork;

	/** 休出拘束時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = RESTRAINT)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer holidayTimeSpentAtWork;

	/** 休出枠時間: 休出枠時間 */
	@AttendanceItemLayout(layout = LAYOUT_D, jpPropertyName = FRAMES, listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<HolidayWorkFrameTimeDto> holidayWorkFrameTime;
	
	public static WorkHolidayTimeDailyPerformDto fromOverTimeWorkDailyPerform(HolidayWorkTimeOfDaily domain){
		return domain == null ? null : new WorkHolidayTimeDailyPerformDto(
				ConvertHelper.mapTo(domain.getHolidayWorkFrameTimeSheet(), c -> new HolidayWorkFrameTimeSheetDto(
						getTimeSpan(c.getTimeSheet()), 
						c.getHolidayWorkTimeSheetNo().v())), 
				HolidayMidnightWorkDto.fromHolidayMidnightWork(domain.getHolidayMidNightWork() == null || !domain.getHolidayMidNightWork().isPresent()
																? null :domain.getHolidayMidNightWork().get()), 
				domain.getHolidayTimeSpentAtWork() == null ? null : domain.getHolidayTimeSpentAtWork().valueAsMinutes(), 
				ConvertHelper.mapTo(domain.getHolidayWorkFrameTime(), (c) -> new HolidayWorkFrameTimeDto(
						CalcAttachTimeDto.toTimeWithCal(c.getHolidayWorkTime().isPresent() ? c.getHolidayWorkTime().get() : null), 
						CalcAttachTimeDto.toTimeWithCal(c.getTransferTime().isPresent() ? c.getTransferTime().get() : null), 
						getAttendanceTime(c.getBeforeApplicationTime().isPresent() ? c.getBeforeApplicationTime().get() : null),
						c.getHolidayFrameNo().v())));
	}
	
	@Override
	public WorkHolidayTimeDailyPerformDto clone(){
		return new WorkHolidayTimeDailyPerformDto(
				holidyWorkFrameTimeSheet == null ? null : holidyWorkFrameTimeSheet.stream().map(c -> c.clone()).collect(Collectors.toList()), 
				holidayMidnightWork == null ? null : holidayMidnightWork.clone(), 
				holidayTimeSpentAtWork, 
				holidayWorkFrameTime == null ? null : holidayWorkFrameTime.stream().map(c -> c.clone()).collect(Collectors.toList()));
	}

	private static TimeSpanForCalcDto getTimeSpan(TimeSpanForCalc c) {
		return c == null ? null : new TimeSpanForCalcDto(c.getStart().valueAsMinutes(), c.getEnd().valueAsMinutes());
	}
	
	private static Integer getAttendanceTime(AttendanceTime time) {
		return time == null ? 0 : time.valueAsMinutes();
	}
	
	public HolidayWorkTimeOfDaily toDomain() {
		return new HolidayWorkTimeOfDaily(
				ConvertHelper.mapTo(holidyWorkFrameTimeSheet,
						(c) -> new HolidayWorkFrameTimeSheet(new HolidayWorkFrameNo(c.getHolidayWorkFrameNo()),
								createTimeSheet(c.getTimeSheet()))),
				ConvertHelper.mapTo(holidayWorkFrameTime,
						(c) -> new HolidayWorkFrameTime(new HolidayWorkFrameNo(c.getNo()),
								createTimeWithCalc(c.getHolidayWorkTime()),
								createTimeWithCalc(c.getTransferTime()),
								c.getBeforeApplicationTime() == null ? Finally.empty() 
										: Finally.of(toAttendanceTime(c.getBeforeApplicationTime())))),
				holidayMidnightWork == null ? Finally.of(HolidayMidnightWorkDto.createDefaul())
											: Finally.of(holidayMidnightWork.toDomain()),
				toAttendanceTime(holidayTimeSpentAtWork));
	}
	
	private TimeSpanForCalc createTimeSheet(TimeSpanForCalcDto c) {
		return c == null ? new TimeSpanForCalc(TimeWithDayAttr.THE_PRESENT_DAY_0000, TimeWithDayAttr.THE_PRESENT_DAY_0000) 
				: new TimeSpanForCalc(toTimeWithDayAttr(c.getStart()), toTimeWithDayAttr(c.getEnd()));
	}
	private Finally<TimeDivergenceWithCalculation> createTimeWithCalc(CalcAttachTimeDto c) {
		return c == null ? Finally.of(TimeDivergenceWithCalculation.defaultValue()) : Finally.of(c.createTimeDivWithCalc());
	}
	
	private TimeWithDayAttr toTimeWithDayAttr(Integer time) {
		return time == null ? TimeWithDayAttr.THE_PRESENT_DAY_0000 : new TimeWithDayAttr(time);
	}
	
	private AttendanceTime toAttendanceTime(Integer time) {
		return time == null ? AttendanceTime.ZERO : new AttendanceTime(time);
	}
}
