package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.actualworkinghours.TotalWorkingTime;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.temporarytime.TemporaryFrameTimeOfDaily;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.temporarytime.TemporaryTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.latetime.IntervalExemptionTime;
import nts.uk.ctx.at.record.dom.daily.withinworktime.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.record.dom.raisesalarytime.RaiseSalaryTimeOfDailyPerfor;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;


/** 日別実績の総労働時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalWorkingTimeDto implements ItemConst {

	/** 総労働時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TOTAL_LABOR)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer totalWorkingTime;

	/** 総計算時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = TOTAL_CALC)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer totalCalcTime;

	/** 実働時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = ACTUAL)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer actualTime;

	/** 所定内時間: 日別実績の所定内時間 */
	@AttendanceItemLayout(layout = LAYOUT_D, jpPropertyName = WITHIN_STATUTORY)
	private WithinStatutoryTimeDailyPerformDto withinStatutoryTime;

	/** 所定外時間: 日別実績の所定外時間 */
	@AttendanceItemLayout(layout = LAYOUT_E, jpPropertyName = EXCESS_STATUTORY)
	private ExcessOfStatutoryTimeDailyPerformDto excessOfStatutoryTime;

	/** 臨時時間: 日別実績の臨時時間 */
	@AttendanceItemLayout(layout = LAYOUT_F, jpPropertyName = TEMPORARY, listMaxLength = 3, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<TemporaryTimeFrameDto> temporaryTime;

	/** 遅刻時間: 日別実績の遅刻時間 */
	@AttendanceItemLayout(layout = LAYOUT_G, jpPropertyName = LATE, listMaxLength = 2, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<LateTimeDto> lateTime;

	/** 早退時間: 日別実績の早退時間 */
	@AttendanceItemLayout(layout = LAYOUT_H, jpPropertyName = LEAVE_EARLY, listMaxLength = 2, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<LeaveEarlyTimeDailyPerformDto> leaveEarlyTime;

	/** 休憩時間: 日別実績の休憩時間 */
	@AttendanceItemLayout(layout = LAYOUT_I, jpPropertyName = BREAK)
	private BreakTimeSheetDailyPerformDto breakTimeSheet;

	/** 外出時間: 日別実績の外出時間 */
	@AttendanceItemLayout(layout = LAYOUT_J, listMaxLength = 4, jpPropertyName = GO_OUT, 
			enumField = DEFAULT_ENUM_FIELD_NAME, listNoIndex = true)
	private List<GoOutTimeSheetDailyPerformDto> goOutTimeSheet;

	/** 短時間勤務時間: 日別実績の短時間勤務時間 */
	@AttendanceItemLayout(layout = LAYOUT_K, jpPropertyName = SHORT_WORK, enumField = DEFAULT_ENUM_FIELD_NAME)
	private ShortWorkTimeDto shortWorkTime;

	/** 加給時間: 日別実績の加給時間 */
	@AttendanceItemLayout(layout = LAYOUT_L, jpPropertyName = RAISING_SALARY)
	private RaisingSalaryTimeDailyPerformDto raisingSalaryTime;

	/** 休暇時間: 日別実績の休暇 */
	@AttendanceItemLayout(layout = LAYOUT_M, jpPropertyName = HOLIDAY)
	private HolidayDailyPerformDto dailyOfHoliday;

	/** 勤務回数: 勤務回数 */
	@AttendanceItemLayout(layout = LAYOUT_N, jpPropertyName = COUNT)
	@AttendanceItemValue(type = ValueType.COUNT)
	private Integer workTimes;

	/** /*休暇加算時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_O, jpPropertyName = HOLIDAY + ADD)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer vacationAddTime;
	
	@Override
	public TotalWorkingTimeDto clone() {
		return new TotalWorkingTimeDto(totalWorkingTime, totalCalcTime, actualTime,
										withinStatutoryTime == null ? null : withinStatutoryTime.clone(),
										excessOfStatutoryTime == null ? null : excessOfStatutoryTime.clone(),
										temporaryTime == null ? null : temporaryTime.stream().map(t -> t.clone()).collect(Collectors.toList()),
										lateTime == null ? null : lateTime.stream().map(t -> t.clone()).collect(Collectors.toList()),
										leaveEarlyTime == null ? null : leaveEarlyTime.stream().map(t -> t.clone()).collect(Collectors.toList()),
										breakTimeSheet == null ? null : breakTimeSheet.clone(),
										goOutTimeSheet == null ? null : goOutTimeSheet.stream().map(t -> t.clone()).collect(Collectors.toList()),
										shortWorkTime == null ? null : shortWorkTime.clone(), 
										raisingSalaryTime == null ? null : raisingSalaryTime.clone(), 
										dailyOfHoliday == null ? null : dailyOfHoliday.clone(), 
										workTimes,
										vacationAddTime);
	}
	
	public static TotalWorkingTimeDto fromTotalWorkingTime(TotalWorkingTime domain) {
		return domain == null ? null
				: new TotalWorkingTimeDto(getAttendanceTime(domain.getTotalTime()),
						getAttendanceTime(domain.getTotalCalcTime()), getAttendanceTime(domain.getActualTime()),
						WithinStatutoryTimeDailyPerformDto
								.fromWithinStatutoryTimeDailyPerform(domain.getWithinStatutoryTimeOfDaily()),
						ExcessOfStatutoryTimeDailyPerformDto
								.fromExcessOfStatutoryTimeDailyPerform(domain.getExcessOfStatutoryTimeOfDaily()),
						domain.getTemporaryTime().getTemporaryTime() == null ? new ArrayList<>()
								: ConvertHelper.mapTo(domain.getTemporaryTime().getTemporaryTime(),
										(c) -> new TemporaryTimeFrameDto(c.getWorkNo().v(),
												getAttendanceTime(c.getTemporaryLateNightTime()),
												getAttendanceTime(c.getTemporaryTime()))),
						ConvertHelper.mapTo(domain.getLateTimeOfDaily(),
								(c) -> new LateTimeDto(CalcAttachTimeDto.toTimeWithCal(c.getLateTime()),
										CalcAttachTimeDto.toTimeWithCal(c.getLateDeductionTime()),
										getValicationUseDto(c.getTimePaidUseTime()),
										getAttendanceTime(c.getExemptionTime().getExemptionTime()), c.getWorkNo().v())),
						ConvertHelper.mapTo(domain.getLeaveEarlyTimeOfDaily(),
								(c) -> new LeaveEarlyTimeDailyPerformDto(CalcAttachTimeDto.toTimeWithCal(c.getLeaveEarlyTime()),
										CalcAttachTimeDto.toTimeWithCal(c.getLeaveEarlyDeductionTime()),
										getValicationUseDto(c.getTimePaidUseTime()),
										getAttendanceTime(c.getIntervalTime().getExemptionTime()), c.getWorkNo().v())),
						BreakTimeSheetDailyPerformDto.fromBreakTimeOfDaily(domain.getBreakTimeOfDaily()),
						ConvertHelper.mapTo(domain.getOutingTimeOfDailyPerformance(), c -> GoOutTimeSheetDailyPerformDto.toDto(c)),
						ShortWorkTimeDto.toDto(domain.getShotrTimeOfDaily()), 
						RaisingSalaryTimeDailyPerformDto.toDto(domain.getRaiseSalaryTimeOfDailyPerfor()), 
//						null,
						HolidayDailyPerformDto.from(domain.getHolidayOfDaily()), 
						domain.getWorkTimes() == null ? null : domain.getWorkTimes().v(),
						domain.getVacationAddTime() == null ? null : domain.getVacationAddTime().valueAsMinutes());
	}

	private static ValicationUseDto getValicationUseDto(TimevacationUseTimeOfDaily c) {
		return c == null ? null
				: new ValicationUseDto(getAttendanceTime(c.getTimeAnnualLeaveUseTime()),
						getAttendanceTime(c.getSixtyHourExcessHolidayUseTime()),
						getAttendanceTime(c.getTimeSpecialHolidayUseTime()),
						getAttendanceTime(c.getTimeCompensatoryLeaveUseTime()));
	}

	private static Integer getAttendanceTime(AttendanceTime domain) {
		return domain == null ? null : domain.valueAsMinutes();
	}

	public TotalWorkingTime toDomain() {
		TotalWorkingTime total = new TotalWorkingTime(toAttendanceTime(totalWorkingTime), toAttendanceTime(totalCalcTime),
				toAttendanceTime(actualTime), withinStatutoryTime == null ? WithinStatutoryTimeOfDaily.defaultValue() : withinStatutoryTime.toDomain(),
				excessOfStatutoryTime == null ? null : excessOfStatutoryTime.toDomain(),
				ConvertHelper.mapTo(lateTime, (c) -> new LateTimeOfDaily(
											createTimeWithCalc(c.getLateTime()),
											createTimeWithCalc(c.getLateDeductionTime()), new WorkNo(c.getNo()),
											createTimeValication(c.getBreakUse()),
											new IntervalExemptionTime(new AttendanceTime(0), null,
												toAttendanceTime(c.getIntervalExemptionTime())))),
				ConvertHelper.mapTo(leaveEarlyTime, (c) -> new LeaveEarlyTimeOfDaily(
											createTimeWithCalc(c.getTime()),
											createTimeWithCalc(c.getDeductionTime()), new WorkNo(c.getNo()),
											createTimeValication(c.getValicationUseTime()),
											new IntervalExemptionTime(AttendanceTime.ZERO, AttendanceTime.ZERO,
													toAttendanceTime(c.getIntervalExemptionTime())))),
				breakTimeSheet == null ? BreakTimeSheetDailyPerformDto.defaultValue() : breakTimeSheet.toDmain(), 
				ConvertHelper.mapTo(goOutTimeSheet, c -> c.toDomain()), 
				raisingSalaryTime == null ? new RaiseSalaryTimeOfDailyPerfor(new ArrayList<>(), new ArrayList<>()) 
						: raisingSalaryTime.toDomain(),
				workTimes == null ? null : new WorkTimes(workTimes),
				new TemporaryTimeOfDaily(ConvertHelper.mapTo(temporaryTime, (c) -> new TemporaryFrameTimeOfDaily(new WorkNo(c.getNo()),
										toAttendanceTime(c.getTemporaryTime()),
										toAttendanceTime(c.getTemporaryNightTime())))),
				shortWorkTime == null ? ShortWorkTimeDto.defaultDomain() : shortWorkTime.toDomain(),
				dailyOfHoliday == null ? HolidayDailyPerformDto.defaulDomain() : dailyOfHoliday.toDomain());
		
		if(vacationAddTime != null) {
			total.setVacationAddTime(new AttendanceTime(vacationAddTime));
		}
		return total;
	}

	private AttendanceTime toAttendanceTime(Integer time) {
		return time == null ? AttendanceTime.ZERO : new AttendanceTime(time);
	}

	private TimevacationUseTimeOfDaily createTimeValication(ValicationUseDto c) {
		return new TimevacationUseTimeOfDaily(toAttendanceTime(c == null ? null : c.getTimeAnnualLeaveUseTime()),
						toAttendanceTime(c == null ? null : c.getTimeCompensatoryLeaveUseTime()),
						toAttendanceTime(c == null ? null : c.getExcessHolidayUseTime()),
						toAttendanceTime(c == null ? null : c.getTimeSpecialHolidayUseTime()));
	}

	private TimeWithCalculation createTimeWithCalc(CalcAttachTimeDto c) {
		return c == null ? TimeWithCalculation.sameTime(AttendanceTime.ZERO) : c.createTimeWithCalc();
	}
}
