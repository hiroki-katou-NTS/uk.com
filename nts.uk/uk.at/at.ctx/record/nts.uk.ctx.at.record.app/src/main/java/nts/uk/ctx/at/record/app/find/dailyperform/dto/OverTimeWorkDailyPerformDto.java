package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.ExcessOverTimeWorkMidNightTime;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculationMinusExist;
import nts.uk.ctx.at.record.dom.daily.overtimework.FlexTime;
import nts.uk.ctx.at.record.dom.daily.overtimework.OverTimeOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OverTimeFrameTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OverTimeFrameTimeSheet;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/** 日別実績の残業時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverTimeWorkDailyPerformDto implements ItemConst {

	/** 残業枠時間: 残業枠時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = FRAMES, listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<OverTimeFrameTimeDto> overTimeFrameTime;

	/** 残業枠時間帯: 残業枠時間帯 */
	// @AttendanceItemLayout(layout = "B", isList = true, listMaxLength = ?,
	// setFieldWithIndex = "overtimeFrameNo")
	private List<OverTimeFrameTimeSheetDto> overTimeFrameTimeSheet;

	/** 所定外深夜時間: 法定外残業深夜時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = LATE_NIGHT)
	private ExcessOverTimeWorkMidNightTimeDto excessOfStatutoryMidNightTime;

	/** 残業拘束時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_D, jpPropertyName = RESTRAINT)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer overTimeSpentAtWork;

	/** 変形法定内残業: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_E, jpPropertyName = IRREGULAR + LEGAL)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer irregularWithinPrescribedOverTimeWork;

	/** フレックス時間: フレックス時間 */
	@AttendanceItemLayout(layout = LAYOUT_F, jpPropertyName = FLEX)
	private FlexTimeDto flexTime;

	public static OverTimeWorkDailyPerformDto fromOverTimeWorkDailyPerform(OverTimeOfDaily domain) {
		return domain == null ? null
				: new OverTimeWorkDailyPerformDto(
						ConvertHelper.mapTo(domain.getOverTimeWorkFrameTime(),
										c -> new OverTimeFrameTimeDto(CalcAttachTimeDto.toTimeWithCal(c.getTransferTime()),
												CalcAttachTimeDto.toTimeWithCal(c.getOverTimeWork()),
												getAttendanceTime(c.getBeforeApplicationTime()),
												getAttendanceTime(c.getOrderTime()), c.getOverWorkFrameNo().v())),
						ConvertHelper.mapTo(domain.getOverTimeWorkFrameTimeSheet(),
										c -> new OverTimeFrameTimeSheetDto(
												new TimeSpanForCalcDto(getAttendanceTime(c.getTimeSpan().getStart()),
														getAttendanceTime(c.getTimeSpan().getEnd())),
												c.getFrameNo().v())),
						ExcessOverTimeWorkMidNightTimeDto
								.fromOverTimeWorkDailyPerform(domain.getExcessOverTimeWorkMidNightTime().isPresent()
																? domain.getExcessOverTimeWorkMidNightTime().get() : null),
						getAttendanceTime(domain.getOverTimeWorkSpentAtWork()),
						getAttendanceTime(domain.getIrregularWithinPrescribedOverTimeWork()),
						domain.getFlexTime() == null ? null
								: new FlexTimeDto(CalcAttachTimeDto.toTimeWithCal(domain.getFlexTime().getFlexTime()),
										getAttendanceTime(domain.getFlexTime().getBeforeApplicationTime())));
	}
	
	@Override
	public OverTimeWorkDailyPerformDto clone() {
		return new OverTimeWorkDailyPerformDto(
				overTimeFrameTime == null ? null : overTimeFrameTime.stream().map(o -> o.clone()).collect(Collectors.toList()),
				overTimeFrameTimeSheet == null ? null : overTimeFrameTimeSheet.stream().map(o -> o.clone()).collect(Collectors.toList()),
				excessOfStatutoryMidNightTime == null ? null : excessOfStatutoryMidNightTime.clone(),
				overTimeSpentAtWork,
				irregularWithinPrescribedOverTimeWork,
				flexTime == null ? null : flexTime.clone());
	}

	private static Integer getAttendanceTime(AttendanceTime time) {
		return time == null ? 0 : time.valueAsMinutes();
	}

	private static Integer getAttendanceTime(TimeWithDayAttr time) {
		return time == null ? 0 : time.valueAsMinutes();
	}

	public OverTimeOfDaily toDomain() {
		return new OverTimeOfDaily(
				ConvertHelper.mapTo(overTimeFrameTimeSheet,
						(c) -> new OverTimeFrameTimeSheet(createTimeSheet(c.getTimeSheet()),
								new OverTimeFrameNo(c.getOvertimeFrameNo()))),
				ConvertHelper.mapTo(overTimeFrameTime,
						(c) -> new OverTimeFrameTime(new OverTimeFrameNo(c.getNo()),
								createTimeWithCalc(c.getOvertime()), createTimeWithCalc(c.getTransferTime()),
								toAttendanceTime(c.getBeforeApplicationTime()), toAttendanceTime(c.getOrderTime()))),
				excessOfStatutoryMidNightTime == null ? Finally.of(new ExcessOverTimeWorkMidNightTime(TimeDivergenceWithCalculation.defaultValue())) 
													  : Finally.of(excessOfStatutoryMidNightTime.toDomain()),
				toAttendanceTime(irregularWithinPrescribedOverTimeWork),
				new FlexTime(createTimeWithCalcMinus(), 
				flexTime == null ? AttendanceTime.ZERO : toAttendanceTime(flexTime.getBeforeApplicationTime())),
				toAttendanceTime(overTimeSpentAtWork));
	}

	private TimeDivergenceWithCalculationMinusExist createTimeWithCalcMinus() {
		return flexTime == null || flexTime.getFlexTime() == null ? 
				TimeDivergenceWithCalculationMinusExist.sameTime(AttendanceTimeOfExistMinus.ZERO): flexTime.getFlexTime().createTimeDivWithMinus();
	}

	private TimeSpanForCalc createTimeSheet(TimeSpanForCalcDto c) {
		return c == null ? new TimeSpanForCalc(TimeWithDayAttr.THE_PRESENT_DAY_0000, TimeWithDayAttr.THE_PRESENT_DAY_0000) 
				: new TimeSpanForCalc(toTimeWithDayAttr(c.getStart()), toTimeWithDayAttr(c.getEnd()));
	}

	private TimeDivergenceWithCalculation createTimeWithCalc(CalcAttachTimeDto c) {
		return c == null ? TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0)) : c.createTimeDivWithCalc();
	}

	private AttendanceTime toAttendanceTime(Integer time) {
		return time == null ? AttendanceTime.ZERO : new AttendanceTime(time);
	}

	private TimeWithDayAttr toTimeWithDayAttr(Integer time) {
		return time == null ? TimeWithDayAttr.THE_PRESENT_DAY_0000 : new TimeWithDayAttr(time);
	}
}
