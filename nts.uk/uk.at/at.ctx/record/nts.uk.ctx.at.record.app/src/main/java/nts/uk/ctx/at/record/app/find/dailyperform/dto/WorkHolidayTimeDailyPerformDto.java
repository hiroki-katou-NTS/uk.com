package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkFrameTimeSheet;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/** 日別実績の休出時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkHolidayTimeDailyPerformDto {

	/** 休出枠時間帯: 休出枠時間帯 */
//	@AttendanceItemLayout(layout = "A", isList = true, listMaxLength = ?)
	private List<HolidayWorkFrameTimeSheetDto> holidyWorkFrameTimeSheet;

	/** 休出深夜: 休出深夜 */
	@AttendanceItemLayout(layout = "B", jpPropertyName="休出深夜")
	private HolidayMidnightWorkDto holidayMidnightWork;

	/** 休出拘束時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName="休出拘束時間")
	@AttendanceItemValue(itemId = 746, type = ValueType.INTEGER)
	private Integer holidayTimeSpentAtWork;

	/** 休出枠時間: 休出枠時間 */
	@AttendanceItemLayout(layout = "D", isList = true, jpPropertyName="休出枠時間", listMaxLength = 10)
	private List<HolidayWorkFrameTimeDto> holidayWorkFrameTime;
	
	public static WorkHolidayTimeDailyPerformDto fromOverTimeWorkDailyPerform(HolidayWorkTimeOfDaily domain){
		return domain == null ? null : new WorkHolidayTimeDailyPerformDto(
				domain.getHolidayWorkFrameTimeSheet().stream().map(c -> new HolidayWorkFrameTimeSheetDto(new TimeSpanForCalcDto(c.getTimeSheet().getStart().valueAsMinutes(), c.getTimeSheet().getEnd().valueAsMinutes()), c.getHolidayWorkTimeSheetNo().v())).collect(Collectors.toList()), 
				HolidayMidnightWorkDto.fromHolidayMidnightWork(domain.getHolidayMidNightWork().get()), 
				domain.getHolidayTimeSpentAtWork().valueAsMinutes(), 
				ConvertHelper.mapTo(domain.getHolidayWorkFrameTime(), (c) -> new HolidayWorkFrameTimeDto(
						new CalcAttachTimeDto(c.getHolidayWorkTime().get().getCalcTime().valueAsMinutes(), 
								c.getHolidayWorkTime().get().getTime().valueAsMinutes()), 
						new CalcAttachTimeDto(c.getTransferTime().get().getCalcTime().valueAsMinutes(), 
										c.getTransferTime().get().getTime().valueAsMinutes()), 
						c.getBeforeApplicationTime().get().valueAsMinutes(),
						c.getHolidayFrameNo().v())));
	}
	
	public HolidayWorkTimeOfDaily toDomain() {
		return new HolidayWorkTimeOfDaily(
				ConvertHelper.mapTo(holidyWorkFrameTimeSheet,
						(c) -> new HolidayWorkFrameTimeSheet(new HolidayWorkFrameNo(c.getHolidayWorkFrameNo()),
								new TimeSpanForCalc(new TimeWithDayAttr(c.getTimeSheet().getStart()),
										new TimeWithDayAttr(c.getTimeSheet().getEnd())))),
				ConvertHelper.mapTo(holidayWorkFrameTime,
						(c) -> new HolidayWorkFrameTime(new HolidayWorkFrameNo(c.getHolidayFrameNo()),
								Finally.of(TimeWithCalculation.createTimeWithCalculation(
										new AttendanceTime(c.getHolidayWorkTime().getTime()),
										new AttendanceTime(c.getHolidayWorkTime().getCalcTime()))),
								Finally.of(TimeWithCalculation.createTimeWithCalculation(
										new AttendanceTime(c.getTransferTime().getTime()),
										new AttendanceTime(c.getTransferTime().getCalcTime()))),
								Finally.of(new AttendanceTime(c.getBeforeApplicationTime())))),
				Finally.of(holidayMidnightWork.toDomain()));
	}
}
